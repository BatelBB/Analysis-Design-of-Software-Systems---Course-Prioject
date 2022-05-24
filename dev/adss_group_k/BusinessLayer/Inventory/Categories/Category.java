package adss_group_k.BusinessLayer.Inventory.Categories;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private final Map<String, SubCategory> subC;
    private final String name;

    Category(String name) {
        this.name = name;
        subC = new HashMap<>();
    }

    public Map<String, SubCategory> getSubC() {
        return subC;
    }

    public String getName() {
        return name;
    }

    public void addSubCategory(String name) {
        if (subC.containsKey(name))
            throw new IllegalArgumentException("The SubCategory already exists in the system");
        else {
            SubCategory subCategory = new SubCategory(name);
            subC.put(name, subCategory);
        }
    }


    public void removeSubCategory(String name) {
        if (!subC.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            subC.remove(name);
    }

    public SubCategory getSubCategory(String name) {
        if (!subC.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            return subC.get(name);
    }
}

