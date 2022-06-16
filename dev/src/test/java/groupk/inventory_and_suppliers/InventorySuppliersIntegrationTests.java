package groupk.inventory_and_suppliers;

import groupk.inventory_suppliers.dataLayer.dao.records.OrderType;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;

import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.shared.business.Suppliers.BussinessObject.Order;
import groupk.shared.business.Suppliers.BussinessObject.QuantityDiscount;
import groupk.shared.business.Suppliers.BussinessObject.Supplier;

import groupk.shared.service.Inventory.Objects.ProductItem;
import groupk.shared.service.ServiceBase;
import groupk.shared.service.ServiceBase.ResponseT;
import groupk.shared.service.ServiceBase.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySuppliersIntegrationTests extends InventorySuppliersTestsBase {

    private QuantityDiscount discount;
    private Order order;
    private Supplier sup;

    private groupk.shared.service.Inventory.Objects.Report missingReport;
    private groupk.shared.service.Inventory.Objects.Report supplierReport;
    private groupk.shared.service.Inventory.Objects.Report expiredReport;
    private groupk.shared.service.Inventory.Objects.Report categoryReport;
    private groupk.shared.service.Inventory.Objects.Report defectiveReport;
    private groupk.shared.service.Inventory.Objects.Report surplusesReport;
    private groupk.shared.service.Inventory.Objects.Report byProductReport;

    private groupk.shared.service.Inventory.Objects.Product prod;

    int categorySize = 0, discountPairSize = 0, itemSize = 0, itemInOrderSize = 0, reportSize = 0,
            orderSize = 0, productSize = 0, productInReportSize = 0, productItem = 0,
            quantityDiscountSize = 0, subSubCategorySize = 0, subCategorySize = 0,
            supplierSize = 0;


    @Test
    public void loadDB() {
        addToDB();
        runQuery();

        Assertions.assertNotEquals(0, categorySize); //passes
        Assertions.assertNotEquals(0, discountPairSize); //passes
        Assertions.assertNotEquals(0, itemSize); //passes
        Assertions.assertNotEquals(0, itemInOrderSize); //passes
        Assertions.assertNotEquals(0, orderSize); //passes
        Assertions.assertNotEquals(0, productSize); //passes
        Assertions.assertNotEquals(0, productInReportSize);//passes
        Assertions.assertNotEquals(0, productItem); //passes
        Assertions.assertNotEquals(0, reportSize); //passes
        Assertions.assertNotEquals(0, quantityDiscountSize); //passes
        Assertions.assertNotEquals(0, subSubCategorySize); //passes
        Assertions.assertNotEquals(0, subCategorySize); //passes
        Assertions.assertNotEquals(0, supplierSize); //passes
    }

    @Test
    public void deleteDB() {
        addToDB();

        facade.deleteOrder(order.getId());
        facade.deleteSupplier(sup.getPpn());

        facade.removeReport(missingReport.getId());
        facade.removeReport(byProductReport.getId());
        facade.removeReport(categoryReport.getId());
        facade.removeReport(defectiveReport.getId());
        facade.removeReport(supplierReport.getId());
        facade.removeReport(expiredReport.getId());
        facade.removeReport(surplusesReport.getId());
        facade.removeProduct(prod.getProduct_id());
        facade.removeSubSubCategory("Dairy", "Shop", "10%",true);
        facade.removeSubCategory("Dairy", "Shop", true);
        facade.removeCategory("Dairy", true);

        runQuery();

        Assertions.assertEquals(0, orderSize);
        Assertions.assertEquals(0, supplierSize);

        Assertions.assertEquals(0, reportSize);
        Assertions.assertEquals(0, categorySize);
        Assertions.assertEquals(0, productSize);
        Assertions.assertEquals(0, subCategorySize);
        Assertions.assertEquals(0, subSubCategorySize);
    }

    @Test
    public void testAddItemToOrder() {
        sup = assertSuccess(facade.createSupplier(1, 123, "Lorem",
                true, PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti",
                "050-9954528", "Foo 15, Foobar"));

        order = assertSuccess(facade.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX, OrderType.Periodical));
        facade.addCategory("Dairy");
        facade.addSubCategory("Dairy", "Shop");
        facade.addSubSubCategory("Dairy", "Shop", "10%");

        prod = assertSuccess(facade.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%"));
        Item item = assertSuccess(facade.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12));

        String select_query = "SELECT * FROM Item WHERE supplierPPN=" + item.getSupplier().getPpn();
        Statement st = null;
        ResultSet res_AddItem;
        double catalogNum = 0;
        try {
            st = conn.createStatement();
            res_AddItem = st.executeQuery(select_query);
            while (res_AddItem.next()) {
                catalogNum = res_AddItem.getFloat(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(124, catalogNum);

    }

    @Test
    public void testAddProductWithoutExistingCategory() {
        facade.addCategory(
                "Dairy"
        );
        facade.addSubCategory(
                "Dairy",
                "Shop"
        );
        facade.addSubSubCategory(
                "Dairy",
                "Shop",
                "10%"
        );
        Assertions.assertFalse(facade.addProduct(
                "Milk",
                "Tnoova",
                10.0,
                20,
                10,
                1200,
                "Dairy",
                "Shop",
                "20%" // <-- wrong subsubcat
        ).success);

        String select_query = "SELECT Count(*) FROM Product";
        Statement st = null;
        ResultSet res_prod;
        double res = 0;
        try {
            st = conn.createStatement();
            res_prod = st.executeQuery(select_query);
            while (res_prod.next()) {
                res = res_prod.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(0, res);
    }

    private void runQuery() {
        String subSubCategoryQuery = "SELECT count(*) FROM SubSubCategory",
                orderQuery = "SELECT count(*) FROM `Order`",
                itemInOrderQuery = "SELECT count(*) FROM ItemInOrder",
                quantityDiscountQuery = "SELECT count(*) FROM QuantityDiscount",
                subCategoryQuery = "SELECT count(*) FROM SubCategory",
                categoryQuery = "SELECT count(*) FROM Category",
                supplierQuery = "SELECT count(*) FROM Supplier",
                itemQuery = "SELECT count(*) FROM Item",
                discountPairQuery = "SELECT count(*) FROM DiscountPair",
                productItemQuery = "SELECT count(*) FROM ProductItem",
                productInReportQuery = "SELECT count(*) FROM ProductInReport",
                productItemInReportQuery = "SELECT count(*) FROM ProductItemInReport",
                productQuery = "SELECT count(*) FROM Product",
                reportQuery = "SELECT count(*) FROM Report";

//        Map<String, String> queryMap = new HashMap<String, String>();
//        queryMap.put("categoryQuery","SELECT count(*) FROM Category");
//        queryMap.put("discountPairQuery","SELECT count(*) FROM DiscountPair");
//        queryMap.put("itemQuery","SELECT count(*) FROM Item");
//        queryMap.put("itemInOrderQuery","SELECT count(*) FROM ItemInOrder");
//        queryMap.put("itemInReportQuery" , "SELECT count(*) FROM ItemInReport");
//        queryMap.put("itemReportQuery","SELECT count(*) FROM ItemReport");
//        queryMap.put("orderQuery" ,"SELECT count(*) FROM `Order`");
//        queryMap.put("productQuery" , "SELECT count(*) FROM Product");
//        queryMap.put("productInReportQuery" , "SELECT count(*) FROM ProductInReport");
//        queryMap.put("productItemQuery" , "SELECT count(*) FROM ProductItem");
//        queryMap.put("productReportQuery" , "SELECT count(*) FROM ProductReport");
//        queryMap.put("quantityDiscountQuery" , "SELECT count(*) FROM QuantityDiscount");
//        queryMap.put("subSubCategoryQuery" , "SELECT count(*) FROM SubSubCategory");
//        queryMap.put("subCategoryQuery" , "SELECT count(*) FROM SubCategory");
//        queryMap.put("supplierQuery" , "SELECT count(*) FROM Supplier");

        Statement st;
        ResultSet resCategory, resDiscountPar, resItem, reItemInOrder, resProductItemInReport, resOrder,
                resProduct, resProductInReport, resProductItem, resQuantityDiscount, resSubSubCategory,
                resSubCategory, resSupplier, resReport;
        try {
            st = conn.createStatement();

            resCategory = st.executeQuery(categoryQuery);
            while (resCategory.next()) {
                categorySize = resCategory.getInt(1);
            }

            resDiscountPar = st.executeQuery(discountPairQuery);
            while (resDiscountPar.next()) {
                discountPairSize = resDiscountPar.getInt(1);
            }

            resItem = st.executeQuery(itemQuery);
            while (resItem.next()) {
                itemSize = resItem.getInt(1);
            }

            resReport = st.executeQuery(reportQuery);
            while (resReport.next()) {
                reportSize = resReport.getInt(1);
            }

            reItemInOrder = st.executeQuery(itemInOrderQuery);
            while (reItemInOrder.next()) {
                itemInOrderSize = reItemInOrder.getInt(1);
            }

            resProductItemInReport = st.executeQuery(productItemInReportQuery);
            while (resProductItemInReport.next()) {
                productInReportSize = resProductItemInReport.getInt(1);
            }

            resOrder = st.executeQuery(orderQuery);
            while (resOrder.next()) {
                orderSize = resOrder.getInt(1);
            }

            resProduct = st.executeQuery(productQuery);
            while (resProduct.next()) {
                productSize = resProduct.getInt(1);
            }

            resProductInReport = st.executeQuery(productInReportQuery);
            while (resProductInReport.next()) {
                productInReportSize = resProductInReport.getInt(1);
            }

            resProductItem = st.executeQuery(productItemQuery);
            while (resProductItem.next()) {
                productItem = resProductItem.getInt(1);
            }

            resQuantityDiscount = st.executeQuery(quantityDiscountQuery);
            while (resQuantityDiscount.next()) {
                quantityDiscountSize = resQuantityDiscount.getInt(1);
            }

            resSubSubCategory = st.executeQuery(subSubCategoryQuery);
            while (resSubSubCategory.next()) {
                subSubCategorySize = resSubSubCategory.getInt(1);
            }

            resSubCategory = st.executeQuery(subCategoryQuery);
            while (resSubCategory.next()) {
                subCategorySize = resSubCategory.getInt(1);
            }

            resSupplier = st.executeQuery(supplierQuery);
            while (resSupplier.next()) {
                supplierSize = resSupplier.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateDB() {
        facade.addCategory(
                "Dairy"
        );
        facade.addSubCategory(
                "Dairy",
                "Shop"
        );
        facade.addSubSubCategory(
                "Dairy",
                "Shop",
                "10%"
        );
        prod = assertSuccess(facade.addProduct(
                "Milk",
                "Tnoova",
                10.0,
                20,
                10,
                1200,
                "Dairy",
                "Shop",
                "10%"
        ));
        float NEW_PRICE = 21;
        facade.updateProductCusPrice(prod.getProduct_id(), NEW_PRICE);
        String select_query = "SELECT * FROM Product WHERE id=" + prod.getProduct_id();
        Statement st = null;
        ResultSet res_update;
        double new_price = 0;
        try {
            st = conn.createStatement();
            res_update = st.executeQuery(select_query);
            while (res_update.next()) {
                new_price = res_update.getFloat(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(NEW_PRICE, new_price); //passes
    }


    @Test
    public void testCreateSupplierCard() {
        assertSuccess(facade.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "050-9954528",
                "Foobar Lane 69, Upper Foo"));
    }


    @Test
    public void testAddProductNotWorking() {
        facade.addCategory(
                "Dairy"
        );
        facade.addSubCategory(
                "Dairy",
                "Shop"
        );
        facade.addSubSubCategory(
                "Dairy",
                "Shop",
                "10%"
        );
        Assertions.assertFalse(facade.addProduct(
                "Milk",
                "Tnoova",
                10.0,
                20,
                10,
                1200,
                "Dairies", // <-- wrong category
                "Shop",
                "10%"
        ).success);
        String select_query = "SELECT * FROM Product";
        Statement st = null;
        ResultSet res_select;
        int res_count = 0;
        try {
            st = conn.createStatement();
            res_select = st.executeQuery(select_query);
            while (res_select.next()) {
                res_count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(0, res_count); //passes
    }

    @Test
    public void testAddItemWithoutExistingSupplier() {
        Supplier sup = assertSuccess(facade.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "050-9954528",
                "Foobar Lane 69, Upper Foo"));

        facade.addCategory("Dairy");
        facade.addSubCategory("Dairy", "Shop");
        facade.addSubSubCategory("Dairy", "Shop", "10%");

        int wrongSupplier = sup.getPpn() + 3;
        Assertions.assertFalse(facade.createItem(wrongSupplier, 2, 1, 0.5f).success);
    }

    @Test
    public void testAddItemToNonExistingOrder() {
        Supplier sup = assertSuccess(facade.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "050-9954528",
                "Foobar Lane 69, Upper Foo"));

        Order order = assertSuccess(facade.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX,
                OrderType.Periodical));

        facade.addCategory("Dairy");
        facade.addSubCategory("Dairy", "Shop");
        facade.addSubSubCategory("Dairy", "Shop", "10%");

        prod = assertSuccess(facade.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%"));

        Item item = assertSuccess(facade.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12));

        int wrongOrderId = order.getId() + 3;
        Response response = facade.orderItem(wrongOrderId, sup.getPpn(), item.getCatalogNumber(), 100);
        assertFalse(response.success);
    }

    @Test
    public void testAddQuantityDiscountToItem() throws SQLException {
        Supplier sup = assertSuccess(facade.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "050-9954528",
                "Foobar Lane 69, Upper Foo"));

        Order order = assertSuccess(facade.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX,
                OrderType.Periodical));

        facade.addCategory("Dairy");
        facade.addSubCategory("Dairy", "Shop");
        facade.addSubSubCategory("Dairy", "Shop", "10%");

        prod = assertSuccess(facade.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%"));

        Item item = assertSuccess(facade.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12));

        QuantityDiscount qd = assertSuccess(facade.createDiscount(sup.getPpn(), item.getCatalogNumber()
                , 100, 0.1f));

        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM QuantityDiscount");
        ResultSet resultSet = ps.executeQuery();
        Assertions.assertTrue(resultSet.next());
        Assertions.assertTrue(resultSet.getInt(1) > 0);
    }

    private void assertSuccess(ServiceBase.Response response) {
        assertTrue(response.success, response.error);
    }

    private <T> T assertSuccess(ResponseT<T> responseT) {
        assertTrue(responseT.success, responseT.error);
        return responseT.data;
    }

    private void addToDB() {
        sup = assertSuccess(facade.createSupplier(1, 123, "Lorem",
                true, PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "Moti", "050-9954528", "Foobar Lane 69, Upper Foo"));

        order = assertSuccess(facade.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX, OrderType.Periodical));

        assertSuccess(facade.addCategory("Dairy"));
        assertSuccess(facade.addSubCategory("Dairy", "Shop"));
        assertSuccess(facade.addSubSubCategory("Dairy", "Shop", "10%"));

        prod = assertSuccess(
                facade.addProduct(
                        "Milk", "Tnoova",
                        10.0, 20, 10, 1200,
                        "Dairy", "Shop", "10%"));

        ProductItem pItem = assertSuccess(facade.addItem(prod.getProduct_id(), "TopMarket", "BeerSheva", sup.getPpn(), LocalDate.MAX, true));
        Item item = assertSuccess(facade.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12));
        assertSuccess(facade.orderItem(
                order.getId(),
                sup.getPpn(),
                item.getCatalogNumber(),
                50));
        discount = assertSuccess(facade.createDiscount(sup.getPpn(), item.getCatalogNumber(), 50, 5));

        facade.updateItemCusDiscount(0.1f, LocalDate.now(), LocalDate.MAX, prod.getProduct_id(), pItem.getId());

        missingReport = assertSuccess(facade.createMissingReport("Missing", "Report1"));
        supplierReport = assertSuccess(facade.createBySupplierReport("Supplier", "Report2", sup.getPpn()));
        expiredReport = assertSuccess(facade.createExpiredReport("Expired", "Report3"));
        categoryReport = assertSuccess(facade.createByCategoryReport("Category", "Report4", "CatName", "SubCatName", "SubSubCatName"));
        defectiveReport = assertSuccess(facade.createDefectiveReport("Defective", "Report5"));
        surplusesReport = assertSuccess(facade.createSurplusesReport("Surpluses", "Report6"));
        byProductReport = assertSuccess(facade.createByProductReport("Product", "Report7", "ProName"));
    }
}