package groupk.inventory_and_suppliers.Inventory;

import groupk.inventory_and_suppliers.InventorySuppliersTestsBase;

import groupk.shared.business.Facade;
import groupk.shared.business.Inventory.Categories.Category;
import groupk.shared.service.Inventory.Objects.Report;
import groupk.shared.service.ServiceBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static groupk.CustomAssertions.*;

class InventoryServiceTest extends InventorySuppliersTestsBase {

    @Test
    void addCategory() {
        try {
            Assertions.assertTrue(facade.getCategoriesNames().data.isEmpty());
            facade.addCategory("Dairy Products");
            Assertions.assertTrue(facade.getCategoriesNames().data.contains("Dairy Products"));
            facade.addCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The categories already exists in the system");
        }
    }

    @Test
    void removeCategory() {
        try {
            facade.addCategory("Dairy Products");
            facade.removeCategory("Dairy Products", true);
            Assertions.assertFalse(facade.getCategoriesNames().data.contains("Dairy Products"));
            facade.removeCategory("Dairy Products", true);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Category doesn't exists");
        }
    }

    @Test
    void getCategory() {
        try {
            Facade.SI_Response dairy_products = facade.addCategory("Dairy Products");
            Category category = assertSuccess(facade.getCategory("Dairy Products"));
            Assertions.assertEquals(category.getName(), "Dairy Products");
            facade.removeCategory("Dairy Products", true);
            facade.getCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Category doesn't exists");
        }
    }

    @Test
    void addProduct() {
        try {
            Assertions.assertTrue(facade.getProductNames().data.isEmpty());
            initCategories();
            facade.addProduct("Milk", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products","Milks","Cow Milk");
            Assertions.assertTrue(facade.getProductNames().data.contains("Milk"));
            facade.addProduct("", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products","Milks","Cow Milk");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "product name empty");
        }
    }

    private void initCategories() {
        facade.addCategory("Dairy Products");
        facade.addSubCategory("Dairy Products", "Milks");
        facade.addSubSubCategory("Dairy Products", "Milks", "Cow Milk");
    }

    @Test
    void removeProduct() {
        try {
            initCategories();
            String productName = facade.addProduct("Milk", "Tnova", 4, 5.9f,
                    350, 6, "Dairy Products","Milks","Cow Milk")
                    .data.getName();
            Assertions.assertTrue(facade.getProductNames().data.contains("Milk"));
            facade.removeProduct(1);
            Assertions.assertFalse(facade.getProductNames().data.contains("Milk"));
            facade.removeProduct(0);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "product id does not exist");
        }
    }

    @Test
    void removeReport() {
        Facade.ResponseT<Report> missingReport = facade.createMissingReport("MissingReport", "Michel");
            int reportId = missingReport.data.getId();
            //Assertions.assertTrue(facade.getReportListIds().data.contains(reportId));
            Assertions.assertTrue(facade.removeReport(reportId).success);
            Assertions.assertFalse(facade.getProductNames().data.contains(reportId));
            Assertions.assertFalse(facade.removeProduct(reportId).success);
    }

    @Test
    void getReport() {
        try {
            Integer id = facade.createMissingReport("MissingReport", "Michel").data.getId();
            Report report = assertSuccess(facade.getReport(id));
            Assertions.assertEquals(report.getName(), "MissingReport");
            facade.removeReport(id);
            facade.getReport(id);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Report id doesn't exists");
        }
    }

    @Test
    void createMissingReport() {
        try {
            facade.createMissingReport("MissingReport", "Michel");
            
            //Assertions.assertTrue(facade.getReportListIds().data.contains(1));
            facade.createMissingReport("MissingReport", "Michel");
            facade.removeReport(1);
            facade.createMissingReport("MissingReport", "Michel");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }

    @Test
    void createExpiredReport() {
        try {
            Integer id = facade.createExpiredReport("ExpiredReport", "Michel").data.getId();
            //Assertions.assertTrue(facade.getReportListIds().data.contains(id));
            facade.removeReport(id);
            facade.createExpiredReport("ExpiredReport", "Michel");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }

    @Test
    void createBySupplierReport() {
        try {
            facade.createBySupplierReport("BySupplierReport", "Michel", 0);
            
            //Assertions.assertTrue(facade.getReportListIds().data.contains(1));
            facade.createBySupplierReport("MissingReport", "Michel", 0);
            facade.removeReport(1);
            facade.createBySupplierReport("ExpiredReport", "Michel", 0);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "The ReportId already exists in the system");
            
        }
    }
}