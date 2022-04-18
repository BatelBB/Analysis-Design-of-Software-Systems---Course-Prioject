import java.util.Map;

public class SubSubCategory {

    protected Map<String,Product> products;
    String name;

    public SubSubCategory(String subSubCatName) {
        name=subSubCatName;
    }

    public void addProduct(String name, String manufacturer, double man_price, double cus_price, int min_qnt, int supply_time){
        try {
            /*if (products.containsKey(name))
                throw new IllegalArgumentException("The product already exists in the system");
            else {*/
                Product product = new Product(name, manufacturer, man_price, cus_price, min_qnt, supply_time);
                products.put(name,product);
            //}
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void removeProduct(String name) throws Exception {
        try {
            products.remove(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Product getProduct(String name) throws Exception {
        try {
            return products.get(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
