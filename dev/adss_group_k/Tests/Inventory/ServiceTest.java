package adss_group_k.Tests.Inventory;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private static Service service;
    private static ISupplierService supplierService;
    private static List<String> CategoryList;
    private static List<String> ProductListNames;
    private static List<Integer> ReportList;
    private static PersistenceController pc;

    static void setService() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            SchemaInit.init(conn);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        pc = new PersistenceController(conn);
        supplierService = new SupplierService(pc);
        service = new Service(supplierService, pc);
    }

    @BeforeEach
    void setData(){
        setService();

        CategoryList = new LinkedList<>();
        ProductListNames = new LinkedList<>();

        ReportList = new LinkedList<>();
        service.addCategory("Dairy Products");
        service.addSubCategory("Dairy Products","Milks");
        service.addSubSubCategory("Dairy Products","Milks","Cow Milk");
    }
    @AfterEach
    void resetData(){
        service.removeCategory("Dairy Products");
        service.removeSubCategory("Dairy Products","Milks");
        service.removeSubSubCategory("Dairy Products","Milks","Cow Milk");
    }

    void restartService(){
        service.restart();
    }

    @Before
    void setCategoryList() {
        CategoryList = service.getCategoriesNames().data;
    }

    @After
    void clearCategoryList() {
        CategoryList.clear();
    }

    @Test
    void addCategory() {
        try {
            assertTrue(CategoryList.isEmpty());
            service.addCategory("Dairy Products");
            setCategoryList();
            assertTrue(CategoryList.contains("Dairy Products"));
            service.addCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The categories already exists in the system");
        }
    }

    @Test
    void removeCategory() {
        try {
            service.addCategory("Dairy Products");
            setCategoryList();
            service.removeCategory("Dairy Products");
            setCategoryList();
            assertFalse(CategoryList.contains("Dairy Products"));
            service.removeCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }

    @Test
    void getCategory() {
        try {
            service.addCategory("Dairy Products");
            setCategoryList();
            Category category = service.getCategory("Dairy Products").data;
            assertEquals(category.getName(), "Dairy Products");
            service.removeCategory("Dairy Products");
            setCategoryList();
            service.getCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }

    @Before
    void setProductIdes() {
        ProductListNames = service.getProductIdes().data;
    }

    @After
    void clearProductIdes() {
        ProductListNames.clear();
    }


    @Test
    void addProduct() {
        try {
            assertTrue(ProductListNames.isEmpty());
            service.addProduct("Milk", "Tnova", 4, 5.9f, 350, 6, "Dairy Products","Milks","Cow Milk");
            setProductIdes();
            assertTrue(ProductListNames.contains("Milk"));
            setProductIdes();
            service.addProduct("", "Tnova", 4, 5.9f, 350, 6, "Dairy Products","Milks","Cow Milk");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "product name empty");
            restartService();
        }
    }

    @Test
    void removeProduct() {
        try {
            service.addProduct("Milk", "Tnova", 4, 5.9f, 350, 6, "Dairy Products","Milks","Cow Milk");
            setProductIdes();
            assertTrue(ProductListNames.contains("Milk"));
            service.removeProduct(0);
            setProductIdes();
            assertFalse(ProductListNames.contains("Milk"));
            service.removeProduct(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "product id does not exist");
            clearProductIdes();
            restartService();
        }
    }

    @Before
    void setReportListNames() {
        ReportList = service.getReportListNames().data;
    }

    @After
    void clearReportListNames() {
        ReportList.clear();
    }

    @Test
    void removeReport() {
        try {
            service.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.removeReport(0);
            setReportListNames();
            assertFalse(ProductListNames.contains("MissingReport"));
            service.removeProduct(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Report id doesn't exists");
        }
    }

    @Test
    void getReport() {
        try {
            service.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            Report report = service.getReport(0).data;
            assertEquals(report.getName(), "MissingReport");
            service.removeReport(0);
            setReportListNames();
            service.getReport(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Report id doesn't exists");
            clearReportListNames();
        }
    }

    @Test
    void createMissingReport() {
        try {
            service.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createMissingReport("MissingReport", "Michel");
            service.removeReport(1);
            service.createMissingReport("MissingReport", "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
        //TODO: make new test
    }

    @Test
    void createExpiredReport() {
        try {
            service.createExpiredReport("ExpiredReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createExpiredReport("ExpiredReport", "Michel");
            service.removeReport(1);
            service.createExpiredReport("ExpiredReport", "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
        //TODO: make new test
    }

    @Test
    void createBySupplierReport() {
        try {
            service.createBySupplierReport("BySupplierReport", "Michel", 0);
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createBySupplierReport("MissingReport", "Michel", 0);
            service.removeReport(1);
            service.createBySupplierReport("ExpiredReport", "Michel", 0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }
}