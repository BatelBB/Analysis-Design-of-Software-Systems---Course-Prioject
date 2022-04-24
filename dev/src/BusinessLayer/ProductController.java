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
    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        discountLegal(discount);
        checkDates(start_date, end_date);
        productExists(product_id);
        products.get(Integer.toString(product_id)).updateItemManDiscount(item_id, discount, start_date, end_date);
    }

    public void updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        discountLegal(discount);
        checkDates(start_date, end_date);
        productExists(product_id);
        products.get(Integer.toString(product_id)).updateItemCusDiscount(item_id, discount, start_date, end_date);
    }

    public void updateProductManPrice(int product_id, double price) throws Exception {
        priceLegal(price);
        productExists(product_id);
        products.get(Integer.toString(product_id)).setMan_price(price);
    }

    public void updateProductCusPrice(int product_id, double price) throws Exception {
        priceLegal(price);
        productExists(product_id);
        products.get(Integer.toString(product_id)).setCus_price(price);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) throws Exception {
        if (defect_reporter == null)
            throw new Exception("defect reporter is null");
        if (defect_reporter.equals(""))
            throw new Exception("defect reporter is empty");
        productExists(product_id);
        products.get(Integer.toString(product_id)).updateItemDefect(item_id, is_defect, defect_reporter);
    }

    public String getItemLocation(int product_id, int item_id) throws Exception {
        productExists(product_id);
        return products.get(Integer.toString(product_id)).getItemLocation(item_id);
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) throws Exception {
        if (name == null || name.equals(""))
            throw new Exception("product name empty");
        if (manufacturer == null || manufacturer.equals(""))
            throw new Exception("product name empty");
        priceLegal(man_price);
        priceLegal(cus_price);
        if (min_qty < 0)
            throw new Exception("min quantity smaller than 0");
        if (supply_time < 0)
            throw new Exception("supply time smaller than 0");
        products.put(Integer.toString(product_ids), new Product(product_ids, name, manufacturer, man_price, cus_price, min_qty, supply_time));
        product_ids++;
    }

    public void removeProduct(int product_id) throws Exception {
        productExists(product_id);
        products.remove(Integer.toString(product_id));
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).addItem(store, location, supplier, expiration_date);
    }

    public void removeItem(int product_id, int item_id) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).removeItem(item_id);
    }

    public void changeItemLocation(int product_id, int item_id, String location) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).changeItemLocation(item_id, location);
    }


    //getters and setters
    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    //private methods
    private void productExists(int product_id) throws Exception {
        if (!products.containsKey(Integer.toString(product_id)))
            throw new Exception("product id does not exist");
    }

    private void checkDates(LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        if (start_date == null)
            throw new Exception("start date is null");
        if (end_date == null)
            throw new Exception("end date is null");
        if (end_date.isBefore(start_date))
            throw new Exception("end date earlier than start date");
        if (end_date.isBefore(LocalDateTime.now()))
            throw new Exception("end date has passed");
    }

    private void discountLegal(double discount) throws Exception {
        if (discount > 1 || discount <= 0)
            throw new Exception("discount percentage illegal");
    }

    private void priceLegal(double price) throws Exception {
        if (price < 0)
            throw new Exception("price illegal");
    }
}
