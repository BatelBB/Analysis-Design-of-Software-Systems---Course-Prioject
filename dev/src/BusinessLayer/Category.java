package BusinessLayer;

import java.util.LinkedList;
import java.util.Map;

public class Category {

    protected Map<String, SubCategory> subC;
    String name;

    Category(String name) {
        this.name = name;
    }

    public void addSubCategory(String name) {
        if (subC.containsKey(name))
            throw new IllegalArgumentException("The SubCategories already exists in the system");
        else {
            SubCategory subCategory = new SubCategory(name);
            subC.put(name, subCategory);
        }
    }


    public void removeSubCategory(String name){ subC.remove(name); }

    public SubCategory getSubCategory(String name) {return subC.get(name);}

    public void UpdateDiscountByCategory(int percentage) {
        for (Map.Entry<String, SubCategory> entry : subC.entrySet()) {
            entry.getValue().UpdateDiscountByCategory(percentage);
        }
    }

    public void UpdateDiscountBySupplier(int percentage, String supplierName) {
        for (Map.Entry<String, SubCategory> entry : subC.entrySet()) {
            entry.getValue().UpdateDiscountBySupplier(percentage,supplierName);
        }
    }
}

