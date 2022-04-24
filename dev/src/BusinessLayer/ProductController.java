package BusinessLayer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ProductController {
    private int product_ids;
    private Map<String, Product> products;

    //constructors
    public ProductController() {
        product_ids = 0;
        products = new HashMap<>();
    }

    //methods
    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        products.get(Integer.toString(product_id)).updateItemManDiscount(item_id, discount, start_date, end_date);
    }

    public void updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        products.get(Integer.toString(product_id)).updateItemCusDiscount(item_id, discount, start_date, end_date);
    }

    public void updateProductManPrice(int product_id, double price) {
        products.get(Integer.toString(product_id)).setMan_price(price);
    }

    public void updateProductCusPrice(int product_id, double price) {
        products.get(Integer.toString(product_id)).setCus_price(price);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        products.get(Integer.toString(product_id)).updateItemDefect(item_id, is_defect, defect_reporter);
    }

    public String getItemLocation(int product_id, int item_id) {
        return products.get(Integer.toString(product_id)).getItemLocation(item_id);
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) {
        products.put(Integer.toString(product_ids), new Product(product_ids, name, manufacturer, man_price, cus_price, min_qty, supply_time));
        product_ids++;
    }

    public void removeProduct(int product_id) {
        products.remove(Integer.toString(product_id));
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date) {
        products.get(product_id).addItem(store, location, supplier, expiration_date);
    }

    public void removeItem(int product_id, int item_id) {
        products.get(product_id).removeItem(item_id);
    }


    //getters and setters
    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }
}
