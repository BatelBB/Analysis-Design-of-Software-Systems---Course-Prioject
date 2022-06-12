package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.serviceLayer.ServiceBase;

import java.time.LocalDate;
import java.util.List;

public class Service extends ServiceBase {
    private final ProductService product_service;
    private final CategoryService category_service;
    private final ReportService report_service;
    private final ISupplierService supplierService;

    public Service(ISupplierService supplierService,
                   ProductService productService,
                   ReportService reportService,
                   CategoryService categoryService) {
        this.product_service = productService;
        this.report_service = reportService;
        this.category_service = categoryService;
        this.supplierService = supplierService;
    }

    //Category methods
    public Response addCategory(String name) {
        return category_service.addCategory(name);
    }

    public Response addSubCategory(String categoryName, String SubCategoryName) {
        return category_service.addSubCategory(categoryName, SubCategoryName);
    }

    public Response addSubSubCategory(String category, String sub_category, String name) {
        return category_service.addSubSubCategory(category, sub_category, name);
    }

    public Response removeCategory(String name) {
        return category_service.removeCategory(name, product_service.productsInCategory(name));
    }

    public Response removeSubCategory(String category, String name) {
        return category_service.removeSubCategory(category, name, product_service.productsInSubCategory(category, name));
    }

    public Response removeSubSubCategory(String category, String sub_category, String name) {
        return category_service.removeSubSubCategory(category, sub_category, name, product_service.productsInSubSubCategory(category, sub_category, name));
    }

    public ResponseT<adss_group_k.BusinessLayer.Inventory.Categories.Category> getCategory(String name) {
        return category_service.getCategory(name);
    }

    public ResponseT<SubCategory> getSubCategory(String category, String name) {
        return category_service.getSubCategory(category, name);
    }

    public Response updateCategoryCusDiscount(float discount, LocalDate start_date, LocalDate end_date, String category, String sub_category, String subsub_category) {
        return product_service.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
    }

    public ResponseT<List<String>> getCategoriesNames() {
        return category_service.getCategoriesNames();
    }

    //Product methods
    public ResponseT<Product> addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) {
        return product_service.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time, category, sub_category, subsub_category);
    }

    public Response removeProduct(int id) {
        return product_service.removeProduct(id);
    }

    public Response updateProductCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id) {
        return product_service.updateProductCusDiscount(discount, start_date, end_date, product_id);
    }

    public Response updateProductCusPrice(int product_id, float price) {
        return product_service.updateProductCusPrice(product_id, price);
    }

    public ResponseT<List<String>> getProductNames() {
        return product_service.getProductNames();
    }

    public ResponseT<List<adss_group_k.BusinessLayer.Inventory.Product>> getProducts(){
        return product_service.getProducts();
    }

    //Item methods
    public ResponseT<adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem> addItem(int product_id, String store, String location, int supplier, LocalDate expiration_date, boolean on_shelf) {
        return product_service.addItem(product_id, store, location, supplier, expiration_date, on_shelf);
    }

    public Response removeItem(int product_id, int item_id) {
        return product_service.removeItem(product_id, item_id);
    }

    public Response updateItemCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id, int item_id) {
        return product_service.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public Response updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        return product_service.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
    }

    public ResponseT<String> getItemLocation(int product_id, int item_id) {
        return product_service.getItemLocation(product_id, item_id);
    }

    public Response setItemLocation(int product_id, int item_id, String location) {
        return product_service.changeItemLocation(product_id, item_id, location);
    }

    public Response setItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        return product_service.changeItemOnShelf(product_id, item_id, on_shelf);
    }

    //Report methods
    public ResponseT<List<Integer>> getReportListIds() {
        return report_service.getReportListNames();
    }

    public ResponseT<Report> createMissingReport(String name, String report_producer) {
        return report_service.createMissingReport(name, report_producer);
    }

    public ResponseT<Report> createExpiredReport(String name, String report_producer) {
        return report_service.createExpiredReport(name, report_producer);
    }

    public ResponseT<Report> createSurplusesReport(String name, String report_producer) {
        return report_service.createSurplusesReport(name, report_producer);
    }

    public ResponseT<Report> createDefectiveReport(String name, String report_producer) {
        return report_service.createDefectiveReport(name, report_producer);
    }

    public ResponseT<Report> createBySupplierReport(String name, String report_producer, int suppName) {
        return report_service.createBySupplierReport(name, report_producer, suppName);
    }

    public ResponseT<Report> createByProductReport(String name, String report_producer, String proName) {
        return report_service.createByProductReport(name, report_producer, proName);
    }

    public ResponseT<Report> createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) {
        return report_service.createByCategoryReport(name, report_producer, CatName, subCatName, subSubCatName);
    }

    public Response removeReport(int id) {
        return report_service.removeReport(id);
    }

    public ResponseT<Report> getReport(int id) {
        return report_service.getReport(id);
    }

    //Order methods
//    public Response createOrder() {
//        return supplierService.createOrder();
//    }
//
//    public Response createDeficienciesOrder() {
//        Map<String, Integer> proAmount = product_service.getDeficiency().data;
//        Order order = supplierService.createOrder();
//        for (Map.Entry<String, Integer> entry : proAmount.entrySet()) {
//            supplierService.orderItem(order.id, entry.getKey(), entry.getValue());
//        }
//        return null;
//    }
//
//    public Response updateOrder(String op, int orderId, String proName, int proAmount) {
//        //need to check that the order contain the min_qnt
//        int minAmount = product_service.getMinAmount(proName).data;
//        switch (op) {
//            case "Remove":
//                return supplierService.updateOrderAmount(orderId, proName); // just enter 0 in the amount, and it will be deleted
//            case "Add":
//                return supplierService.orderItem(orderId, proName, proAmount, minAmount);
//            case "UpdateAmount":
//                return supplierService.updateOrderAmount(orderId, proName, proAmount, minAmount);
//        }
//        return null;
//    }
}
