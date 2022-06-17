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
       Assertions.assertTrue(facade.getCategoriesNames().data.isEmpty());
        assertSuccess(facade.addCategory("Dairy Products"));
        Assertions.assertTrue(facade.getCategoriesNames().data.contains("Dairy Products"));
        assertFailure(facade.addCategory("Dairy Products"));
    }

    @Test
    void removeCategory() {
        facade.addCategory("Dairy Products");
        facade.removeCategory("Dairy Products");
        Assertions.assertFalse(facade.getCategoriesNames().data.contains("Dairy Products"));
        assertFailure(facade.removeCategory("Dairy Products"));
    }

    @Test
    void getCategory() {
        try {
            Facade.SI_Response dairy_products = facade.addCategory("Dairy Products");
            Category category = assertSuccess(facade.getCategory("Dairy Products"));
            Assertions.assertEquals(category.getName(), "Dairy Products");
            facade.removeCategory("Dairy Products");
            facade.getCategory("Dairy Products");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Category doesn't exists");
        }
    }

    @Test
    void addProduct() {
            Assertions.assertTrue(facade.getProductNames().data.isEmpty());
            initCategories();
            facade.addProduct("Milk", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products","Milks","Cow Milk");
            Assertions.assertTrue(facade.getProductNames().data.contains("Milk"));
            assertFailure(facade.addProduct("", "Tnova", 4, 5.9f, 350, 6,
                    "Dairy Products", "Milks", "Cow Milk"));

    }

    private void initCategories() {
        facade.addCategory("Dairy Products");
        facade.addSubCategory("Dairy Products", "Milks");
        facade.addSubSubCategory("Dairy Products", "Milks", "Cow Milk");
    }

    @Test
    void removeProduct() {
            initCategories();
            String productName = facade.addProduct("Milk", "Tnova", 4, 5.9f,
                    350, 6, "Dairy Products","Milks","Cow Milk")
                    .data.getName();
            Assertions.assertTrue(facade.getProductNames().data.contains("Milk"));
            facade.removeProduct(1);
            Assertions.assertFalse(facade.getProductNames().data.contains("Milk"));
            assertFailure(facade.removeProduct(0));

    }

    @Test
    void removeReport() {
        Facade.ResponseT<Report> missingReport = facade.createMissingReport("MissingReport", "Michel");
            int reportId = assertSuccess(missingReport).getId();
            Assertions.assertTrue(facade.getReportListIds().data.contains(reportId));
            Assertions.assertTrue(facade.removeReport(reportId).success);
            Assertions.assertFalse(facade.getProductNames().data.contains(reportId));
            Assertions.assertFalse(facade.removeProduct(reportId).success);
    }

    @Test
    void getReport() {
            Integer id = assertSuccess(
                    facade.createMissingReport("MissingReport", "Michel")
            ).getId();
            Report report = assertSuccess(facade.getReport(id));
            Assertions.assertEquals(report.getName(), "MissingReport");
            facade.removeReport(id);
            assertFailure(facade.getReport(id));

    }

    @Test
    void createMissingReport() {
           facade.createMissingReport("MissingReport", "Michel");
            
            Assertions.assertTrue(facade.getReportListIds().data.contains(1));
            facade.createMissingReport("MissingReport", "Michel");
            facade.removeReport(1);
            assertSuccess(facade.createMissingReport("MissingReport", "Michel"));

    }

    @Test
    void createExpiredReport() {
            Integer id = facade.createExpiredReport("ExpiredReport", "Michel").data.getId();
            Assertions.assertTrue(facade.getReportListIds().data.contains(id));
            facade.removeReport(id);
            assertSuccess(facade.createExpiredReport("ExpiredReport", "Michel"));

    }

    @Test
    void createBySupplierReport() {
           assertSuccess(facade.createBySupplierReport("BySupplierReport", "Michel", 0));
            Assertions.assertTrue(facade.getReportListIds().data.contains(1));
            assertSuccess(facade.createBySupplierReport("MissingReport", "Michel", 0));
            assertSuccess(facade.removeReport(1));
            assertSuccess(facade.createBySupplierReport("ExpiredReport", "Michel", 0));
    }
}