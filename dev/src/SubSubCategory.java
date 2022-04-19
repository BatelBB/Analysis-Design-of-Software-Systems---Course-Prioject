import java.util.Map;

public class SubSubCategory {

    protected Map<String,Product> products;
    String name;

    public SubSubCategory(String subSubCatName) {
        name=subSubCatName;
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qnt, int supply_time){
        if (products.containsKey(name))
                throw new IllegalArgumentException("The product already exists in the system");
        else {
            Product product = new Product(name, manufacturer, man_price, cus_price, min_qnt, supply_time);
            products.put(name,product);
        }
    }


    public void removeProduct(String name){
            products.remove(name);
    }


    public Product getProduct(String name) {
        return products.get(name);
    }


    public void UpdateDiscountByCategory(int percentage) {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            entry.getValue().SetDiscount(percentage);
        }
    }

    public void UpdateDiscountBySupplier(int percentage, String supplierName) {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            if (entry.getValue().getSupplierName().equals(supplierName))
                entry.getValue().SetDiscount(percentage);
        }
    }
}
