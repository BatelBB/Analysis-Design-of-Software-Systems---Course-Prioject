package BusinessLayer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private int item_ids;
    private final int product_id;
    private String name;
    private int shelf_qty;
    private int storage_qty;
    private String manufacturer;
    private double man_price;
    private double cus_price;
    private int min_qty;
    private int supply_time;
    private Map<String, ProductItem> items;

    private String cat;
    private String sub_cat;
    private String sub_sub_cat;

    //constructors
    public Product(int product_id, String name, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time) {
        this.product_id = product_id;
        this.name = name;
        shelf_qty = 0;
        storage_qty = 0;
        this.manufacturer = manufacturer;
        this.man_price = man_price;
        this.cus_price = cus_price;
        this.min_qty = min_qty;
        this.supply_time = supply_time;
        items = new HashMap<>();
    }

    public Product(int product_id, String name, int shelf_qty, int storage_qty, String manufacturer, double man_price, double cus_price, int min_qty, int supply_time, Map<String, ProductItem> items) {
        this.product_id = product_id;
        this.name = name;
        this.shelf_qty = shelf_qty;
        this.storage_qty = storage_qty;
        this.manufacturer = manufacturer;
        this.man_price = man_price;
        this.cus_price = cus_price;
        this.min_qty = min_qty;
        this.supply_time = supply_time;
        this.items = items;
    }

    //methods
    public void updateItemManDiscount(int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        itemExists(item_id);
        items.get(Integer.toString(item_id)).addManDiscount(new DiscountPair(start_date, end_date, discount));
    }

    public void updateItemCusDiscount(int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        itemExists(item_id);
        items.get(Integer.toString(item_id)).addCusDiscount(new DiscountPair(start_date, end_date, discount));
    }

    public void updateItemDefect(int id, boolean is_defect, String defect_reporter) throws Exception {
        itemExists(id);
        items.get(Integer.toString(id)).setIs_defect(is_defect);
        items.get(Integer.toString(id)).setDefect_reporter(defect_reporter);
    }

    public String getItemLocation(int item_id) throws Exception {
        itemExists(item_id);
        return items.get(Integer.toString(item_id)).getLocation();
    }

    public void addItem(String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) throws Exception {
        if (store == null || store.equals(""))
            throw new Exception("store name empty");
        if (location == null || location.equals(""))
            throw new Exception("location name empty");
        if (supplier == null || supplier.equals(""))
            throw new Exception("supplier name empty");
        if (expiration_date == null)
            throw new Exception("expiration date is null");
        items.put(Integer.toString(item_ids), new ProductItem(item_ids, store, location, supplier, expiration_date, on_shelf));
        if (on_shelf)
            shelf_qty++;
        else
            storage_qty++;
        item_ids++;
    }

    public void removeItem(int item_id) throws Exception {
        itemExists(item_id);
        if (items.get(Integer.toString(item_id)).isOn_shelf())
            shelf_qty--;
        else
            storage_qty--;
        items.remove(Integer.toString(item_id));
    }

    public void changeItemLocation(int item_id, String location) throws Exception {
        itemExists(item_id);
        if (location == null || location.equals(""))
            throw new Exception("location name empty");
        items.get(Integer.toString(item_id)).setLocation(location);
    }

    public void changeItemOnShelf(int item_id, boolean on_shelf) throws Exception {
        itemExists(item_id);
        if (items.get(Integer.toString(item_id)).isOn_shelf() && !on_shelf) {
            shelf_qty--;
            storage_qty++;
            items.get(Integer.toString(item_id)).setOn_shelf(false);
        } else if (!items.get(Integer.toString(item_id)).isOn_shelf() && on_shelf) {
            shelf_qty++;
            storage_qty--;
            items.get(Integer.toString(item_id)).setOn_shelf(false);
        }
    }

    //getters and setters

    public int getItem_ids() { return item_ids; }

    public int getProduct_id() { return product_id; }

    public String getCat() { return cat; }

    public String getSub_cat() { return sub_cat; }

    public String getSub_sub_cat() { return sub_sub_cat; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShelf_qty() {
        return shelf_qty;
    }

    public void setShelf_qty(int shelf_qty) {
        this.shelf_qty = shelf_qty;
    }

    public int getStorage_qty() {
        return storage_qty;
    }

    public void setStorage_qty(int storage_qty) {
        this.storage_qty = storage_qty;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getMan_price() {
        return man_price;
    }

    public void setMan_price(double man_price) {
        this.man_price = man_price;
    }

    public double getCus_price() {
        return cus_price;
    }

    public void setCus_price(double cus_price) {
        this.cus_price = cus_price;
    }

    public int getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(int min_qty) {
        this.min_qty = min_qty;
    }

    public int getSupply_time() {
        return supply_time;
    }

    public void setSupply_time(int supply_time) {
        this.supply_time = supply_time;
    }

    public Map<String, ProductItem> getItems() {
        return items;
    }

    public void setItems(Map<String, ProductItem> items) {
        this.items = items;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getSub_sub_cat() {
        return sub_sub_cat;
    }

    public void setSub_sub_cat(String sub_sub_cat) {
        this.sub_sub_cat = sub_sub_cat;
    }

    //private methods
    private void itemExists(int item_id) throws Exception {
        if (!items.containsKey(Integer.toString(item_id)))
            throw new Exception("item id doesn't exist");
    }
}
