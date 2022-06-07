package adss_group_k.BusinessLayer.Inventory.Controllers;

import adss_group_k.BusinessLayer.Inventory.Product;
import adss_group_k.BusinessLayer.Inventory.ProductItem;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ProductRecord;
import adss_group_k.dataLayer.records.readonly.ProductData;


import java.time.LocalDate;
import java.util.*;

public class ProductController {
    private Map<Integer, Product> products;
    private CategoryController category_controller;
    private PersistenceController pc;


    //constructors
    public ProductController(PersistenceController pc, CategoryController category_controller) {
        products = new HashMap<>();
        this.pc = pc;
        this.category_controller = category_controller;
        pc.getProducts().all().forEach(this::addFromExisting);
    }

    public void updateCategoryCusDiscount(float discount, LocalDate start_date, LocalDate end_date, String category, String sub_category, String subsub_category) throws Exception {
        if (category != null && !category.equals("")) {
            if (sub_category != null && !sub_category.equals("")) {
                if (subsub_category != null && !subsub_category.equals("")) {
                    for (Product p : products.values())
                        //for sub-sub-category
                        if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category) && p.getSub_sub_cat().equals(subsub_category))
                            for (int i : p.getItems().keySet())
                                p.updateItemCusDiscount(i, discount, start_date, end_date);
                } else
                    for (Product p : products.values())
                        //for sub-category
                        if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category))
                            for (int i : p.getItems().keySet())
                                p.updateItemCusDiscount(i, discount, start_date, end_date);
            } else
                for (Product p : products.values())
                    //for category
                    if (p.getCat().equals(category))
                        for (int i : p.getItems().keySet())
                            p.updateItemCusDiscount(i, discount, start_date, end_date);
        } else {
            if (sub_category == null || sub_category.equals("") && subsub_category == null || subsub_category.equals(""))
                for (Product p : products.values())
                    //for all
                    for (int i : p.getItems().keySet())
                        p.updateItemCusDiscount(i, discount, start_date, end_date);
            else throw new Exception("missing category input");
        }
    }

    public void updateProductCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id) throws Exception {
        productExists(product_id);
        for (int i : products.get(product_id).getItems().keySet())
            products.get(product_id).updateItemCusDiscount(i, discount, start_date, end_date);
    }

    public void updateItemCusDiscount(int product_id, int item_id, float discount, LocalDate start_date, LocalDate end_date) throws Exception {
        discountLegal(discount);
        checkDates(start_date, end_date);
        productExists(product_id);
        products.get(product_id).updateItemCusDiscount(item_id, discount, start_date, end_date);
    }

    public void updateProductCusPrice(int product_id, float price) throws Exception {
        priceLegal(price);
        productExists(product_id);
        products.get(product_id).setCus_price(price);
    }

    public void updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) throws Exception {
        if (defect_reporter == null) throw new Exception("defect reporter is null");
        if (defect_reporter.equals("")) throw new Exception("defect reporter is empty");
        productExists(product_id);
        products.get(product_id).updateItemDefect(item_id, is_defect, defect_reporter);
    }

    public String getItemLocation(int product_id, int item_id) throws Exception {
        productExists(product_id);
        return products.get(product_id).getItemLocation(item_id);
    }

    public Product addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) throws Exception {
        try {
            if (!category_controller.getCategories().containsKey(category))
                throw new Exception("category doesn't exist");
            if (!category_controller.getCategories().get(category).getSubC().containsKey(sub_category))
                throw new Exception("sub-category doesn't exist");
            if (!category_controller.getCategories().get(category).getSubC().get(sub_category).getSubSubCategories().containsKey(subsub_category))
                throw new Exception("sub-sub-category doesn't exist");
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
            if (category == null || category.equals(""))
                throw new Exception("category name empty");
            if (sub_category == null || sub_category.equals(""))
                throw new Exception("sub_category name empty");
            if (subsub_category == null || subsub_category.equals(""))
                throw new Exception("sub-sub-category name empty");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        int id = pc.getProducts().getMaxId() + 1;
        ProductData r = pc.getProducts().create(
                0,
                id,
                name,
                cus_price,
                min_qty,
                0,
                0,
                category,
                sub_category,
                subsub_category
        );
        Product ret = new Product(r, pc);
        products.put(id, ret);
        return ret;
    }

    public void removeProduct(int product_id) throws Exception {
        productExists(product_id);
        for (int pi : products.get(product_id).getItems().keySet())
            removeItem(product_id, pi);
        int r = pc.getProducts().runDeleteQuery(product_id);
        if (r == -1)
            throw new Exception("Error deleting Product from DB");
        products.remove(product_id);
    }

    public ProductItem addItem(int product_id, String store, String location, int supplier, LocalDate expiration_date, boolean on_shelf) throws Exception {
        productExists(product_id);
        return products.get(product_id).addItem(store, location, supplier, expiration_date, on_shelf);
    }

    public void removeItem(int product_id, int item_id) throws Exception {
        productExists(product_id);
        products.get(product_id).removeItem(item_id);
    }

    public void changeItemLocation(int product_id, int item_id, String location) throws Exception {
        productExists(product_id);
        products.get(product_id).changeItemLocation(item_id, location);
    }

    public void changeItemOnShelf(int product_id, int item_id, boolean on_shelf) throws Exception {
        productExists(product_id);
        products.get(product_id).changeItemOnShelf(item_id, on_shelf);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    //FOR REPORTS
    public List<Product> getMissingProducts() {
        List<Product> missing_products = new ArrayList<>();
        for (Product p : products.values())
            if (p.getItems().size() < p.getMin_qty())
                missing_products.add(p);
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
                if (i.getExpirationDate().isBefore(LocalDate.now())) expired_items.add(i);
        return expired_items;
    }

    public List<ProductItem> getDefectiveItems() {
        List<ProductItem> defective_items = new ArrayList<>();
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.is_defect()) defective_items.add(i);
        return defective_items;
    }

    public List<ProductItem> getItemsBySupplier(int supplier) {
        List<ProductItem> items = new ArrayList<>();
        for (Product p : products.values())
            for (ProductItem i : p.getItems().values())
                if (i.getSupplier() == supplier) items.add(i);
        return items;
    }

    public List<ProductItem> getItemsByProduct(String name) {
        List<ProductItem> items = new ArrayList<>();
        for (Product p : products.values())
            if (p.getName().equals(name))
                items = new ArrayList<>(p.getItems().values());
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
            if (p.getCat().equals(category)) return false;
        return true;
    }

    public boolean productsInSubCategory(String category, String sub_category) {
        for (Product p : products.values())
            if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category)) return false;
        return true;
    }

    public boolean productsInSubSubCategory(String category, String sub_category, String sub_sub_category) {
        for (Product p : products.values())
            if (p.getCat().equals(category) && p.getSub_cat().equals(sub_category) && p.getSub_sub_cat().equals(sub_sub_category))
                return false;
        return true;
    }


    //getters and setters

    //private methods
    private void productExists(int product_id) throws Exception {
        if (!products.containsKey(product_id)) throw new Exception("product id does not exist");
    }

    private void checkDates(LocalDate start_date, LocalDate end_date) throws Exception {
        if (start_date == null) throw new Exception("start date is null");
        if (end_date == null) throw new Exception("end date is null");
        if (end_date.isBefore(start_date)) throw new Exception("end date earlier than start date");
        if (end_date.isBefore(LocalDate.now())) throw new Exception("end date has passed");
    }

    private void discountLegal(double discount) throws Exception {
        if (discount > 1 || discount <= 0) throw new Exception("discount percentage illegal");
    }

    private void priceLegal(double price) throws Exception {
        if (price < 0) throw new Exception("price illegal");
    }

    private void addFromExisting(ProductRecord product) {
        products.put(product.getId(), new Product(product, pc));
    }

    public List<String> getProductNames() {
        List<String> ProductIdes = new LinkedList<>();
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
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

    public Map<String, Integer> getDeficiency() {
        Map<String, Integer> deficiency = new HashMap<>();
        for (Product p : products.values()) {
            if (p.getMin_qty() > p.getItems().size()) deficiency.put(p.getName(), p.getMin_qty());
        }
        return (Map<String, Integer>) deficiency;
    }
}
