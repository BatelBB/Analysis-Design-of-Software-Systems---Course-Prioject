import java.security.PublicKey;
import java.util.LinkedList;
import java.util.Map;

public class CategoryController {

    protected Map<String, Category> categories;

    public void addCategory(String name){
        try {
            /*if (categories.containsKey(name))
                throw new IllegalArgumentException("The categories already exists in the system");
            else {*/
            Category category = new Category(name);
            categories.put(name,category);
            //}
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void removeCategory(String name) throws Exception {
        try {
            categories.remove(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Category getCategory(String name) throws Exception {
        try {
            return categories.get(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
