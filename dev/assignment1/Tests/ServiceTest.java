package assignment1.Tests;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Entity.*;
import assignment1.BusinessLayer.Service.Service;
import assignment1.BusinessLayer.Service.ServiceResponse;
import assignment1.BusinessLayer.Service.ServiceResponseWithData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private final LocalDate date1 = LocalDate.of(2022, Month.APRIL, 1),
            date2 = LocalDate.of(2022, Month.APRIL, 2);


    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void createSupplier() {
        ServiceResponseWithData<Supplier> response = createWithPpn(1);
        assertTrue(response.success, response.error);
        Supplier supplier = response.data;
        assertNotNull(supplier);
        assertEquals(1, supplier.getPpn());
        // TODO add tests for other fields if there's time

        ServiceResponseWithData<Supplier> responseErroneous = createWithPpn(1);
        assertFalse(responseErroneous.success, "shouldn't be able to create when supplier with same PPN exists.");
        assertNull(responseErroneous.data, "shouldn't be able to create when supplier with same PPN exists.");
    }

    @Test
    void getSuppliers() {
        createWithPpn(1);
        createWithPpn(2);
        createWithPpn(3);
        createWithPpn(1);
        createWithPpn(2);

        Collection<Supplier> suppliers = service.getSuppliers();
        assertEquals(3, suppliers.size());
    }

    @Test
    void getSupplier() throws BusinessLogicException {
        service.createSupplier(1, 111, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY,
                new Contact("john", "john@email.com", "054"));
        Supplier supplier = service.getSupplier(1);
        assertNotNull(supplier);
        assertEquals(1, supplier.getPpn());
        assertEquals(111, supplier.getBankNumber());
        // TODO add checking more fields if there's time.
    }

    @Test
    void deleteSupplier() throws BusinessLogicException {
        final int ppn = 1;

        Supplier supplier = service.createSupplier(ppn, 111, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY,
                new Contact("john", "john@email.com", "054")).data;
        Item item = service.createItem(ppn, 1, "item", "category", 1).data;
        Order order = service.createOrder(supplier, date1, date2).data;
        service.orderItem(order, item, 12);

        // delete should work.
        ServiceResponse serviceResponse = service.deleteSupplier(ppn);
        assertTrue(serviceResponse.success, "delete supplier failed, but shouldn't have");

        // getting this supplier shouldn't work.
        assertThrows(BusinessLogicException.class, () -> service.getSupplier(ppn),
                "Getting deleted supplier should have failed");

        // creating new one with same PPN should work.
        ServiceResponseWithData<Supplier> otherResponse = service.createSupplier(ppn, 222,
                "Ipsum", false,
                PaymentCondition.Credit, DayOfWeek.MONDAY,
                new Contact("george", "george@email.com", "050"));
        assertTrue(otherResponse.success,
                "creating new supplier with PPN of deleted supplier should have worked");
        assertNotNull(otherResponse.data,
                "creating new supplier with PPN of deleted supplier should have worked");
        assertEquals(ppn, otherResponse.data.getPpn());

        // getting new one should work.
        Supplier findNew = service.getSupplier(ppn);
        assertNotNull(findNew,
                "creating new supplier with PPN of deleted supplier should have worked");
        assertEquals(ppn, findNew.getPpn());
        assertEquals(222, findNew.getBankNumber(),
                "new supplier should have fields of new one.");
    }

    @Test
    void createItem() {
        final int ppn1 = 1, cn1 = 111, cn2 = 112, ppn2 = 2, ppnNotExisting = 3;
        createWithPpn(ppn1);
        createWithPpn(ppn2);

        // Supplier 1, Item 1
        ServiceResponseWithData<Item> resApple =
                service.createItem(ppn1, cn1, "Apple", "Fruit", 1);
        assertTrue(resApple.success, "should have succeeded.");
        Item apple = resApple.data;
        assertNotNull(apple, "creating item shouldn't have returned null.");
        assertEquals("Apple", apple.getName());

        // Supplier 1, Item 2
        ServiceResponseWithData<Item> resBanana =
                service.createItem(ppn1, cn2, "Banana", "Fruit", 2);
        assertTrue(resBanana.success, "should have succeeded but got " + resBanana.error);
        Item banana = resBanana.data;
        assertNotNull(banana, "creating item shouldn't have returned null.");
        assertEquals("Banana", banana.getName());

        // Supplier 2, Item 1
        ServiceResponseWithData<Item> resOtherSupplier =
                service.createItem(ppn2, cn1, "Pen", "Office stuff", 10);
        assertTrue(resOtherSupplier.success,
                "creating other item with same CN but different PPN should've worked.");
        Item pen = resOtherSupplier.data;
        assertNotNull(pen, "creating item shouldn't have returned null.");
        assertEquals(ppn2, pen.getSupplier().getPpn());
        assertEquals(cn1, pen.getCatalogNumber());
        assertEquals("Pen", pen.getName());

        // create with already existing
        ServiceResponseWithData<Item> alreadyExisting = service.createItem(ppn1, cn1,
            "Cat in a bag", "Animals & containers", 123
        );
        assertFalse(alreadyExisting.success);

        // not existence supplier
        ServiceResponseWithData<Item> noSuchSupplier = service.createItem(ppnNotExisting, cn1,
                "Puzzle", "Games",60);
        assertFalse(noSuchSupplier.success);

    }

    @Test
    void getItems() {
        final int[] amountsOfItems = { 10, 11, 12, 13, 14 };
        for(int i = 0; i < amountsOfItems.length; i++) {
            int ppn = (i + 1) * 111;
            int amountForThisSupplier = amountsOfItems[i];
            createWithPpn(ppn);
            for(int j = 0; j < amountForThisSupplier; j++) {
                int cn = (j + 1) * 11111;
                service.createItem(ppn, cn, "Lorem", "Ipsum", 1);
            }
        }

        final int totalItems = Arrays.stream(amountsOfItems).sum();
        assertEquals(totalItems, service.getItems().size());
    }

    @Test
    void getItem() {
        final int ppn = 1, wrongPPN = 2;
        final int cn = 11, wrongCN = 2;
        createWithPpn(ppn);
        service.createItem(ppn, cn, "Pen", "Office stuff", 10);

        ServiceResponseWithData<Item> resSucc = service.getItem(ppn, cn);
        assertTrue(resSucc.success);
        assertNotNull(resSucc.data);
        assertEquals(ppn, resSucc.data.getSupplier().getPpn());
        assertEquals(cn, resSucc.data.getCatalogNumber());
        assertEquals("Pen", resSucc.data.getName());

        ServiceResponseWithData<Item> resWrongPPN = service.getItem(wrongPPN, cn);
        assertFalse(resWrongPPN.success);

        ServiceResponseWithData<Item> resWrongCN = service.getItem(ppn, wrongCN);
        assertFalse(resWrongCN.success);

        ServiceResponseWithData<Item> resWrongBoth = service.getItem(wrongPPN, wrongCN);
        assertFalse(resWrongBoth.success);
    }

    @Test
    void seedExample() {
        assertDoesNotThrow(service::seedExample);
    }

    @Test
    void deleteItem() {
        int ppn = 1, cnPen = 11, cnNotebook = 12;
        Supplier sup = createWithPpn(ppn).data;

        Item pen = service.createItem(ppn, cnPen, "Pen", "Office", 10).data;
        Item notebook = service.createItem(ppn, cnNotebook, "Notebook", "Office", 7).data;

        Order orderPens = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderPens, pen, 10);

        Order orderNotebooks = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderNotebooks, notebook, 10);

        Order orderBoth = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderBoth, pen, 10);
        service.orderItem(orderBoth, notebook, 5);

        ServiceResponse resDelete = service.deleteItem(pen);
        assertTrue(resDelete.success);

        ServiceResponseWithData<Item> getDeleted = service.getItem(ppn, cnPen);
        assertFalse(getDeleted.success);

        assertEquals(0, orderPens.getTotalPrice());
    }

/*
    @Test
    void createDiscount() {
        fail("test not yet implemented.");
    }

    @Test
    void deleteDiscount() {
        fail("test not yet implemented.");
    }

    @Test
    void deleteOrder() {
        int ppn = 1, cnPen = 11;
        Supplier sup = createWithPpn(ppn).data;

        Item pen = service.createItem(ppn, cnPen, "Pen", "Office", 10).data;

        Order order = service.createOrder(sup, date1, date2).data;
        service.orderItem(order, pen, 10);

        ServiceResponse resDelete = service.deleteOrder(order);
        assertTrue(resDelete.success);

        // delete already deleted
        ServiceResponse resDeleteAgain = service.deleteOrder(order);
        assertFalse(resDeleteAgain.success);
    }

    @Test
    void getOrders() {
        fail("test not yet implemented.");
    }

    @Test
    void deleteOrder() { fail("test not yet implemented"); }


    @Test
    void getDiscount() { fail("test not yet implemented."); }

    @Test
    void orderItem() {
        fail("test not yet implemented.");
    }

    @Test
    void setPrice() {
        fail("test not yet implemented.");
    }

    */

    private ServiceResponseWithData<Supplier> createWithPpn(int ppn) {
       return service.createSupplier(ppn, 111, "dummy", true,
                PaymentCondition.Credit, null,
                new Contact("John", "john@email.com", "054"));
    }
}