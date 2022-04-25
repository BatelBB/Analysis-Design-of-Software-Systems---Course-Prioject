package ServiceLayer;

import ServiceLayer.Objects.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private static Service service;
    private static List<String> CategoryList;
    private static List<String> ProductListNames;
    private static List<Integer> ReportList;

    static void setService(){ service= new Service(); }

    @BeforeAll
    static void setUp(){
        setService();
        CategoryList=new LinkedList<>();
        ProductListNames = new LinkedList<>();
        ReportList = new LinkedList<>();
    }

    @Before
    void setCategoryList(){
        CategoryList = service.getCategoriesNames();
    }
    @After
    void clearCategoryList(){
        CategoryList.clear();
    }
    @org.junit.jupiter.api.Test
    void addCategory() {
        try {
            Assert.assertEquals(CategoryList.isEmpty(),true);
            service.addCategory("Dairy Products");
            setCategoryList();
            Assert.assertEquals(CategoryList.contains("Dairy Products"), true);
            service.addCategory("Dairy Products");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The categories already exists in the system");
        }
    }

    @org.junit.jupiter.api.Test
    void removeCategory() {
        try {
            service.addCategory("Dairy Products");
            setCategoryList();
            service.removeCategory("Dairy Products");
            setCategoryList();
            Assert.assertEquals(CategoryList.contains("Dairy Products"), false);
            service.removeCategory("Dairy Products");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }

    @org.junit.jupiter.api.Test
    void getCategory() {
        try {
            service.addCategory("Dairy Products");
            setCategoryList();
            Category category=service.getCategory("Dairy Products");
            Assert.assertEquals(category.getName(), "Dairy Products");
            service.removeCategory("Dairy Products");
            setCategoryList();
            service.getCategory("Dairy Products");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Category doesn't exists");
            clearCategoryList();
        }
    }
    @Before
    void setProductIdes(){
        ProductListNames = service.getProductIdes();
    }
    @After
    void clearProductIdes(){
        ProductListNames.clear();
    }
    @org.junit.jupiter.api.Test
    void addProduct() {
        try {
            Assert.assertEquals(ProductListNames.isEmpty(),true);
            service.addProduct("Milk","Tnova",4,5.9,350,6);
            setProductIdes();
            Assert.assertEquals(ProductListNames.contains("Milk"), true);
            setProductIdes();
            service.addProduct("","Tnova",4,5.9,350,6);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "product name empty");
        }
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        try {
            service.addProduct("Milk","Tnova",4,5.9,350,6);
            setProductIdes();
            Assert.assertEquals(ProductListNames.contains("Milk"), true);
            service.removeProduct(0);
            setProductIdes();
            Assert.assertEquals(ProductListNames.contains("Milk"), false);
            service.removeProduct(0);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "product id does not exist");
            clearProductIdes();
        }
    }
    @Before
    void setReportListNames(){
        ReportList = service.getReportListNames();
    }
    @After
    void clearReportListNames(){
        ReportList.clear();
    }
    @org.junit.jupiter.api.Test
    void removeReport() {
        try {
            service.createMissingReport("MissingReport",0,"Michel");
            setReportListNames();
            Assert.assertEquals(ReportList.contains(0), true);
            service.removeReport(0);
            setReportListNames();
            Assert.assertEquals(ProductListNames.contains("MissingReport"), false);
            service.removeProduct(0);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Report id doesn't exists");
        }
    }

    @org.junit.jupiter.api.Test
    void getReport() {
        try {
            service.createMissingReport("MissingReport",0,"Michel");
            setReportListNames();
            Report report =service.getReport(0);
            Assert.assertEquals(report.getName(), "MissingReport");
            service.removeReport(0);
            setReportListNames();
            service.getReport(0);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Report id doesn't exists");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createMissingReport() {
        try {
            service.createMissingReport("MissingReport", 0, "Michel");
            setReportListNames();
            Assert.assertEquals(ReportList.contains(0),true);
            Assert.assertEquals(service.createMissingReport("MissingReport", 1, "Michel") instanceof MissingReport, true);
            service.removeReport(1);
            service.createMissingReport("MissingReport", 0, "Michel");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createExpiredReport() {
        try {
            service.createExpiredReport("ExpiredReport", 0, "Michel");
            setReportListNames();
            Assert.assertEquals(ReportList.contains(0),true);
            Assert.assertEquals(service.createExpiredReport("ExpiredReport", 1, "Michel") instanceof ExpiredReport, true);
            service.removeReport(1);
            service.createExpiredReport("ExpiredReport", 0, "Michel");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }

    @org.junit.jupiter.api.Test
    void createBySupplierReport() {
        try {
            service.createBySupplierReport("BySupplierReport", 0, "Michel", "Tnuva");
            setReportListNames();
            Assert.assertEquals(ReportList.contains(0),true);
            Assert.assertEquals(service.createBySupplierReport("MissingReport", 1, "Michel", "Tnuva") instanceof bySupplierReport, true);
            service.removeReport(1);
            service.createBySupplierReport("ExpiredReport", 0, "Michel", "Tnuva");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            clearReportListNames();
        }
    }
}