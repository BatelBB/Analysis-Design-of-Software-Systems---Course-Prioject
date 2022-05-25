package adss_group_k.BusinessLayer.Inventory.Categories;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {

    private Map<String, SubSubCategory> subSubCategories;
    private final String name;

    public SubCategory(String subCatName) {
        name = subCatName;
        subSubCategories = new HashMap<>();
    }

    public void addSubSubCategory(String subSubCatName) {
        if (subSubCategories.containsKey(name))
            throw new IllegalArgumentException("The SubSubCategory already exists in the system");
        else {
            SubSubCategory subSubCategory = new SubSubCategory(subSubCatName);
            subSubCategories.put(subSubCatName, subSubCategory);
        }
    }

    public void removeSubSubCategory(String name) {
        if (subSubCategoryExists(name))
            subSubCategories.remove(name);
    }

    public SubSubCategory getSubSubCategory(String name) {
        if (subSubCategoryExists(name))
            return subSubCategories.get(name);
        return null;
    }

    public Map<String, SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    public String getName() {
        return name;
    }

    private boolean subSubCategoryExists(String name) {
        if (!subSubCategories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        return true;
    }
}
