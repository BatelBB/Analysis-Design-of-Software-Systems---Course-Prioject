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

public class IntegrationTests {

    private ISupplierService service;
    private Service inventory;

    @BeforeEach
    void setUp() {
        Connection conn = null;
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

        ResultSet result = statement.executeQuery(query);
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