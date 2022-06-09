package groupk.shared.Tests.Inventory;

import adss_group_k.BusinessLayer.Inventory.Categories.Category;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import groupk.shared.Tests.TestsBase;

import adss_group_k.serviceLayer.ServiceBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static adss_group_k.serviceLayer.ServiceBase.*;

class ServiceTest extends TestsBase {

    @Test
    void addCategory() {
        try {
            Assertions.assertTrue(inventory.getCategoriesNames().data.isEmpty());
            inventory.addCategory("Dairy Products");
            Assertions.assertTrue(inventory.getCategoriesNames().data.contains("Dairy Products"));
            inventory.addCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The categories already exists in the system");
        }
    }

    @Test
    void removeCategory() {
        try {
            inventory.addCategory("Dairy Products");
            inventory.removeCategory("Dairy Products");
            Assertions.assertFalse(inventory.getCategoriesNames().data.contains("Dairy Products"));
            inventory.removeCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Category doesn't exists");
        }
    }

    @Test
    void getCategory() {
        try {
            ServiceBase.Response dairy_products = inventory.addCategory("Dairy Products");
            Category category = assertSuccess(inventory.getCategory("Dairy Products"));
            Assertions.assertEquals(category.getName(), "Dairy Products");
            inventory.removeCategory("Dairy Products");
            inventory.getCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Category doesn't exists");
        }
    }

    private <T> T assertSuccess(ServiceBase.ResponseT<T> response) {
        assertTrue(response.success);
        return response.data;
    }

    @Test
    void addProduct() {
        try {
            Assertions.assertTrue(inventory.getProductNames().data.isEmpty());
            initCategories();
            inventory.addProduct("Milk", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products","Milks","Cow Milk");
            Assertions.assertTrue(inventory.getProductNames().data.contains("Milk"));
            inventory.addProduct("", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products","Milks","Cow Milk");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "product name empty");
        }
    }

    private void initCategories() {
        inventory.addCategory("Dairy Products");
        inventory.addSubCategory("Dairy Products", "Milks");
        inventory.addSubSubCategory("Dairy Products", "Milks", "Cow Milk");
    }

    @Test
    void removeProduct() {
        try {
            initCategories();
            String productName = inventory.addProduct("Milk", "Tnova", 4, 5.9f,
                    350, 6, "Dairy Products","Milks","Cow Milk")
                    .data.getName();
            Assertions.assertTrue(inventory.getProductNames().data.contains("Milk"));
            inventory.removeProduct(1);
            Assertions.assertFalse(inventory.getProductNames().data.contains("Milk"));
            inventory.removeProduct(0);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "product id does not exist");
        }
    }

    @Test
    void removeReport() {
            ResponseT<Report> missingReport = inventory.createMissingReport("MissingReport", "Michel");
            int reportId = missingReport.data.getId();
            Assertions.assertTrue(inventory.getReportListIds().data.contains(reportId));
            Assertions.assertTrue(inventory.removeReport(reportId).success);
            Assertions.assertFalse(inventory.getProductNames().data.contains(reportId));
            Assertions.assertFalse(inventory.removeProduct(reportId).success);
    }

    @Test
    void getReport() {
        try {
            Integer id = inventory.createMissingReport("MissingReport", "Michel").data.getId();
            Report report = assertSuccess(inventory.getReport(id));
            Assertions.assertEquals(report.getName(), "MissingReport");
            inventory.removeReport(id);
            inventory.getReport(id);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Report id doesn't exists");
        }
    }

    @Test
    void createMissingReport() {
        try {
            inventory.createMissingReport("MissingReport", "Michel");
            
            Assertions.assertTrue(inventory.getReportListIds().data.contains(1));
            inventory.createMissingReport("MissingReport", "Michel");
            inventory.removeReport(1);
            inventory.createMissingReport("MissingReport", "Michel");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }

    @Test
    void createExpiredReport() {
        try {
            Integer id = inventory.createExpiredReport("ExpiredReport", "Michel").data.getId();
            Assertions.assertTrue(inventory.getReportListIds().data.contains(id));
            inventory.removeReport(id);
            inventory.createExpiredReport("ExpiredReport", "Michel");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }

    @Test
    void createBySupplierReport() {
        try {
            inventory.createBySupplierReport("BySupplierReport", "Michel", 0);
            
            Assertions.assertTrue(inventory.getReportListIds().data.contains(1));
            inventory.createBySupplierReport("MissingReport", "Michel", 0);
            inventory.removeReport(1);
            inventory.createBySupplierReport("ExpiredReport", "Michel", 0);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }
}