package adss_group_k.Tests;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.OrderType;
import adss_group_k.dataLayer.records.PaymentCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntegrationTests {

    private ISupplierService service;
    private Service inventory;

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
        Supplier sup = service.createSupplier(1,123,"Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528", "B@Gmail.com").data;
        Product prod = inventory.addProduct("Milk","Tnoova",10.0, 20, 10,
                1200, "Dairy","Shop", "10%").data;
        Item item = service.createItem(sup.getPpn(),124,prod.getProduct_id(), 12).data;
        service.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.of(2022,04,07),
                OrderType.Periodical);
        service.createDiscount(sup.getPpn(),item.getCatalogNumber(), 50, 5);

        inventory.createMissingReport("Missing", "Report");
        inventory.createBySupplierReport("Supplier", "Report", 10);
        inventory.createExpiredReport("Expired", "Report");
        inventory.createByCategoryReport("Category", "Report","CatName",
                "SubCatName", "SubSubCatName");
        inventory.createDefectiveReport("Defective", "Report");
        inventory.createSurplusesReport("Surpluses", "Report");
        inventory.createByProductReport("Product", "Report", "ProName");
        inventory.addCategory("Store");
        inventory.addSubSubCategory("Store","Shop", "TopMarket");
        inventory.addItem(prod.getProduct_id(),"TopMarket", "BeerSheva", sup.getPpn(), LocalDate.MAX,
                true);
        inventory.addSubCategory("Store","Shop");

        int categorySize = 0, discountPairSize = 0, itemSize = 0, itemInOrderSize = 0, itemInReportSize = 0,
                itemReportSize = 0, orderSize = 0, productSize = 0, productInReportSize = 0, productItem = 0,
                productReportSize = 0, quantityDiscountSize = 0, subSubCategorySize = 0, subCategorySize = 0,
                supplierSize = 0;

        String categoryQuery = "SELECT count(*) FROM Category", discountPairQuery = "SELECT count(*) FROM DiscountPair",
                itemQuery = "SELECT count(*) FROM Item", itemInOrderQuery = "SELECT count(*) FROM ItemInOrder",
                itemInReportQuery = "SELECT count(*) FROM ItemInReport",
                itemReportQuery = "SELECT count(*) FROM ItemReport", orderQuery = "SELECT count(*) FROM `Order`",
                productQuery = "SELECT count(*) FROM Product",
                productInReportQuery = "SELECT count(*) FROM ProductInReport",
                productItemQuery = "SELECT count(*) FROM ProductItem",
                productReportQuery = "SELECT count(*) FROM ProductReport",
                quantityDiscountQuery = "SELECT count(*) FROM QuantityDiscount",
                subSubCategoryQuery = "SELECT count(*) FROM SubSubCategory",
                subCategoryQuery = "SELECT count(*) FROM SubCategory", supplierQuery = "SELECT count(*) FROM Supplier";

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
        ResultSet resCategory, resDiscountPar, resItem, reItemInOrder, resItemInReport, resItemReport, resOrder,
                resProduct, resProductInReport, resProductItem, resProductReport, resQuantityDiscount, resSubSubCategory,
                resSubCategory, resSupplier;
        try{
            st = conn.createStatement();
            resCategory = st.executeQuery(categoryQuery);
            while (resCategory.next()){
                categorySize = resCategory.getInt(1);
            }
            resDiscountPar = st.executeQuery(discountPairQuery);
            while (resDiscountPar.next()){
                discountPairSize = resDiscountPar.getInt(1);
            }
            resItem = st.executeQuery(itemQuery);
            while (resItem.next()){
                itemSize = resItem.getInt(1);
            }
            reItemInOrder = st.executeQuery(itemInOrderQuery);
            while (reItemInOrder.next()){
                itemInOrderSize = reItemInOrder.getInt(1);
            }
            resItemInReport = st.executeQuery(itemInReportQuery);
            while (resItemInReport.next()){
                itemInReportSize = resItemInReport.getInt(1);
            }
            resItemReport = st.executeQuery(itemReportQuery);
            while (resItemReport.next()){
                itemReportSize = resItemReport.getInt(1);
            }
            resOrder = st.executeQuery(orderQuery);
            while (resOrder.next()){
                orderSize = resOrder.getInt(1);
            }
            resProduct = st.executeQuery(productQuery);
            while (resProduct.next()){
                productSize = resProduct.getInt(1);
            }
            resProductInReport = st.executeQuery(productInReportQuery);
            while (resProductInReport.next()){
                productInReportSize = resProductInReport.getInt(1);
            }
            resProductItem = st.executeQuery(productItemQuery);
            while (resProductItem.next()){
                productItem = resProductItem.getInt(1);
            }
            resProductReport = st.executeQuery(productReportQuery);
            while (resProductReport.next()){
                productReportSize = resProductReport.getInt(1);
            }
            resQuantityDiscount = st.executeQuery(quantityDiscountQuery);
            while (resQuantityDiscount.next()){
                quantityDiscountSize = resQuantityDiscount.getInt(1);
            }
            resSubSubCategory = st.executeQuery(subSubCategoryQuery);
            while (resSubSubCategory.next()){
                subSubCategorySize = resSubSubCategory.getInt(1);
            }
            resSubCategory = st.executeQuery(subCategoryQuery);
            while (resSubCategory.next()){
                subCategorySize = resSubCategory.getInt(1);
            }
            resSupplier = st.executeQuery(supplierQuery);
            while (resSupplier.next()){
                supplierSize = resSupplier.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertNotEquals(0,categorySize);
        assertNotEquals(0,discountPairSize);
        assertNotEquals(0,itemSize);
        assertNotEquals(0,itemInOrderSize);
        assertNotEquals(0,itemInReportSize);
        assertNotEquals(0,itemReportSize);
        assertNotEquals(0,orderSize);
        assertNotEquals(0,productSize);
        assertNotEquals(0,productInReportSize);
        assertNotEquals(0,productItem);
        assertNotEquals(0,productReportSize);
        assertNotEquals(0,quantityDiscountSize);
        assertNotEquals(0,subSubCategorySize);
        assertNotEquals(0,subCategorySize);
        assertNotEquals(0,supplierSize);
    }

    @Test
    public void deleteDB() {
        Supplier sup = service.createSupplier(1,123,"Lorem", true,
                PaymentCondition.Credit, DayOfWeek.SUNDAY, "Moti", "0509954528", "B@Gmail.com").data;
        Product prod = inventory.addProduct("Milk","Tnoova",10.0, 20, 10,
                1200, "Dairy","Shop", "10%").data;
        Item item = service.createItem(sup.getPpn(),124,prod.getProduct_id(), 12).data;
        service.createOrder(sup.getPpn(), LocalDate.now(), LocalDate.of(2022,04,07),
                OrderType.Periodical);
        service.createDiscount(sup.getPpn(),item.getCatalogNumber(), 50, 5);

        inventory.createMissingReport("Missing", "Report");
        inventory.createBySupplierReport("Supplier", "Report", 10);
        inventory.createExpiredReport("Expired", "Report");
        inventory.createByCategoryReport("Category", "Report","CatName",
                "SubCatName", "SubSubCatName");
        inventory.createDefectiveReport("Defective", "Report");
        inventory.createSurplusesReport("Surpluses", "Report");
        inventory.createByProductReport("Product", "Report", "ProName");
        inventory.addCategory("Store");
        inventory.addSubSubCategory("Store","Shop", "TopMarket");
        inventory.addItem(prod.getProduct_id(),"TopMarket", "BeerSheva", sup.getPpn(), LocalDate.MAX,
                true);
        inventory.addSubCategory("Store","Shop");


    }





    @Test
    public void updateDB() {
    }


    @Test
    public void testCreateOrder() {
    }


    @Test
    public void testCreateSupplierCard() {
    }


    @Test
    public void testAddProductNotWorking() {
    }


    @Test
    public void testAddProductWithoutExistingSupplier() {
    }

    @Test
    public void testAddItemToNonExistingOrder(){

    }

    @Test
    public void testAddQuantityDiscountToItem(){

    }

    @Test
    public void testAddItemWithNoExistingProduct(){

    }
}