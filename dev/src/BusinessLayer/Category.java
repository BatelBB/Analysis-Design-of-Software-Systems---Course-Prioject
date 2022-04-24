package BusinessLayer;

import java.util.LinkedList;
import java.util.Map;

public class Category {

    private Map<String, SubCategory> subC;
    private String name;

    Category(String name) {
        this.name = name;
    }

    public Map<String, SubCategory> getSubC() { return subC; }

    public String getName() { return name; }

    public void addSubCategory(String name) {
        if (subC.containsKey(name))
            throw new IllegalArgumentException("The SubCategories already exists in the system");
        else {
            SubCategory subCategory = new SubCategory(name);
            subC.put(name, subCategory);
        }
    }


    public void removeSubCategory(String name) {
        if (!subC.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            subC.remove(name);
        }
    }

    public SubCategory getSubCategory(String name) {
        if (!subC.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            return subC.get(name);
        }
    }
}

