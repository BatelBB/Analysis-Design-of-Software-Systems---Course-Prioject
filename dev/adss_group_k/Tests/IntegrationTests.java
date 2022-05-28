package adss_group_k.Tests;

import adss_group_k.BusinessLayer.Inventory.ProductItem;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Report;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Order;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.OrderType;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

    private ISupplierService service;
    private Service inventory;
    private QuantityDiscount discount;
    private Order order;
    private Supplier sup;

    private Report missingReport;
    private Report supplierReport;
    private Report expiredReport;
    private Report categoryReport;
    private Report defectiveReport;
    private Report surplusesReport;
    private Report byProductReport;

    private Product prod;
    private ProductItem pItem;

    int categorySize = 0, discountPairSize = 0, itemSize = 0, itemInOrderSize = 0, reportSize = 0,
            orderSize = 0, productSize = 0, productInReportSize = 0, productItem = 0,
            quantityDiscountSize = 0, subSubCategorySize = 0, subCategorySize = 0,
            supplierSize = 0;

    Connection conn = null;

    @BeforeEach
    void setUp() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            SchemaInit.init(conn);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        PersistenceController dal = new PersistenceController(conn);
        service = new SupplierService(dal);
        inventory = new Service(service, dal);


    }

    @Test
    public void loadDB() {
        sup = service.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528",
                "B@Gmail.com").data;

        order = service.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX,
                OrderType.Periodical).data;

        inventory.addCategory("Dairy");
        inventory.addSubCategory("Dairy", "Shop");
        inventory.addSubSubCategory("Dairy", "Shop", "10%");

        prod = inventory.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%").data;

        pItem = inventory.addItem(prod.getProduct_id(), "TopMarket", "BeerSheva", sup.getPpn(), LocalDate.MAX,
                true).data;
        Item item = service.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12).data;
        service.orderItem(order.getId(), sup.getPpn(), item.getCatalogNumber(), 50);
        discount = service.createDiscount(sup.getPpn(), item.getCatalogNumber(), 50, 5).data;

        inventory.updateItemCusDiscount(0.1f, LocalDate.now(), LocalDate.MAX, prod.getProduct_id(), pItem.getId());

        missingReport = inventory.createMissingReport("Missing", "Report1").data;
        supplierReport = inventory.createBySupplierReport("Supplier", "Report2", 10).data;
        expiredReport = inventory.createExpiredReport("Expired", "Report3").data;
        categoryReport = inventory.createByCategoryReport("Category", "Report4", "CatName",
                "SubCatName", "SubSubCatName").data;
        defectiveReport = inventory.createDefectiveReport("Defective", "Report5").data;
        surplusesReport = inventory.createSurplusesReport("Surpluses", "Report6").data;
        byProductReport = inventory.createByProductReport("Product", "Report7", "ProName").data;

        runQuery();
        assertNotEquals(0, categorySize); //passes
        assertNotEquals(0, discountPairSize); //passes
        assertNotEquals(0, itemSize); //passes
        assertNotEquals(0, itemInOrderSize); //passes
        assertNotEquals(0, orderSize); //passes
        assertNotEquals(0, productSize); //passes
        assertNotEquals(0, productInReportSize);//passes
        assertNotEquals(0, productItem); //passes
        assertNotEquals(0, reportSize); //passes
        assertNotEquals(0, quantityDiscountSize); //passes
        assertNotEquals(0, subSubCategorySize); //passes
        assertNotEquals(0, subCategorySize); //passes
        assertNotEquals(0, supplierSize); //passes
    }

    @Test
    public void deleteDB() {
        loadDB();

        service.deleteDiscount(discount);
        service.deleteOrder(order.getId());
        service.deleteSupplier(sup.getPpn());

        inventory.removeReport(missingReport.getId());
        inventory.removeReport(byProductReport.getId());
        inventory.removeReport(categoryReport.getId());
        inventory.removeReport(defectiveReport.getId());
        inventory.removeReport(supplierReport.getId());
        inventory.removeReport(expiredReport.getId());
        inventory.removeReport(surplusesReport.getId());
        inventory.removeCategory("Dairy");
        inventory.removeItem(prod.getProduct_id(), pItem.getId());
        inventory.removeProduct(prod.getProduct_id());
        inventory.removeSubCategory("Dairy", "Shop");
        inventory.removeSubSubCategory("Dairy", "Shop", "10%");

        runQuery();
        assertEquals(0, reportSize);
        assertEquals(0, categorySize);
        assertEquals(0, itemSize);


    }


    @Test
    public void testCreateOrder() {
    }


    @Test
    public void testAddProductWithoutExistingSupplier() {
    }

    private void runQuery() {
        String categoryQuery = "SELECT count(*) FROM Category", discountPairQuery = "SELECT count(*) FROM DiscountPair",
                itemQuery = "SELECT count(*) FROM Item", itemInOrderQuery = "SELECT count(*) FROM ItemInOrder",
                orderQuery = "SELECT count(*) FROM `Order`",
                productQuery = "SELECT count(*) FROM Product",
                productInReportQuery = "SELECT count(*) FROM ProductInReport",
                productItemQuery = "SELECT count(*) FROM ProductItem",
                productItemInReportQuery = "SELECT count(*) FROM ProductItemInReport",
                quantityDiscountQuery = "SELECT count(*) FROM QuantityDiscount",
                subSubCategoryQuery = "SELECT count(*) FROM SubSubCategory",
                subCategoryQuery = "SELECT count(*) FROM SubCategory", supplierQuery = "SELECT count(*) FROM Supplier",
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

        Statement st = null;
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
        assertNotEquals(0, categorySize); //passes
        assertNotEquals(0, discountPairSize); //passes
        assertNotEquals(0, itemSize); //passes
        assertNotEquals(0, itemInOrderSize); //passes
        assertNotEquals(0, orderSize); //passes
        assertNotEquals(0, productSize); //passes
        assertNotEquals(0, productInReportSize);//passes
        assertNotEquals(0, productItem); //passes
        assertNotEquals(0, reportSize); //passes
        assertNotEquals(0, quantityDiscountSize); //passes
        assertNotEquals(0, subSubCategorySize); //passes
        assertNotEquals(0, subCategorySize); //passes
        assertNotEquals(0, supplierSize); //passes
    }

    @Test
    public void updateDB() {
        inventory.addCategory(
                "Dairy"
        );
        inventory.addSubCategory(
                "Dairy",
                "Shop"
        );
        inventory.addSubSubCategory(
                "Dairy",
                "Shop",
                "10%"
        );
        Product prod = inventory.addProduct(
                "Milk",
                "Tnoova",
                10.0,
                20,
                10,
                1200,
                "Dairy",
                "Shop",
                "10%"
        ).data;
        double NEW_PRICE = 21;
        inventory.updateProductCusPrice(prod.getProduct_id(), NEW_PRICE);
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
        assertEquals(NEW_PRICE, new_price); //passes
    }


    @Test
    public void testCreateSupplierCard() {
        assertTrue(service.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528",
                "B@Gmail.com").success);
    }


    @Test
    public void testAddProductNotWorking() {

    }

    @Test
    public void testAddItemWithoutExistingSupplier() {
        Supplier sup = service.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528",
                "B@Gmail.com").data;

        inventory.addCategory("Dairy");
        inventory.addSubCategory("Dairy", "Shop");
        inventory.addSubSubCategory("Dairy", "Shop", "10%");

        int wrongSupplier = sup.getPpn() + 3;
        assertFalse(service.createItem(wrongSupplier, 2, 1, 0.5f).success);
    }

    @Test
    public void testAddItemToNonExistingOrder() {
        Supplier sup = service.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528",
                "B@Gmail.com").data;

        Order order = service.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX,
                OrderType.Periodical).data;

        inventory.addCategory("Dairy");
        inventory.addSubCategory("Dairy", "Shop");
        inventory.addSubSubCategory("Dairy", "Shop", "10%");

        Product prod = inventory.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%").data;

        Item item = service.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12).data;

        int wrongOrderId = order.getId() + 3;
        Response response = service.orderItem(wrongOrderId, sup.getPpn(), item.getCatalogNumber(), 100);
        assertFalse(response.success);
    }

    @Test
    public void testAddQuantityDiscountToItem() throws SQLException {
        Supplier sup = service.createSupplier(1, 123, "Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528",
                "B@Gmail.com").data;

        Order order = service.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.MAX,
                OrderType.Periodical).data;

        inventory.addCategory("Dairy");
        inventory.addSubCategory("Dairy", "Shop");
        inventory.addSubSubCategory("Dairy", "Shop", "10%");

        Product prod = inventory.addProduct("Milk", "Tnoova", 10.0, 20, 10,
                1200, "Dairy", "Shop", "10%").data;

        Item item = service.createItem(sup.getPpn(), 124, prod.getProduct_id(), 12).data;

        QuantityDiscount qd = service.createDiscount(sup.getPpn(), item.getCatalogNumber()
                , 100, 0.1f).getOrThrow(RuntimeException::new);

        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM QuantityDiscount");
        ResultSet resultSet = ps.executeQuery();
        assertTrue(resultSet.next());
        assertTrue(resultSet.getInt(1) > 0);
    }
}