package adss_group_k.inventory.ServiceLayer.Objects;

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

    public Product(adss_group_k.inventory.BusinessLayer.Product p) {
        item_ids = p.getItem_ids();
        product_id = p.getProduct_id();
        name = p.getName();
        shelf_qty = p.getShelf_qty();
        storage_qty = p.getStorage_qty();
        manufacturer = p.getManufacturer();
        man_price = p.getMan_price();
        cus_price = p.getCus_price();
        min_qty = p.getMin_qty();
        supply_time = p.getSupply_time();
        cat = p.getCat();
        sub_cat = p.getCat();
        sub_sub_cat = p.getSub_sub_cat();
        Map<String, adss_group_k.inventory.BusinessLayer.ProductItem> BusinessItemsMap = p.getItems();
        items = new HashMap<>();
        for (Map.Entry<String, adss_group_k.inventory.BusinessLayer.ProductItem> entry : BusinessItemsMap.entrySet()) {
            items.put(entry.getKey(), new ProductItem(entry.getValue()));
        }
    }

    public String toString() {
        String s = "product_id: " + product_id + "\n" + "product name: " + name + "\n"
                + "category: " + cat + "\n" + "sub category: " + sub_cat + "\n" +
                "sub sub category: " + sub_sub_cat + "\n" + "manufacturer: " + manufacturer + "\n" +
                "manufacturer price: " + man_price + "\n" + "customer price: " + cus_price + "\n" +
                "supply time: " + supply_time + "\n" + "minimum quantity: " + min_qty + "\n" +
                "shelf quantity: " + shelf_qty + "\n" + "storage quantity: " + storage_qty + "\n" +
                "The items id in this product are:\n";
        for (Map.Entry<String, ProductItem> entry : items.entrySet()) {
            s = s + entry.getValue().getId() + "\n";
        }
        return s;
    }

    public String getName() {
        return name;
    }

    public int getMin_qty() {
        return min_qty;
    }
}
