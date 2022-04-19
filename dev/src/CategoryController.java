import java.security.PublicKey;
import java.util.LinkedList;
import java.util.Map;

public class CategoryController {

    protected Map<String, Category> categories;

    public void addCategory(String name){
        if (categories.containsKey(name))
            throw new IllegalArgumentException("The categories already exists in the system");
        else {
            Category category = new Category(name);
            categories.put(name,category);
            }
    }

    public void removeCategory(String name) throws Exception {
        categories.remove(name);
    }

    public Category getCategory(String name) throws Exception {
        return categories.get(name);
    }

    public void UpdateDiscountByCategory(int percentage , String name){
        if (percentage > 100)
            throw new IllegalArgumentException("A discount of more than 100 percent is not possible");
        if (!categories.containsKey(name))
            throw new IllegalArgumentException(("This category does not exist"))
        else
            for (Map.Entry<String, Category> entry : categories.entrySet()) {
                entry.getValue().UpdateDiscountByCategory(percentage);
            }
    }

    //need to check that the supplier is exist
    public void UpdateDiscountBySupplier(int percentage , String SupplierName){
        if (percentage > 100)
            throw new IllegalArgumentException("A discount of more than 100 percent is not possible");
        else
            for (Map.Entry<String, Category> entry : categories.entrySet()) {
                entry.getValue().UpdateDiscountBySupplier(percentage,SupplierName);
            }
    }
    }


