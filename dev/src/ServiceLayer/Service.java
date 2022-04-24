package ServiceLayer;

import ServiceLayer.Objects.Category;
import ServiceLayer.Objects.MissingReport;
import ServiceLayer.Objects.SubCategory;

import java.time.LocalDateTime;

public class Service {
    private final ProductService product_service;
    private final CategoryService category_service;
    private final ReportService report_service;

    public Service() {

        product_service = new ProductService();
        report_service = new ReportService();
        category_service = new CategoryService();
    }

    public void addCategory(String name) { category_service.addCategory(name); }

    public void removeCategory(String name) { category_service.removeCategory(name); }
    public Category getCategory(String name){ return category_service.getCategory(name); }

    public void addSubCategory(String categoryName, String SubCategoryName) {category_service.addSubCategory(categoryName, SubCategoryName);
    }

    public void removeSubCategory(String category, String name) {category_service.removeSubCategory(category, name);
    }
    public SubCategory getSubCategory(String category, String name) {return category_service.getSubCategory(category,name);}

    public void addSubSubCategory(String category, String sub_category, String name) { category_service.addSubSubCategory(category,sub_category, name); }

    public void removeSubSubCategory(String category, String sub_category, String name) { category_service.removeSubSubCategory(category,sub_category, name); }


    public void updateCategoryManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        product_service.updateCategoryManDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
    }

    public void updateCategoryCusDiscount(String name, double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        product_service.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
    }

    public void updateProductManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        product_service.updateProductManDiscount(discount, start_date, end_date, product_id);
    }

    public void updateProductCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        product_service.updateProductManDiscount(discount, start_date, end_date, product_id);
    }

    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        product_service.updateItemManDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public void updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        product_service.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public void updateProductManPrice(int product_id, double price) {
        product_service.updateProductManPrice(product_id, price);
    }

    public void updateProductCusPrice(int product_id, double price) {
        product_service.updateProductCusPrice(product_id, price);
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) {
        product_service.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time);
    }

    public void removeProduct(int id) {
        product_service.removeProduct(id);
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) {
        product_service.addItem(product_id, store, location, supplier, expiration_date, on_shelf);
    }

    public void removeItem(int product_id, int item_id) {
        product_service.removeItem(product_id, item_id);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        product_service.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
    }

    public String getItemLocation(int product_id, int item_id) {
        return product_service.getItemLocation(product_id, item_id);
    }

    public void changeItemLocation(int product_id, int item_id, String location) {
        product_service.changeItemLocation(product_id, item_id, location);
    }

    public void changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        product_service.changeItemOnShelf(product_id, item_id, on_shelf);
    }
    public void removeReport(int id) {report_service.removeReport(id);}
    public void getReport(int id) {report_service.getReport(id);}
    public MissingReport createMissingReport(String name, int id, String report_producer) {return report_service.createMissingReport(name, id, report_producer);}
    public ServiceLayer.Objects.ExpiredReport createExpiredReport(String name, int id, String report_producer){return report_service.createExpiredReport(name, id, report_producer);}
    public ServiceLayer.Objects.SurplusesReport createSurplusesReport(String name, int id, String report_producer){return report_service.createSurplusesReport(name, id, report_producer);}
    public ServiceLayer.Objects.DefectiveReport createDefectiveReport(String name, int id, String report_producer){return report_service.createDefectiveReport(name, id, report_producer);}
    public ServiceLayer.Objects.bySupplierReport createBySupplierReport(String name, int id, String report_producer, String suppName){return report_service.createBySupplierReport(name, id, report_producer, suppName);}
    public ServiceLayer.Objects.byProductReport createByProductReport(String name, int id, String report_producer, String proName){return report_service.createByProductReport(name, id, report_producer, proName);}
    public ServiceLayer.Objects.byCategoryReport createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName)
    {return report_service.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);}
}
