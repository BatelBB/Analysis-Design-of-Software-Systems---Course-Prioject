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

    public void addSubCategory(String categoryName, String SubCategoryName) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            category.addSubCategory(SubCategoryName);
        }
    }

    public void removeSubCategory(String categoryName, String SubCategoryName) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            category.removeSubCategory(SubCategoryName);
        }
    }
    public SubCategory getSubCategory(String categoryName, String SubCategoryName) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            return category.getSubCategory(SubCategoryName);
        }
    }

    public void addSubSubCategory(String categoryName, String sub_category, String name) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            SubCategory subCategory= category.getSubCategory(sub_category);
            subCategory.addSubSubCategory(name);
        }
    }

    public void removeSubSubCategory(String categoryName, String sub_category, String name) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            SubCategory subCategory= category.getSubCategory(sub_category);
            subCategory.removeSubSubCategory(name);
        }
    }
}


