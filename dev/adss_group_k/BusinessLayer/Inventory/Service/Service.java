package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import adss_group_k.BusinessLayer.Suppliers.Entity.MutableOrder;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService_V2;
import adss_group_k.shared.response.*;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Service {
    private final ProductService product_service;
    private final CategoryService category_service;
    private final ReportService report_service;
    private final ISupplierService_V2 supplierService;

    public Service(ISupplierService_V2 supplierService) {
        product_service = new ProductService();
        report_service = new ReportService();
        category_service = new CategoryService();
        this.supplierService = supplierService;
    }

    public ResponseT<List<String>> getCategoriesNames() {
        return category_service.getCategoriesNames();
    }

    public ResponseT<List<String>> getProductIdes() {
        return product_service.getProductIdes();
    }

    public ResponseT<List<Integer>> getReportListNames() {
        return report_service.getReportListNames();
    }

    public Response addCategory(String name) {
        return category_service.addCategory(name);
    }

    public Response removeCategory(String name) {
        return category_service.removeCategory(name, product_service.productsInCategory(name));
    }

    public ResponseT<Category> getCategory(String name) {
        return category_service.getCategory(name);
    }

    public Response addSubCategory(String categoryName, String SubCategoryName) {
        return category_service.addSubCategory(categoryName, SubCategoryName);
    }

    public Response removeSubCategory(String category, String name) {
        return category_service.removeSubCategory(category, name, product_service.productsInSubCategory(category, name));
    }

    public ResponseT<SubCategory> getSubCategory(String category, String name) {
       return category_service.getSubCategory(category, name);
    }

    public Response addSubSubCategory(String category, String sub_category, String name) {
        return category_service.addSubSubCategory(category, sub_category, name);
    }

    public Response removeSubSubCategory(String category, String sub_category, String name) {
        return category_service.removeSubSubCategory(category, sub_category, name, product_service.productsInSubSubCategory(category, sub_category, name));
    }

//    public void updateCategoryManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
//        product_service.updateCategoryManDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
//    }

    public Response updateCategoryCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        return product_service.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
    }

//    public void updateProductManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
//        product_service.updateProductManDiscount(discount, start_date, end_date, product_id);
//    }

    public Response updateProductSupplierManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String supplier) {
        return product_service.updateProductSupplierManDiscount(discount, start_date, end_date, supplier);
    }

    public Response updateProductCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        return product_service.updateProductCusDiscount(discount, start_date, end_date, product_id);
    }

//    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
//        product_service.updateItemManDiscount(product_id, item_id, discount, start_date, end_date);
//    }

    public Response updateItemCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id, int item_id) {
        return product_service.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public Response updateProductManPrice(int product_id, double price) {
        return product_service.updateProductManPrice(product_id, price);
    }

    public Response updateProductCusPrice(int product_id, double price) {
        return product_service.updateProductCusPrice(product_id, price);
    }

    public ResponseT<Product> addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) {
        return product_service.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time, category, sub_category, subsub_category);
    }

    public Response removeProduct(int id) {
        return product_service.removeProduct(id);
    }

    public Response addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) {
        return product_service.addItem(product_id, store, location, supplier, expiration_date, on_shelf);
    }

    public Response removeItem(int product_id, int item_id) {
        return product_service.removeItem(product_id, item_id);
    }

    public Response updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        return product_service.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
    }

    public ResponseT<String> getItemLocation(int product_id, int item_id) {
        return product_service.getItemLocation(product_id, item_id);
    }

    public Response changeItemLocation(int product_id, int item_id, String location) {
        return product_service.changeItemLocation(product_id, item_id, location);
    }

    public Response changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        return product_service.changeItemOnShelf(product_id, item_id, on_shelf);
    }

    public ResponseT<Report> createMissingReport(String name, int id, String report_producer) {
        return report_service.createMissingReport(name, id, report_producer);
    }

    public ResponseT<Report> createExpiredReport(String name, int id, String report_producer) {
        return report_service.createExpiredReport(name, id, report_producer);
    }

    public ResponseT<Report> createSurplusesReport(String name, int id, String report_producer) {
        return report_service.createSurplusesReport(name, id, report_producer);
    }

    public ResponseT<Report> createDefectiveReport(String name, int id, String report_producer) {
        return report_service.createDefectiveReport(name, id, report_producer);
    }

    public ResponseT<Report> createBySupplierReport(String name, int id, String report_producer, String suppName) {
        return report_service.createBySupplierReport(name, id, report_producer, suppName);
    }

    public ResponseT<Report> createByProductReport(String name, int id, String report_producer, String proName) {
        return report_service.createByProductReport(name, id, report_producer, proName);
    }

    public ResponseT<Report> createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        return report_service.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);
    }

    public Response removeReport(int id) {
        return report_service.removeReport(id);
    }

    public ResponseT<Report> getReport(int id) {
        return report_service.getReport(id);
    }

    public Response createOrder() {
        return supplierService.createOrder();
    }

    public Response createDeficienciesOrder() {
        Map<String, Integer> proAmount = product_service.getDeficiency().data;
        MutableOrder order= supplierService.createOrder();
        for (Map.Entry<String, Integer> entry : proAmount.entrySet()) {
            supplierService.orderItem(order.id, entry.getKey(), entry.getValue());
        }
        return null;
    }

    public Response updateOrder(String op, int orderId, String proName, int proAmount) {//need to check that the order contain the min_qnt
        int minAmount = product_service.getMinAmount(proName).data;
        switch (op) {
            case "Remove":
                return supplierService.updateOrderAmount(orderId, proName); // just enter 0 in the amount, and it will be deleted
            case "Add":
                return supplierService.orderItem(orderId, proName, proAmount, minAmount);
            case "UpdateAmount":
                return supplierService.updateOrderAmount(orderId, proName, proAmount, minAmount);
        }
        return null;
    }

    public void restart() {
        product_service.restart();
        report_service.restart();
        category_service.restart();
    }
}
