package BusinessLayer;

import java.util.HashMap;
import java.util.Map;

public class CategoryController {

    protected Map<String, Category> categories;

    private static CategoryController categoryController;

    public static CategoryController getInstance()
    {
        if (categoryController==null)
            categoryController= new CategoryController();
        return categoryController;
    }

    private CategoryController(){
        categories = new HashMap<>();
    }

    public void addCategory(String name){
        if (categories.containsKey(name))
            throw new IllegalArgumentException("The categories already exists in the system");
        else {
            Category category = new Category(name);
            categories.put(name,category);
        }
    }

    public void removeCategory(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            categories.remove(name);
    }

    public Category getCategory(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            return categories.get(name);
    }
}


