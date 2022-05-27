package adss_group_k.BusinessLayer.Inventory.Controllers;


import adss_group_k.BusinessLayer.Inventory.Categories.Category;
import adss_group_k.BusinessLayer.Inventory.Categories.SubCategory;
import adss_group_k.dataLayer.dao.CategoryDAO;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.CategoryRecord;
import adss_group_k.dataLayer.records.readonly.CategoryData;
import adss_group_k.shared.response.ResponseT;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController {

    private Map<String, Category> categories;
    private static CategoryController category_controller;
    private PersistenceController pc;

    //singleton instance
    public static CategoryController getInstance(PersistenceController pc) {
        if (category_controller == null)
            category_controller = new CategoryController(pc);
        return category_controller;
    }

    //constructor
    private CategoryController(PersistenceController pc) {
        categories = new HashMap<>();
        this.pc = pc;
        pc.getCategories().all().forEach(this::addFromExisting);
    }

    public void addCategory(String name) throws Exception {
        if (categories.containsKey(name))
            throw new IllegalArgumentException("Category name already exists in the system");
        else {
            ResponseT<CategoryData> r = pc.getCategories().create(name);
            if (r.success) {
                Category category = new Category(name, pc);
                categories.put(name, category);
            } else
                throw new Exception(r.error);
        }
    }

    public void removeCategory(String name, boolean safe_remove) throws Exception {
        if (!safe_remove)
            throw new IllegalArgumentException("Category has products in it");
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            int r = pc.getCategories().runDeleteQuery(name);
            if (r == -1)
                throw new Exception("Error deleting category from DB");
            else
                categories.remove(name);
        }
    }

    public void addSubCategory(String categoryName, String SubCategoryName) throws Exception {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            category.addSubCategory(SubCategoryName);
        }
    }

    public void removeSubCategory(String categoryName, String SubCategoryName, boolean safe_remove) throws Exception {
        if (!safe_remove)
            throw new IllegalArgumentException("Subcategory has products in it");
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            categories.get(categoryName).removeSubCategory(SubCategoryName);
    }

    public void addSubSubCategory(String categoryName, String sub_category, String name) throws Exception {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            categories.get(categoryName).getSubCategory(sub_category).addSubSubCategory(categoryName, name);
    }

    public void removeSubSubCategory(String categoryName, String sub_category, String name, boolean safe_remove) throws Exception {
        if (!safe_remove)
            throw new IllegalArgumentException("Subsubcategory has products in it");
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            SubCategory subCategory = category.getSubCategory(sub_category);
            subCategory.removeSubSubCategory(categoryName, name);
        }
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public Category getCategory(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            return categories.get(name);
    }

    public SubCategory getSubCategory(String categoryName, String SubCategoryName) {
        if (!categories.containsKey(categoryName))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            Category category = categories.get(categoryName);
            return category.getSubCategory(SubCategoryName);
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

    public void restart() {
        categories.clear();
    }

    private void addFromExisting(CategoryRecord category) {
        categories.put(category.getName(), new Category(category, pc));
    }
}


