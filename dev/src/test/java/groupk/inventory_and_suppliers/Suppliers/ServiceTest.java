package groupk.inventory_and_suppliers.Suppliers;

import groupk.shared.business.Inventory.Service.Objects.Product;
import groupk.shared.business.Suppliers.BusinessLogicException;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.shared.business.Suppliers.BussinessObject.Order;
import groupk.shared.business.Suppliers.BussinessObject.QuantityDiscount;
import groupk.shared.business.Suppliers.BussinessObject.Supplier;
import groupk.inventory_and_suppliers.TestsBase;
import groupk.inventory_suppliers.dataLayer.dao.records.OrderType;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;


import groupk.shared.service.ServiceBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest extends TestsBase {

    /**
     * used for float equality
     */
    private final float EPSILON = 0.1f;

    private final LocalDate date1 = LocalDate.of(2022, Month.APRIL, 1),
            date2 = LocalDate.of(2022, Month.APRIL, 2);

    /**
     * SUPPLIERS
     **/
    @Test
    void createSupplier() {
        ServiceBase.ResponseT<Supplier> response = createWithPpn(1);
        assertTrue(response.success, response.error);
        Supplier supplier = assertSuccess(response);
        Assertions.assertNotNull(supplier);
        Assertions.assertEquals(1, supplier.getPpn());
        // TODO add tests for other fields if there's time

        ServiceBase.ResponseT<Supplier> responseErroneous = createWithPpn(1);
        assertFalse(responseErroneous.success,
                "shouldn't be able to create when ReadOnlysupplier with same PPN exists.");
        assertNull(responseErroneous.data,
                "shouldn't be able to create when ReadOnlysupplier with same PPN exists.");
    }

    @Test
    void getSuppliers() {
        createWithPpn(1);
        createWithPpn(2);
        createWithPpn(3);
        createWithPpn(1);
        createWithPpn(2);

        Collection<Supplier> all = suppliers.getSuppliers();
        Assertions.assertEquals(3, all.size());
    }

    @Test
    void getSupplier() throws BusinessLogicException {
        suppliers.createSupplier(1, 111, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "john", "john@email.com", "054");
        Supplier supplier = assertSuccess(suppliers.getSupplier(1));
        Assertions.assertNotNull(supplier);
        Assertions.assertEquals(1, supplier.getPpn());
        Assertions.assertEquals(111, supplier.getBankNumber());
        // TODO add checking more fields if there's time.
    }

    @Test
    void deleteSupplier() throws BusinessLogicException {
        final int ppn = 1;

        initCategories();

        initCategories();
        
        Supplier supplier = assertSuccess(suppliers.createSupplier(ppn, 111, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "john", "john@email.com", "054"));
        Product product = assertSuccess(inventory.addProduct("Milk", "Tnoova", 100.0, 50, 10,
                1200, "Store", "Shop,", "10%"));
        Item item = assertSuccess(suppliers.createItem(ppn, 1, product.getProduct_id(),  1));
        Order order = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 12);

        // delete should work.
        ServiceBase.Response serviceResponse = suppliers.deleteSupplier(ppn);
        assertTrue(serviceResponse.success, "delete ReadOnlysupplier failed, but shouldn't have");

        // getting this ReadOnlysupplier shouldn't work.
        Assertions.assertFalse(suppliers.getSupplier(ppn).success,
                "Getting deleted ReadOnlysupplier should have failed");

        // creating new one with same PPN should work.
        ServiceBase.ResponseT<Supplier> otherResponse = suppliers.createSupplier(ppn, 222,
                "Ipsum", false,
                PaymentCondition.Credit, DayOfWeek.MONDAY,
                "george", "george@email.com", "050");
        assertTrue(otherResponse.success,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        assertNotNull(otherResponse.data,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        assertEquals(ppn, otherResponse.data.getPpn());

        // getting new one should work.
        Supplier findNew = assertSuccess(suppliers.getSupplier(ppn));
        Assertions.assertNotNull(findNew,
                "creating new ReadOnlysupplier with PPN of deleted ReadOnlysupplier should have worked");
        Assertions.assertEquals(ppn, findNew.getPpn());
        Assertions.assertEquals(222, findNew.getBankNumber(),
                "new ReadOnlysupplier should have fields of new one.");
    }
    
    private void initCategories() {
        inventory.addCategory("Store");
        inventory.addSubCategory("Store", "Shop,");
        inventory.addSubSubCategory("Store", "Shop,", "5%");
        inventory.addSubSubCategory("Store", "Shop,", "10%");
        inventory.addSubSubCategory("Store", "Shop,", "rye");
        inventory.addSubSubCategory("Store", "Shop,", "wheat");
    }

    /**
     * ITEMS
     **/
    @Test
    void createItem() {
        final int ppn1 = 1, cn1 = 111, cn2 = 112, ppn2 = 2, ppnNotExisting = 3;
        createWithPpn(ppn1);
        createWithPpn(ppn2);
        initCategories();
        // ReadOnlySupplier 1, ReadOnlyItem 1
        Product product = assertSuccess(inventory.addProduct("Milk", "Tnoova", 100.0, 50, 10,
                1200, "Store", "Shop,", "10%"));
        ServiceBase.ResponseT<Item> resApple =
                suppliers.createItem(ppn1, cn1, product.getProduct_id(),  1);
        assertTrue(resApple.success, "should have succeeded.");
        Item apple = assertSuccess(resApple);
        Assertions.assertNotNull(apple, "creating ReadOnlyitem shouldn't have returned null.");
        Assertions.assertEquals("Milk", product.getName());

        // ReadOnlySupplier 1, ReadOnlyItem 2
        Product product2 = assertSuccess(inventory.addProduct("Banana", "Tnoova", 100.0,
                50, 10, 1200, "Store", "Shop,", "10%"));
        ServiceBase.ResponseT<Item> resBanana =
                suppliers.createItem(ppn1, cn2, product2.getProduct_id(),  2);
        assertTrue(resBanana.success, "should have succeeded but got " + resBanana.error);

        Item banana = assertSuccess(resBanana);
        Assertions.assertNotNull(banana, "creating ReadOnlyitem shouldn't have returned null.");
        Assertions.assertEquals("Banana", product2.getName());

        // ReadOnlySupplier 2, ReadOnlyItem 1
        Product product3 =assertSuccess (inventory.addProduct("Pen", "Tnoova", 100.0, 50, 10,
                1200, "Store", "Shop,", "10%"));
        ServiceBase.ResponseT<Item> resOtherSupplier =
                suppliers.createItem(ppn2, cn1, product3.getProduct_id(),  10);
        assertTrue(resOtherSupplier.success,
                "creating other ReadOnlyitem with same CN but different PPN should've worked.");
        Item pen = assertSuccess(resOtherSupplier);
        Assertions.assertNotNull(pen, "creating ReadOnlyitem shouldn't have returned null.");
        Assertions.assertEquals(ppn2, pen.getSupplier().getPpn());
        Assertions.assertEquals(cn1, pen.getCatalogNumber());
        Assertions.assertEquals("Pen", product3.getName());

        // create with already existing
        ServiceBase.ResponseT<Item> alreadyExisting = suppliers.createItem(ppn1, cn1,
                product3.getProduct_id(),  123);
        assertFalse(alreadyExisting.success);

        // not existence supplier
        ServiceBase.ResponseT<Item> noSuchSupplier = suppliers.createItem(ppnNotExisting, cn1,
                product3.getProduct_id(),  60);
        assertFalse(noSuchSupplier.success);

    }

    @Test
    void getItems() {
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50, 10,
                1200, "Store", "Shop,", "10%"));
        final int[] amountsOfItems = {10, 11, 12, 13, 14};
        for (int i = 0; i < amountsOfItems.length; i++) {
            int ppn = (i + 1) * 111;
            int amountForThisSupplier = amountsOfItems[i];
            createWithPpn(ppn);
            for (int j = 0; j < amountForThisSupplier; j++) {
                int cn = (j + 1) * 11111;
                suppliers.createItem(ppn, cn, product4.getProduct_id(),  1);
            }
        }

        final int totalItems = Arrays.stream(amountsOfItems).sum();
        Assertions.assertEquals(totalItems, suppliers.getItems().size());
    }

    @Test
    void getItem() {
        initCategories();
        Product product4 =assertSuccess (inventory.addProduct("Pen", "Tnoova", 100.0, 50, 10,
                1200, "Store", "Shop,", "10%"));
        final int ppn = 1, wrongPPN = 2;
        final int cn = 11, wrongCN = 2;
        createWithPpn(ppn);
        suppliers.createItem(ppn, cn, product4.getProduct_id(),10);

        ServiceBase.ResponseT<Item> resSucc = suppliers.getItem(ppn, cn);
        assertTrue(resSucc.success);
        Assertions.assertNotNull(resSucc.data);
        assertEquals(ppn, resSucc.data.getSupplier().getPpn());
        assertEquals(cn, resSucc.data.getCatalogNumber());
        Assertions.assertEquals("Pen", product4.getName());

        ServiceBase.ResponseT<Item> resWrongPPN = suppliers.getItem(wrongPPN, cn);
        assertFalse(resWrongPPN.success);

        ServiceBase.ResponseT<Item> resWrongCN = suppliers.getItem(ppn, wrongCN);
        assertFalse(resWrongCN.success);

        ServiceBase.ResponseT<Item> resWrongBoth = suppliers.getItem(wrongPPN, wrongCN);
        assertFalse(resWrongBoth.success);
    }


    /**
     * ORDERS
     **/
    @Test
    void createOrder() {
        int ppn = 1, cnPen = 11;
        Supplier sup = assertSuccess(createWithPpn(ppn));
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        suppliers.createItem(ppn, cnPen, product4.getProduct_id(),  10);


        ServiceBase.ResponseT<Order> responseWithBadDates = suppliers.createOrder(ppn, date2, date1, OrderType.Periodical);
        assertFalse(responseWithBadDates.success,
                "shouldn't be able to start ReadOnlyorder if supplying date is before ordering.");

        ServiceBase.ResponseT<Order> response = suppliers.createOrder(ppn, date1, date2, OrderType.Periodical);
        assertTrue(response.success);

    }

    @Test
    void getOrders() {
        final int[] amountOfOrders = {10, 11, 12, 13, 14};
        for (int i = 0; i < amountOfOrders.length; i++) {
            int ppn = (i + 1) * 111;

            Supplier sup = assertSuccess(createWithPpn(ppn));

            // ReadOnlysupplier must have >= 1 items or opening ReadOnlyorder fails.
            initCategories();
            Product product4 =assertSuccess (inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                    10, 1200, "Store", "Shop,", "10%"));
            suppliers.createItem(ppn, 0, product4.getProduct_id(),  1);
            int amountForThisSupplier = amountOfOrders[i];
            createWithPpn(ppn);
            for (int j = 0; j < amountForThisSupplier; j++) {
                suppliers.createOrder(ppn, date1, date2, OrderType.Periodical);
            }
        }

        final int totalOrders = Arrays.stream(amountOfOrders).sum();
        Assertions.assertEquals(totalOrders, suppliers.getOrders().size());
    }

    @Test
    void deleteOrder() {
        int ppn = 1, cnPen = 11;
        Supplier sup = assertSuccess(createWithPpn(ppn));
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        Item pen = assertSuccess(suppliers.createItem(ppn, cnPen, product4.getProduct_id(),10));

        Order order = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(order.getId(), ppn, pen.getCatalogNumber(),10);

        ServiceBase.Response resDelete = suppliers.deleteOrder(order.getId());
        assertTrue(resDelete.success);

        // delete already deleted
        ServiceBase.Response resDeleteAgain = suppliers.deleteOrder(order.getId());
        assertFalse(resDeleteAgain.success);
    }


    @Test
    void orderItem() {
        int ppn = 1, cnPen = 11, cnNotebook = 12;
        Supplier sup = assertSuccess(createWithPpn(ppn));

        float penPrice = 11, notebookPrice = 7;
        int penAmount = 13, notebookAmount = 5;
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));

        Product product5 = assertSuccess(inventory.addProduct("Notebook", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        Item pen = assertSuccess(suppliers.createItem(ppn, cnPen, product4.getProduct_id(),  penPrice));
        Item notebook = assertSuccess(suppliers.createItem(ppn, cnNotebook, product5.getProduct_id(),
                notebookPrice));

        Order orderPens = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(orderPens.getId(), ppn, pen.getCatalogNumber(), penAmount);

        Order orderNotebooks = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(orderNotebooks.getId(), ppn, notebook.getCatalogNumber(), notebookAmount);

        Order orderBoth = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(orderBoth.getId(), ppn, pen.getCatalogNumber(), penAmount);
        suppliers.orderItem(orderBoth.getId(), ppn, notebook.getCatalogNumber(), notebookAmount);

        Assertions.assertTrue(orderBoth.containsItem(pen));
        Assertions.assertTrue(orderPens.containsItem(pen));
        Assertions.assertFalse(orderNotebooks.containsItem(pen));

        Assertions.assertTrue(orderBoth.containsItem(notebook));
        Assertions.assertFalse(orderPens.containsItem(notebook));
        Assertions.assertTrue(orderNotebooks.containsItem(notebook));
    }

    @Test
    void setOrderingDate() throws BusinessLogicException {
        final int ppn = 1;
        Supplier supplier = assertSuccess(createWithPpn(ppn));

        // ReadOnlyitem doesn't matter, there just has to be at least one for ReadOnlyorder to create.
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        suppliers.createItem(ppn, 11, product4.getProduct_id(),  1);

        final Period ONE_DAY = Period.ofDays(1);
        final LocalDate
                SUN = LocalDate.of(2022, Month.JANUARY, 2),
                MON = SUN.plus(ONE_DAY),
                TUE = MON.plus(ONE_DAY),
                WED = TUE.plus(ONE_DAY),
                THU = WED.plus(ONE_DAY),
                FRI = THU.plus(ONE_DAY);

        Order order = assertSuccess(suppliers.createOrder(ppn, MON, WED, OrderType.Periodical));
        Assertions.assertEquals(MON, order.getOrdered());

        suppliers.setOrderOrdered(order.getId(), SUN);
        Assertions.assertEquals(SUN, order.getOrdered());

        Assertions.assertDoesNotThrow(() -> suppliers.setOrderOrdered(order.getId(), WED), "should support same-day delivery");
        Assertions.assertEquals(WED, order.getOrdered());

        suppliers.setOrderOrdered(order.getId(), TUE);
        Assertions.assertEquals(TUE, order.getOrdered());

        Assertions.assertFalse(suppliers.setOrderOrdered(order.getId(), THU).success);
        Assertions.assertEquals(TUE, order.getOrdered());

        Assertions.assertFalse(suppliers.setOrderOrdered(order.getId(), FRI).success);
        Assertions.assertEquals(TUE, order.getOrdered());
    }


    @Test
    void setProvidedDate() throws BusinessLogicException {
        final int ppn = 1;
        Supplier supplier = assertSuccess(createWithPpn(ppn));
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));

        // ReadOnlyitem doesn't matter, there just has to be at least one for ReadOnlyorder to create.
        suppliers.createItem(ppn, 11, product4.getProduct_id(),  1);

        final Period ONE_DAY = Period.ofDays(1);
        final LocalDate
                SUN = LocalDate.of(2022, Month.JANUARY, 2),
                MON = SUN.plus(ONE_DAY),
                TUE = MON.plus(ONE_DAY),
                WED = TUE.plus(ONE_DAY),
                THU = WED.plus(ONE_DAY),
                FRI = THU.plus(ONE_DAY);

        Order order = assertSuccess(suppliers.createOrder(ppn, TUE, FRI, OrderType.Periodical));
        Assertions.assertEquals(FRI, order.getProvided());

        suppliers.setOrderProvided(order.getId(), THU);
        Assertions.assertEquals(THU, order.getProvided());

        Assertions.assertDoesNotThrow(() -> suppliers.setOrderProvided(order.getId(), TUE), "should support same-day delivery");
        Assertions.assertEquals(TUE, order.getProvided());

        suppliers.setOrderProvided(order.getId(), WED);
        Assertions.assertEquals(WED, order.getProvided());

        Assertions.assertFalse(suppliers.setOrderProvided(order.getId(), SUN).success);
        Assertions.assertEquals(WED, order.getProvided());

        Assertions.assertFalse(suppliers.setOrderProvided(order.getId(), MON).success);
        Assertions.assertEquals(WED, order.getProvided());
    }

    /**
     * DISCOUNTS & PRICE CHANGES
     */

    @Test
    void createDiscount() {
        int ppn = 1, cnCalc = 11;
        float priceCalc = 100;
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Calculator", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));

        Supplier sup = assertSuccess(createWithPpn(ppn));
        Item item = assertSuccess(suppliers.createItem(ppn, cnCalc, product4.getProduct_id(),  priceCalc));
        Order order = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 1);
        Assertions.assertEquals(priceCalc, order.getTotalPrice());

        ServiceBase.ResponseT<QuantityDiscount> res = suppliers.createDiscount(ppn, item.getCatalogNumber(), 10,
                0.01f);
        assertTrue(res.success);
        Assertions.assertEquals(priceCalc, order.getTotalPrice());

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 9);
        Assertions.assertEquals(priceCalc * 9, order.getTotalPrice(), EPSILON);

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 10);
        Assertions.assertEquals(priceCalc * 10 * 0.99, order.getTotalPrice(), EPSILON);

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 11);
        Assertions.assertEquals(priceCalc * 11 * 0.99, order.getTotalPrice(), EPSILON);

        res = suppliers.createDiscount(ppn, item.getCatalogNumber(), 100, 0.1f);
        assertTrue(res.success);
        Assertions.assertEquals(priceCalc * 11 * 0.99, order.getTotalPrice(), EPSILON);

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 100);
        Assertions.assertEquals(priceCalc * 100 * 0.9, order.getTotalPrice(), EPSILON);

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 101);
        Assertions.assertEquals(priceCalc * 101 * 0.9, order.getTotalPrice(), EPSILON);

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 250);
        Assertions.assertEquals(priceCalc * 250 * 0.9, order.getTotalPrice(), EPSILON);

        res = suppliers.createDiscount(ppn, item.getCatalogNumber(), 200, 0.25f);
        assertTrue(res.success);
        Assertions.assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        res = suppliers.createDiscount(ppn, item.getCatalogNumber(), 200, 0.25f);
        assertFalse(res.success, "already exists such discount; shouldn't be able to create, but succeeded");

        res = suppliers.createDiscount(ppn, item.getCatalogNumber(), 100, 0.2f);
        assertFalse(res.success, "already exists such discount; shouldn't be able to create, but succeeded");
    }

    @Test
    void deleteDiscount() {
        int ppn = 1, cnCalc = 11;
        float priceCalc = 100;
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Calculator", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        Supplier sup = assertSuccess(createWithPpn(ppn));

        Item item = assertSuccess(suppliers.createItem(ppn, cnCalc, product4.getProduct_id(), priceCalc));
        Order order = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));

        QuantityDiscount over10 = assertSuccess(suppliers.createDiscount(ppn, item.getCatalogNumber(), 10, 0.01f));
        QuantityDiscount over50 = assertSuccess(suppliers.createDiscount(ppn, item.getCatalogNumber(), 50, 0.05f));
        QuantityDiscount over100 = assertSuccess(suppliers.createDiscount(ppn, item.getCatalogNumber(), 100, 0.1f));
        QuantityDiscount over200 = assertSuccess(suppliers.createDiscount(ppn, item.getCatalogNumber(), 200, 0.25f));

        suppliers.orderItem(order.getId(), ppn, item.getCatalogNumber(), 250);

        Assertions.assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        suppliers.deleteDiscount(over50);
        Assertions.assertEquals(priceCalc * 250 * 0.75f, order.getTotalPrice(), EPSILON);

        suppliers.deleteDiscount(over200);
        Assertions.assertEquals(priceCalc * 250 * 0.9f, order.getTotalPrice(), EPSILON);

        suppliers.deleteDiscount(over100);
        Assertions.assertEquals(priceCalc * 250 * 0.99f, order.getTotalPrice(), EPSILON);

        suppliers.deleteDiscount(over10);
        Assertions.assertEquals(priceCalc * 250, order.getTotalPrice(), EPSILON);


    }

    private <T> T assertSuccess(ServiceBase.ResponseT<T> response) {
        assertTrue(response.success);
        return response.data;
    }

    @Test
    void setPrice() {
        int ppn = 1, cnBread = 12, cnMilk = 13;
        float priceBread = 5, priceMilk = 7;
        Supplier sup = assertSuccess(createWithPpn(ppn));
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Bread", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));

        Product product5 = assertSuccess(inventory.addProduct("Milk", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));

        Item bread = assertSuccess(suppliers.createItem(ppn, cnBread, product4.getProduct_id(),  priceBread));
        Item milk = assertSuccess(suppliers.createItem(ppn, cnMilk, product5.getProduct_id(),  priceMilk));

        final int amountBread = 10, amountMilk = 10;

        Order order = assertSuccess(suppliers.createOrder(ppn, date1, date2, OrderType.Periodical));
        suppliers.orderItem(order.getId(), ppn, bread.getCatalogNumber(), amountBread);
        suppliers.orderItem(order.getId(),ppn,  milk.getCatalogNumber(), amountMilk);

        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // I guess it's expensive organic or something
        priceBread = 10;
        priceMilk = 10;
        suppliers.setPrice(ppn,bread.getCatalogNumber(), priceBread);
        suppliers.setPrice(ppn,milk.getCatalogNumber(), priceMilk);
        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // wow the market sure is going crazy
        priceBread = 100;
        priceMilk = 125;
        suppliers.setPrice(ppn,bread.getCatalogNumber(), priceBread);
        suppliers.setPrice(ppn,milk.getCatalogNumber(), priceMilk);
        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);

        // Ba'al HaBayit Hishtage`a
        priceBread = 0.2f;
        priceMilk = 0.5f;
        suppliers.setPrice(ppn,bread.getCatalogNumber(), priceBread);
        suppliers.setPrice(ppn,milk.getCatalogNumber(), priceMilk);
        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);


        priceBread = 5;
        priceMilk = 7;
        suppliers.setPrice(ppn,bread.getCatalogNumber(), priceBread);
        suppliers.setPrice(ppn,milk.getCatalogNumber(), priceMilk);
        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);


        priceBread = 1;
        priceMilk = 2;
        suppliers.setPrice(ppn,bread.getCatalogNumber(), priceBread);
        suppliers.setPrice(ppn,milk.getCatalogNumber(), priceMilk);
        Assertions.assertEquals(priceBread * amountBread + priceMilk * amountMilk, order.getTotalPrice(), EPSILON);
    }

    /**
     * Tests for intertwined methods between the modules
     */

    @Test
    void orderNewShortageOrder(){
        int ppn = 1, cnPen = 11;
        Supplier sup = assertSuccess(createWithPpn(ppn));

        initCategories();

        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        suppliers.createItem(ppn, cnPen, product4.getProduct_id(),  10);


        ServiceBase.ResponseT<Order> responseWithBadDates = suppliers.createOrder(ppn, date2, date1, OrderType.Shortages);
        assertFalse(responseWithBadDates.success,
                "shouldn't be able to start ReadOnlyorder if supplying date is before ordering.");

        ServiceBase.ResponseT<Order> response = suppliers.createOrder(ppn, date1, date2, OrderType.Shortages);
        assertTrue(response.success);

    }

    @Test
    void orderNewPeriodicalOrder(){
        int ppn = 1, cnPen = 11;
        Supplier sup = assertSuccess(createWithPpn(ppn));
        initCategories();
        Product product4 = assertSuccess(inventory.addProduct("Pen", "Tnoova", 100.0, 50,
                10, 1200, "Store", "Shop,", "10%"));
        suppliers.createItem(ppn, cnPen, product4.getProduct_id(),  10);


        ServiceBase.ResponseT<Order> responseWithBadDates = suppliers.createOrder(ppn, date2, date1, OrderType.Periodical);
        assertFalse(responseWithBadDates.success,
                "shouldn't be able to start ReadOnlyorder if supplying date is before ordering.");

        ServiceBase.ResponseT<Order> response = suppliers.createOrder(ppn, date1, date2, OrderType.Periodical);
        assertTrue(response.success);


    }

    /**
     * (private) UTILS
     */

    private ServiceBase.ResponseT<Supplier> createWithPpn(int ppn) {
        return suppliers.createSupplier(ppn, 111, "dummy", true,
                PaymentCondition.Credit, null,
                "John", "john@email.com", "054");
    }
}