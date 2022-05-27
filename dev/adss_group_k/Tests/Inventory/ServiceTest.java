package adss_group_k.Tests.Inventory;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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

    static void setService() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        supplierService = new SupplierService(conn);
        service = new Service(supplierService);
    }

    @BeforeAll
    static void setUp() {
        setService();
        CategoryList = new LinkedList<>();
        ProductListNames = new LinkedList<>();
        ReportList = new LinkedList<>();

    }
    @BeforeEach
    void setData(){
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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


    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
    void removeReport() {
        try {
            service.createMissingReport("MissingReport", 0, "Michel");
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

    @org.junit.jupiter.api.Test
    void getReport() {
        try {
            service.createMissingReport("MissingReport", 0, "Michel");
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

    @org.junit.jupiter.api.Test
    void createMissingReport() {
        try {
            service.createMissingReport("MissingReport", 0, "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createMissingReport("MissingReport", 1, "Michel");
            service.removeReport(1);
            service.createMissingReport("MissingReport", 0, "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createExpiredReport() {
        try {
            service.createExpiredReport("ExpiredReport", 0, "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createExpiredReport("ExpiredReport", 1, "Michel");
            service.removeReport(1);
            service.createExpiredReport("ExpiredReport", 0, "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createBySupplierReport() {
        try {
            service.createBySupplierReport("BySupplierReport", 0, "Michel", "Tnuva");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            service.createBySupplierReport("MissingReport", 1, "Michel", "Tnuva");
            service.removeReport(1);
            service.createBySupplierReport("ExpiredReport", 0, "Michel", "Tnuva");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }
}