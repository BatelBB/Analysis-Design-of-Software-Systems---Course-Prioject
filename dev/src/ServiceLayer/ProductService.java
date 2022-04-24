package ServiceLayer;

import BusinessLayer.ProductController;

import java.time.LocalDateTime;

public class ProductService {
    private final ProductController product_controller;

    public ProductService() {
        product_controller = ProductController.getInstance();
    }

    //methods
    public void updateCategoryManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        try {
            product_controller.updateCategoryManDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCategoryCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        try {
            product_controller.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        try {
            product_controller.updateProductManDiscount(discount, start_date, end_date, product_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        try {
            product_controller.updateProductCusDiscount(discount, start_date, end_date, product_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) {
        try {
            product_controller.addItem(product_id, store, location, supplier, expiration_date, on_shelf);
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

    public void changeItemLocation(int product_id, int item_id, String location) {
        try {
            product_controller.changeItemLocation(product_id, item_id, location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        try {
            product_controller.changeItemOnShelf(product_id, item_id, on_shelf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
