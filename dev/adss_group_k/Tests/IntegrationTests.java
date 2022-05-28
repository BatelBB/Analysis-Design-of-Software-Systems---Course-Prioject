package adss_group_k.Tests;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.PaymentCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DayOfWeek;

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
    public void deleteDB() {
        service.createSupplier(1,123,"Lorem", true, PaymentCondition.Credit,
                DayOfWeek.SUNDAY, "Moti", "0509954528", "B@Gmail.com");

    }

    @Test
    public void loadDB() {
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