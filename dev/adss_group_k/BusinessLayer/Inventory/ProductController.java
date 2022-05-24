package adss_group_k.BusinessLayer.Inventory;

import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.readonly.ProductData;
import adss_group_k.shared.response.ResponseT;

import java.time.LocalDateTime;
import java.util.*;

public class ProductController {
    private int product_ids;
    private Map<String, Product> products;
    private final CategoryController category_controller;
    private static ProductController product_controller;
    private PersistenceController dal;


    //singleton instance
    public static ProductController getInstance() {
        if (product_controller == null)
            product_controller = new ProductController();
        return product_controller;
    }

    //constructors
    private ProductController(PersistenceController dal) {
        product_ids = 0;
        products = new HashMap<>();
        category_controller = CategoryController.getInstance();
        this.dal = dal;
    }

    public void updateCategoryCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) throws Exception {
        if (category != null && !category.equals("")) {
            if (sub_category != null && !sub_category.equals("")) {
                if (subsub_category != null && !subsub_category.equals("")) {
                    for (Product p : products.values())
                        if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category) && p.getSub_sub_cat().equals(subsub_category))
                            for (ProductItem i : p.getItems().values())
                                i.addCusDiscount(new DiscountPair(start_date, end_date, discount));
                } else
                    for (Product p : products.values())
                        if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category))
                            for (ProductItem i : p.getItems().values())
                                i.addCusDiscount(new DiscountPair(start_date, end_date, discount));
            } else
                for (Product p : products.values())
                    if (p.getCat().equals(category))
                        for (ProductItem i : p.getItems().values())
                            i.addCusDiscount(new DiscountPair(start_date, end_date, discount));
        } else {
            if (sub_category == null || sub_category.equals("") || subsub_category == null || subsub_category.equals(""))
                for (Product p : products.values())
                    for (ProductItem i : p.getItems().values())
                        i.addManDiscount(new DiscountPair(start_date, end_date, discount));
            else
                throw new Exception("missing category input");
        }
    }

    public void updateProductSupplierManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String supplier) {
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.getSupplier().equals(supplier))
                    i.addManDiscount(new DiscountPair(start_date, end_date, discount));
    }

    public void updateProductCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) throws Exception {
        productExists(product_id);
        for (ProductItem i : products.get(Integer.toString(product_id)).getItems().values())
            i.addCusDiscount(new DiscountPair(start_date, end_date, discount));
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

    public void updateProductCusPrice(int product_id, float price) throws Exception {
        priceLegal(price);
        productExists(product_id);
        products.get(Integer.toString(product_id)).setCus_price(price);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) throws Exception {
        if (defect_reporter == null) throw new Exception("defect reporter is null");
        if (defect_reporter.equals("")) throw new Exception("defect reporter is empty");
        productExists(product_id);
        products.get(Integer.toString(product_id)).updateItemDefect(item_id, is_defect, defect_reporter);
    }

    public String getItemLocation(int product_id, int item_id) throws Exception {
        productExists(product_id);
        return products.get(Integer.toString(product_id)).getItemLocation(item_id);
    }

    public Product addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) throws Exception {
        if (category_controller.getCategories().containsKey(category) &&
                category_controller.getCategories().get(category).getSubC().containsKey(sub_category) &&
                category_controller.getCategories().get(category).getSubC().get(sub_category).getSubSubCategories().containsKey(subsub_category)
        ) {
            if (name == null || name.equals("")) throw new Exception("product name empty");
            if (manufacturer == null || manufacturer.equals("")) throw new Exception("product name empty");
            priceLegal(man_price);
            priceLegal(cus_price);
            if (min_qty < 0) throw new Exception("min quantity smaller than 0");
            if (supply_time < 0) throw new Exception("supply time smaller than 0");
            if (category == null || category.equals("")) throw new Exception("category name empty");
            if (sub_category == null || sub_category.equals("")) throw new Exception("sub_category name empty");
            if (subsub_category == null || subsub_category.equals("")) throw new Exception("subsub_category name empty");
            ProductData product = dal.products.create(product_ids,
                    name,
                    cus_price,
                    min_qty,
                    0,
                    0,
                    category_controller.getCategories().get(category).getName(),
                    category_controller.getCategories().get(category).getSubC().get(sub_category).getName(),
                    category_controller.getCategories().get(category).getSubC().get(sub_category).getSubSubCategories().get(subsub_category).name).data;
            Product p = new Product(
                    product_ids,
                    name,
                    manufacturer,
                    man_price,
                    cus_price,
                    min_qty,
                    supply_time,
                    category_controller.getCategories().get(category),
                    category_controller.getCategories().get(category).getSubC().get(sub_category),
                    category_controller.getCategories().get(category).getSubC().get(sub_category).getSubSubCategories().get(subsub_category)
            );
            products.put(Integer.toString(product_ids), p);
            product_ids++;
            return p;
        } else
            throw new Exception("category doesn't exist");
    }

    public void removeProduct(int product_id) throws Exception {
        productExists(product_id);
        products.remove(Integer.toString(product_id));
    }

    public void addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).addItem(store, location, supplier, expiration_date, on_shelf);
    }

    public void removeItem(int product_id, int item_id) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).removeItem(item_id);
    }

    public void changeItemLocation(int product_id, int item_id, String location) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).changeItemLocation(item_id, location);
    }

    public void changeItemOnShelf(int product_id, int item_id, boolean on_shelf) throws Exception {
        productExists(product_id);
        products.get(Integer.toString(product_id)).changeItemOnShelf(item_id, on_shelf);
    }

    public List<Product> getMissingProducts() {
        List<Product> missing_products = new ArrayList<>();
        for (Product p : products.values())
            if (p.getItems().size() < p.getMin_qty()) missing_products.add(p);
        return missing_products;
    }

    public List<Product> getSurplusProducts() {
        List<Product> missing_products = new ArrayList<>();
        for (Product p : products.values())
            if (p.getItems().size() >= p.getMin_qty()) missing_products.add(p);
        return missing_products;
    }

    public List<ProductItem> getExpiredItems() {
        List<ProductItem> expired_items = new ArrayList<>();
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.getExpirationDate().isBefore(LocalDateTime.now())) expired_items.add(i);
        return expired_items;
    }

    public List<ProductItem> getDefectiveItems() {
        List<ProductItem> defective_items = new ArrayList<>();
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.is_defect()) defective_items.add(i);
        return defective_items;
    }

    public List<ProductItem> getItemsBySupplier(String supplier) {
        List<ProductItem> items = new ArrayList<>();
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.getSupplier().equals(supplier)) items.add(i);
        return items;
    }

    public List<ProductItem> getItemsByProduct(String name) {
        List<ProductItem> items = null;
        for (Product p : products.values())
            if (p.getName().equals(name)) items = new ArrayList<>(p.getItems().values());
        return items;
    }

    public List<Product> getItemsByCategory(String category, String subCategory, String subSubCategory) {
        List<Product> ret_products = new ArrayList<>();
        if (category != null && !category.equals("")) {
            if (subCategory != null && !subCategory.equals("")) {
                if (subSubCategory != null && !subSubCategory.equals("")) {
                    for (Product p : products.values())
                        if (p.getCat().equals(category) && p.getSub_cat().equals(subCategory) && p.getSub_sub_cat().equals(subSubCategory))
                            ret_products.add(p);
                } else for (Product p : products.values())
                    if (p.getCat().equals(category) && p.getSub_cat().equals(subCategory)) ret_products.add(p);
            } else for (Product p : products.values())
                if (p.getCat().equals(category)) ret_products.add(p);
        } else ret_products = new ArrayList<>(products.values());
        return ret_products;
    }

    public boolean productsInCategory(String category) {
        for (Product p : products.values())
            if (p.getCat().equals(category))
                return false;
        return true;
    }

    public boolean productsInSubCategory(String category, String sub_category) {
        for (Product p : products.values())
            if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category))
                return false;
        return true;
    }

    public boolean productsInSubSubCategory(String category, String sub_category, String sub_sub_category) {
        for (Product p : products.values())
            if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category) && p.getSub_sub_cat().equals(sub_sub_category))
                return false;
        return true;
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
        if (!products.containsKey(Integer.toString(product_id))) throw new Exception("product id does not exist");
    }

    private void checkDates(LocalDateTime start_date, LocalDateTime end_date) throws Exception {
        if (start_date == null) throw new Exception("start date is null");
        if (end_date == null) throw new Exception("end date is null");
        if (end_date.isBefore(start_date)) throw new Exception("end date earlier than start date");
        if (end_date.isBefore(LocalDateTime.now())) throw new Exception("end date has passed");
    }

    private void discountLegal(double discount) throws Exception {
        if (discount > 1 || discount <= 0) throw new Exception("discount percentage illegal");
    }

    private void priceLegal(double price) throws Exception {
        if (price < 0) throw new Exception("price illegal");
    }

    public List<String> getProductIdes() {
        List<String> ProductIdes = new LinkedList<>();
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            ProductIdes.add(entry.getValue().getName());
        }
        return ProductIdes;
    }

    public void restart() {
        products.clear();
    }

    public int getMinAmount(String proName) {
        Product p = products.get(proName);
        return p.getMin_qty();
    }

    public ResponseT<Map<String, Integer>> getDeficiency() {
        Map<String, Integer> deficiency = new HashMap<>();
        for (Product p : products.values()) {
            if (p.getMin_qty() > p.getItems().size())
                deficiency.put(p.getName(), p.getMin_qty());
        }
        return (ResponseT<Map<String, Integer>>) deficiency;
    }
}
