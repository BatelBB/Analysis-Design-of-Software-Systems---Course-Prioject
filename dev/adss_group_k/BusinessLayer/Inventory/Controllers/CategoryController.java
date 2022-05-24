package adss_group_k.BusinessLayer.Inventory.Controllers;


import adss_group_k.BusinessLayer.Inventory.Categories.Category;
import adss_group_k.BusinessLayer.Inventory.Categories.SubCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController {

    private Map<String, Category> categories;
    private static CategoryController category_controller;

    //singleton instance
    public static CategoryController getInstance() {
        if (category_controller == null)
            category_controller = new CategoryController();
        return category_controller;
    }

    //constructor
    private CategoryController() {
        categories = new HashMap<>();
    }

    public void addCategory(String name) {
        if (categories.containsKey(name))
            throw new IllegalArgumentException("The categories already exists in the system");
        else {
            Category category = new Category(name);
            categories.put(name, category);
        }
    }

    public void removeCategory(String name, boolean safe_remove) {
        if(!safe_remove)
            throw new IllegalArgumentException("Category has products in it");
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

    public void removeSubCategory(String categoryName, String SubCategoryName, boolean safe_remove) {
        if(!safe_remove)
            throw new IllegalArgumentException("Subcategory has products in it");
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
            SubCategory subCategory = category.getSubCategory(sub_category);
            subCategory.addSubSubCategory(name);
        }
    }

    public void removeSubSubCategory(String categoryName, String sub_category, String name, boolean safe_remove) {
        if(!safe_remove)
            throw new IllegalArgumentException("Subsubcategory has products in it");
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            SubCategory subCategory = category.getSubCategory(sub_category);
            subCategory.removeSubSubCategory(name);
        }
    }

    public List<String> getCategoriesNames() {
//        List<String> CategoriesNames = new LinkedList<>();
//        for (Map.Entry<String, Category> entry : categories.entrySet()) {
//            CategoriesNames.add(entry.getKey());
//        }
//        return CategoriesNames;
        return new ArrayList<>(categories.keySet());
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public void restart() {
        categories.clear();
    }
}


