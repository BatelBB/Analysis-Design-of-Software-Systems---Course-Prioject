package adss_group_k.Tests.Suppliers;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.Entity.MutableContact;
import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;
import adss_group_k.BusinessLayer.Suppliers.Entity.QuantityDiscount;
import assignment1.BusinessLayer.Entity.*;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Item;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Order;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.ServiceResponse;
import adss_group_k.BusinessLayer.Suppliers.Service.ServiceResponseWithData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    /**
     * used for float equality
     */
    private final float EPSILON = 0.1f;

    private final LocalDate date1 = LocalDate.of(2022, Month.APRIL, 1),
            date2 = LocalDate.of(2022, Month.APRIL, 2);


    private SupplierService service;

    @BeforeEach
    void setUp() {
        service = new SupplierService();
    }

    /**
     * SUPPLIERS
     **/
    @Test
    void createSupplier() {
        ServiceResponseWithData<Supplier> response = createWithPpn(1);
        assertTrue(response.success, response.error);
        Supplier supplier = response.data;
        assertNotNull(supplier);
        assertEquals(1, supplier.getPpn());
        // TODO add tests for other fields if there's time

        ServiceResponseWithData<Supplier> responseErroneous = createWithPpn(1);
        assertFalse(responseErroneous.success, "shouldn't be able to create when ReadOnlysupplier with same PPN exists.");
        assertNull(responseErroneous.data, "shouldn't be able to create when ReadOnlysupplier with same PPN exists.");
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
                new MutableContact("john", "john@email.com", "054"));
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
                new MutableContact("john", "john@email.com", "054")).data;
        Item item = service.createItem(ppn, 1, "item", "category", 1).data;
        Order order = service.createOrder(supplier, date1, date2).data;
        service.orderItem(order, item, 12);

        // delete should work.
        ServiceResponse serviceResponse = service.deleteSupplier(ppn);
        assertTrue(serviceResponse.success, "delete ReadOnlysupplier failed, but shouldn't have");

        // getting this ReadOnlysupplier shouldn't work.
        assertThrows(BusinessLogicException.class, () -> service.getSupplier(ppn),
                "Getting deleted ReadOnlysupplier should have failed");

        // creating new one with same PPN should work.
        ServiceResponseWithData<Supplier> otherResponse = service.createSupplier(ppn, 222,
                "Ipsum", false,
                PaymentCondition.Credit, DayOfWeek.MONDAY,
                new MutableContact("george", "george@email.com", "050"));
        assertTrue(otherResponse.success,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        assertNotNull(otherResponse.data,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        assertEquals(ppn, otherResponse.data.getPpn());

        // getting new one should work.
        Supplier findNew = service.getSupplier(ppn);
        assertNotNull(findNew,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        assertEquals(ppn, findNew.getPpn());
        assertEquals(222, findNew.getBankNumber(),
                "new ReadOnlysupplier should have fields of new one.");
    }

    /**
     * ITEMS
     **/
    @Test
    void createItem() {
        final int ppn1 = 1, cn1 = 111, cn2 = 112, ppn2 = 2, ppnNotExisting = 3;
        createWithPpn(ppn1);
        createWithPpn(ppn2);

        // ReadOnlySupplier 1, ReadOnlyItem 1
        ServiceResponseWithData<Item> resApple =
                service.createItem(ppn1, cn1, "Apple", "Fruit", 1);
        assertTrue(resApple.success, "should have succeeded.");
        Item apple = resApple.data;
        assertNotNull(apple, "creating ReadOnlyitem shouldn't have returned null.");
        assertEquals("Apple", apple.getName());

        // ReadOnlySupplier 1, ReadOnlyItem 2
        ServiceResponseWithData<Item> resBanana =
                service.createItem(ppn1, cn2, "Banana", "Fruit", 2);
        assertTrue(resBanana.success, "should have succeeded but got " + resBanana.error);

        Item banana = resBanana.data;
        assertNotNull(banana, "creating ReadOnlyitem shouldn't have returned null.");
        assertEquals("Banana", banana.getName());

        // ReadOnlySupplier 2, ReadOnlyItem 1
        ServiceResponseWithData<Item> resOtherSupplier =
                service.createItem(ppn2, cn1, "Pen", "Office stuff", 10);
        assertTrue(resOtherSupplier.success,
                "creating other ReadOnlyitem with same CN but different PPN should've worked.");
        Item pen = resOtherSupplier.data;
        assertNotNull(pen, "creating ReadOnlyitem shouldn't have returned null.");
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
                "Puzzle", "Games", 60);
        assertFalse(noSuchSupplier.success);

    }

    @Test
    void getItems() {
        final int[] amountsOfItems = {10, 11, 12, 13, 14};
        for (int i = 0; i < amountsOfItems.length; i++) {
            int ppn = (i + 1) * 111;
            int amountForThisSupplier = amountsOfItems[i];
            createWithPpn(ppn);
            for (int j = 0; j < amountForThisSupplier; j++) {
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

    /**
     * ORDERS
     **/
    @Test
    void createOrder() {
        int ppn = 1, cnPen = 11;
        Supplier sup = createWithPpn(ppn).data;

        ServiceResponseWithData<Order> responseWithEmptySupplier = service.createOrder(sup, date1, date2);
        assertFalse(responseWithEmptySupplier.success,
                "shouldn't be able to start ReadOnlyorder if ReadOnlysupplier has no items.");


        service.createItem(ppn, cnPen, "Pen", "Office", 10);


        ServiceResponseWithData<Order> responseWithBadDates = service.createOrder(sup, date2, date1);
        assertFalse(responseWithBadDates.success,
                "shouldn't be able to start ReadOnlyorder if supplying date is before ordering.");

        ServiceResponseWithData<Order> response = service.createOrder(sup, date1, date2);
        assertTrue(response.success);

    }

    @Test
    void getOrders() {
        final int[] amountOfOrders = {10, 11, 12, 13, 14};
        for (int i = 0; i < amountOfOrders.length; i++) {
            int ppn = (i + 1) * 111;

            Supplier sup = createWithPpn(ppn).data;

            // ReadOnlysupplier must have >= 1 items or opening ReadOnlyorder fails.
            service.createItem(ppn, 0, "Lorem", "Ipsum", 1);
            int amountForThisSupplier = amountOfOrders[i];
            createWithPpn(ppn);
            for (int j = 0; j < amountForThisSupplier; j++) {
                service.createOrder(sup, date1, date2);
            }
        }

        final int totalOrders = Arrays.stream(amountOfOrders).sum();
        assertEquals(totalOrders, service.getOrders().size());
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
    void orderItem() {
        int ppn = 1, cnPen = 11, cnNotebook = 12;
        Supplier sup = createWithPpn(ppn).data;

        float penPrice = 11, notebookPrice = 7;
        int penAmount = 13, notebookAmount = 5;

        Item pen = service.createItem(ppn, cnPen, "Pen", "Office", penPrice).data;
        Item notebook = service.createItem(ppn, cnNotebook, "Notebook", "Office", notebookPrice).data;

        Order orderPens = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderPens, pen, penAmount);

        Order orderNotebooks = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderNotebooks, notebook, notebookAmount);

        Order orderBoth = service.createOrder(sup, date1, date2).data;
        service.orderItem(orderBoth, pen, penAmount);
        service.orderItem(orderBoth, notebook, notebookAmount);

        assertTrue(orderBoth.containsItem(pen));
        assertTrue(orderPens.containsItem(pen));
        assertFalse(orderNotebooks.containsItem(pen));

        assertTrue(orderBoth.containsItem(notebook));
        assertFalse(orderPens.containsItem(notebook));
        assertTrue(orderNotebooks.containsItem(notebook));
    }

    @Test
    void setOrderingDate() throws BusinessLogicException {
        final int ppn = 1;
        Supplier supplier = createWithPpn(ppn).data;

        // ReadOnlyitem doesn't matter, there just has to be at least one for ReadOnlyorder to create.
        service.createItem(ppn, 11, "Lorem", "Ipsum", 1);

        final Period ONE_DAY = Period.ofDays(1);
        final LocalDate
                SUN = LocalDate.of(2022, Month.JANUARY, 2),
                MON = SUN.plus(ONE_DAY),
                TUE = MON.plus(ONE_DAY),
                WED = TUE.plus(ONE_DAY),
                THU = WED.plus(ONE_DAY),
                FRI = THU.plus(ONE_DAY);

        Order order = service.createOrder(supplier, MON, WED).data;
        assertEquals(MON, order.getOrdered());

        service.setOrdered(order, SUN);
        assertEquals(SUN, order.getOrdered());

        assertDoesNotThrow(() -> service.setOrdered(order, WED), "should support same-day delivery");
        assertEquals(WED, order.getOrdered());

        service.setOrdered(order, TUE);
        assertEquals(TUE, order.getOrdered());

        assertThrows(BusinessLogicException.class, () -> service.setOrdered(order, THU));
        assertEquals(TUE, order.getOrdered());

        assertThrows(BusinessLogicException.class, () -> service.setOrdered(order, FRI));
        assertEquals(TUE, order.getOrdered());
    }


    @Test
    void setProvidedDate() throws BusinessLogicException {
        final int ppn = 1;
        Supplier supplier = createWithPpn(ppn).data;

        // ReadOnlyitem doesn't matter, there just has to be at least one for ReadOnlyorder to create.
        service.createItem(ppn, 11, "Lorem", "Ipsum", 1);

        final Period ONE_DAY = Period.ofDays(1);
        final LocalDate
                SUN = LocalDate.of(2022, Month.JANUARY, 2),
                MON = SUN.plus(ONE_DAY),
                TUE = MON.plus(ONE_DAY),
                WED = TUE.plus(ONE_DAY),
                THU = WED.plus(ONE_DAY),
                FRI = THU.plus(ONE_DAY);

        Order order = service.createOrder(supplier, TUE, FRI).data;
        assertEquals(FRI, order.getProvided());

        service.setProvided(order, THU);
        assertEquals(THU, order.getProvided());

        assertDoesNotThrow(() -> service.setProvided(order, TUE), "should support same-day delivery");
        assertEquals(TUE, order.getProvided());

        service.setProvided(order, WED);
        assertEquals(WED, order.getProvided());

        assertThrows(BusinessLogicException.class, () -> service.setProvided(order, SUN));
        assertEquals(WED, order.getProvided());

        assertThrows(BusinessLogicException.class, () -> service.setProvided(order, MON));
        assertEquals(WED, order.getProvided());
    }

    /**
     * DISCOUNTS & PRICE CHANGES
     */

    @Test
    void createDiscount() {
        int ppn = 1, cnCalc = 11;
        float priceCalc = 100;

        Supplier sup = createWithPpn(ppn).data;
        Item item = service.createItem(ppn, cnCalc, "Calculator", "Office", priceCalc).data;
        Order order = service.createOrder(sup, date1, date2).data;

        service.orderItem(order, item, 1);
        assertEquals(priceCalc, order.getTotalPrice());

        ServiceResponseWithData<QuantityDiscount> res = service.createDiscount(item, 10, 0.01f);
        assertTrue(res.success);
        assertEquals(priceCalc, order.getTotalPrice());

        service.orderItem(order, item, 9);
        assertEquals(priceCalc * 9, order.getTotalPrice(), EPSILON);

        service.orderItem(order, item, 10);
        assertEquals(priceCalc * 10 * 0.99, order.getTotalPrice(), EPSILON);

        service.orderItem(order, item, 11);
        assertEquals(priceCalc * 11 * 0.99, order.getTotalPrice(), EPSILON);

        res = service.createDiscount(item, 100, 0.1f);
        assertTrue(res.success);
        assertEquals(priceCalc * 11 * 0.99, order.getTotalPrice(), EPSILON);

        service.orderItem(order, item, 100);
        assertEquals(priceCalc * 100 * 0.9, order.getTotalPrice(), EPSILON);

        service.orderItem(order, item, 101);
        assertEquals(priceCalc * 101 * 0.9, order.getTotalPrice(), EPSILON);

        service.orderItem(order, item, 250);
        assertEquals(priceCalc * 250 * 0.9, order.getTotalPrice(), EPSILON);

        res = service.createDiscount(item, 200, 0.25f);
        assertTrue(res.success);
        assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        res = service.createDiscount(item, 200, 0.25f);
        assertFalse(res.success, "already exists such discount; shouldn't be able to create, but succeeded");

        res = service.createDiscount(item, 100, 0.2f);
        assertFalse(res.success, "already exists such discount; shouldn't be able to create, but succeeded");
    }

    @Test
    void deleteDiscount() {
        int ppn = 1, cnCalc = 11;
        float priceCalc = 100;

        Supplier sup = createWithPpn(ppn).data;
        Item item = service.createItem(ppn, cnCalc, "Calculator", "Office", priceCalc).data;
        Order order = service.createOrder(sup, date1, date2).data;

        QuantityDiscount over10 = service.createDiscount(item, 10, 0.01f).data;
        QuantityDiscount over50 = service.createDiscount(item, 50, 0.05f).data;
        QuantityDiscount over100 = service.createDiscount(item, 100, 0.1f).data;
        QuantityDiscount over200 = service.createDiscount(item, 200, 0.25f).data;

        service.orderItem(order, item, 250);

        assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        service.deleteDiscount(over50);
        assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        service.deleteDiscount(over200);
        assertEquals(priceCalc * 250 * 0.9f, order.getTotalPrice(), EPSILON);

        service.deleteDiscount(over100);
        assertEquals(priceCalc * 250 * 0.99f, order.getTotalPrice(), EPSILON);

        service.deleteDiscount(over10);
        assertEquals(priceCalc * 250, order.getTotalPrice(), EPSILON);


    }

    @Test
    void setPrice() {
        int ppn = 1, cnBread = 12, cnMilk = 13;
        float priceBread = 5, priceMilk = 7;
        Supplier sup = createWithPpn(ppn).data;

        Item bread = service.createItem(ppn, cnBread, "Bread", "Food", priceBread).data;
        Item milk = service.createItem(ppn, cnMilk, "Milk", "Food", priceMilk).data;

        final int amountBread = 10, amountMilk = 10;

        Order order = service.createOrder(sup, date1, date2).data;
        service.orderItem(order, bread, amountBread);
        service.orderItem(order, milk, amountMilk);

        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // I guess it's expensive organic or something
        priceBread = 10;
        priceMilk = 10;
        service.setPrice(bread, priceBread);
        service.setPrice(milk, priceMilk);
        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // wow the market sure is going crazy
        priceBread = 100;
        priceMilk = 125;
        service.setPrice(bread, priceBread);
        service.setPrice(milk, priceMilk);
        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // Ba'al HaBayit Hishtage`a
        priceBread = 0.2f;
        priceMilk = 0.5f;
        service.setPrice(bread, priceBread);
        service.setPrice(milk, priceMilk);
        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);


        priceBread = 5;
        priceMilk = 7;
        service.setPrice(bread, priceBread);
        service.setPrice(milk, priceMilk);
        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);


        priceBread = 1;
        priceMilk = 2;
        service.setPrice(bread, priceBread);
        service.setPrice(milk, priceMilk);
        assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);
    }

    /**
     * MISC
     */

    @Test
    void seedExample() {
        assertDoesNotThrow(service::seedExample);
        assertTrue(1 < service.getSuppliers().size());
        assertTrue(1 < service.getOrders().size());
        assertTrue(1 < service.getItems().size());
        assertTrue(1 < service.getDiscounts().size());
    }

    /**
     * (private) UTILS
     */

    private ServiceResponseWithData<Supplier> createWithPpn(int ppn) {
        return service.createSupplier(ppn, 111, "dummy", true,
                PaymentCondition.Credit, null,
                new MutableContact("John", "john@email.com", "054"));
    }
}