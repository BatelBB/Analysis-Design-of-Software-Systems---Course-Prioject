package BusinessLayer;

import java.util.LinkedList;
import java.util.Map;

public class SubCategory {

    private Map<String, SubSubCategory> subSubCategories;
    private final String name;

    public SubCategory(String subCatName) {
        name = subCatName;
        subSubCategories = new HashMap<>();
    }

    public Map<String, SubSubCategory> getSubSubCategories() {
        return subSubCategories;
    }

    public String getName() {
        return name;
    }

    public void addSubSubCategory(String subSubCatName) {
        SubSubCategory subSubCategory = new SubSubCategory(subSubCatName);
        subSubCategories.put(subSubCatName, subSubCategory);
    }


    public void removeSubSubCategory(String name) {
        if (!subSubCategories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            subSubCategories.remove(name);
        }
    }

    public SubSubCategory getSubSubCategory(String name) {
        if (!subSubCategories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            return subSubCategories.get(name);
        }
    }

}
