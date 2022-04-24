package ServiceLayer;

import java.time.LocalDateTime;

public class Service {
    private final ProductService product_service;

    public Service() {
        product_service = new ProductService();
    }

    public void addCategory(String name) {
    }

    public void removeCategory(String name) {
    }

    public void addSubCategory(String category, String name) {
    }

    public void removeSubCategory(String category, String name) {
    }

    public void addSubSubCategory(String category, String sub_category, String name) {
    }

    public void removeSubSubCategory(String category, String sub_category, String name) {
    }

    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        product_service.updateItemManDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public void updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        product_service.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
    }

    public void updateCategoryManDiscount(String name, double discount, LocalDateTime start_date, LocalDateTime end_date) {
    }

    public void updateCategoryCusDiscount(String name, double discount, LocalDateTime start_date, LocalDateTime end_date) {
    }

    public void updateProductManPrice(int product_id, double price) {
        product_service.updateProductManPrice(product_id, price);
    }

    public void updateProductCusPrice(int product_id, double price) {
        product_service.updateProductCusPrice(product_id, price);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        product_service.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) {
        product_service.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time);
    }

    public void removeProduct(int id) {
        product_service.removeProduct(id);
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date) {
        product_service.addItem(product_id, store, location, supplier, expiration_date);
    }

    public void removeItem(int product_id, int item_id) {
        product_service.removeItem(product_id, item_id);
    }

    public String locateItem(int product_id, int item_id) {
        return null;
    }

    public String getItemLocation(int product_id, int item_id) {
        return product_service.getItemLocation(product_id, item_id);
    }
}
