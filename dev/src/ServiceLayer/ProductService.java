package ServiceLayer;

import BusinessLayer.ProductController;

import java.time.LocalDateTime;

public class ProductService {
    private final ProductController product_controller;

    public ProductService() {
        product_controller = new ProductController();
    }

    //methods
    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        try {
            product_controller.updateItemManDiscount(product_id, item_id, discount, start_date, end_date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        try {
            product_controller.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductManPrice(int product_id, double price) {
        try {
            product_controller.updateProductManPrice(product_id, price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductCusPrice(int product_id, double price) {
        try {
            product_controller.updateProductCusPrice(product_id, price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        try {
            product_controller.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) {
        try {
            product_controller.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProduct(int id) {
        try {
            product_controller.removeProduct(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date) {
        try {
            product_controller.addItem(product_id, store, location, supplier, expiration_date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeItem(int product_id, int item_id) {
        try {
            product_controller.removeItem(product_id, item_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getItemLocation(int product_id, int item_id) {
        try {
            return product_controller.getItemLocation(product_id, item_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
