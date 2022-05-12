package adss_group_k.inventory.tests;

import adss_group_k.inventory.ServiceLayer.Objects.*;
import adss_group_k.inventory.ServiceLayer.Service;
import adss_group_k.suppliers.BusinessLayer.Service.SupplierService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private static adss_group_k.inventory.ServiceLayer.Service InventoryService;
    private static adss_group_k.suppliers.BusinessLayer.Service.SupplierService SuppliersService;

    private static List<String> CategoryList;
    private static List<String> ProductListNames;
    private static List<Integer> ReportList;

    static void setService() {
        SuppliersService=new SupplierService();
        InventoryService = new Service(SuppliersService);
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
        InventoryService.addCategory("Dairy Products");
        InventoryService.addSubCategory("Dairy Products","Milks");
        InventoryService.addSubSubCategory("Dairy Products","Milks","Cow Milk");
    }
    @AfterEach
    void resetData(){
        InventoryService.removeCategory("Dairy Products");
        InventoryService.removeSubCategory("Dairy Products","Milks");
        InventoryService.removeSubSubCategory("Dairy Products","Milks","Cow Milk");
    }

    void restartService(){
        InventoryService.restart();
    }

    @Before
    void setCategoryList() {
        CategoryList = InventoryService.getCategoriesNames().value;
    }

    @After
    void clearCategoryList() {
        CategoryList.clear();
    }

    @org.junit.jupiter.api.Test
    void addCategory() {
        try {
            assertTrue(CategoryList.isEmpty());
            InventoryService.addCategory("Dairy Products");
            setCategoryList();
            assertTrue(CategoryList.contains("Dairy Products"));
            InventoryService.addCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The categories already exists in the system");
        }
    }

    @org.junit.jupiter.api.Test
    void removeCategory() {
        try {
            InventoryService.addCategory("Dairy Products");
            setCategoryList();
            InventoryService.removeCategory("Dairy Products");
            setCategoryList();
            assertFalse(CategoryList.contains("Dairy Products"));
            InventoryService.removeCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }

    @org.junit.jupiter.api.Test
    void getCategory() {
        try {
            InventoryService.addCategory("Dairy Products");
            setCategoryList();
            Category category = InventoryService.getCategory("Dairy Products").value;
            assertEquals(category.getName(), "Dairy Products");
            InventoryService.removeCategory("Dairy Products");
            setCategoryList();
            InventoryService.getCategory("Dairy Products");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }

    @Before
    void setProductIdes() {
        ProductListNames = InventoryService.getProductIdes().value;
    }

    @After
    void clearProductIdes() {
        ProductListNames.clear();
    }


    @org.junit.jupiter.api.Test
    void addProduct() {
        try {
            assertTrue(ProductListNames.isEmpty());
            InventoryService.addProduct("Milk", "Tnova", 4, 5.9, 350, 6, "Dairy Products","Milks","Cow Milk");
            setProductIdes();
            assertTrue(ProductListNames.contains("Milk"));
            setProductIdes();
            InventoryService.addProduct("", "Tnova", 4, 5.9, 350, 6, "Dairy Products","Milks","Cow Milk");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "product name empty");
            restartService();
        }
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        try {
            InventoryService.addProduct("Milk", "Tnova", 4, 5.9, 350, 6, "Dairy Products","Milks","Cow Milk");
            setProductIdes();
            assertTrue(ProductListNames.contains("Milk"));
            InventoryService.removeProduct(0);
            setProductIdes();
            assertFalse(ProductListNames.contains("Milk"));
            InventoryService.removeProduct(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "product id does not exist");
            clearProductIdes();
            restartService();
        }
    }

    @Before
    void setReportListNames() {
        ReportList = InventoryService.getReportListNames().value;
    }

    @After
    void clearReportListNames() {
        ReportList.clear();
    }

    @org.junit.jupiter.api.Test
    void removeReport() {
        try {
            InventoryService.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            InventoryService.removeReport(0);
            setReportListNames();
            assertFalse(ProductListNames.contains("MissingReport"));
            InventoryService.removeProduct(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Report id doesn't exists");
        }
    }

    @org.junit.jupiter.api.Test
    void getReport() {
        try {
            InventoryService.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            Report report = InventoryService.getReport(0).value;
            assertEquals(report.getName(), "MissingReport");
            InventoryService.removeReport(0);
            setReportListNames();
            InventoryService.getReport(0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Report id doesn't exists");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createMissingReport() {
        try {
            InventoryService.createMissingReport("MissingReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            assertTrue(InventoryService.createMissingReport("MissingReport","Michel").value instanceof MissingReport);
            InventoryService.removeReport(1);
            InventoryService.createMissingReport("MissingReport", "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createExpiredReport() {
        try {
            InventoryService.createExpiredReport("ExpiredReport", "Michel");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            assertTrue(InventoryService.createExpiredReport("ExpiredReport", "Michel").value instanceof ExpiredReport);
            InventoryService.removeReport(1);
            InventoryService.createExpiredReport("ExpiredReport", "Michel");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createBySupplierReport() {
        try {
            InventoryService.createBySupplierReport("BySupplierReport", "Michel", "Tnuva");
            setReportListNames();
            assertTrue(ReportList.contains(0));
            assertTrue(InventoryService.createBySupplierReport("MissingReport", "Michel", "Tnuva").value instanceof bySupplierReport);
            InventoryService.removeReport(1);
            InventoryService.createBySupplierReport("ExpiredReport", "Michel", "Tnuva");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }
}