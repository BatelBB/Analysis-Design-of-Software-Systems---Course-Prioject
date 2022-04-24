package BusinessLayer;

import java.util.LinkedList;
import java.util.Map;

public class SubCategory {

    protected Map<String, SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(String subCatName) {
        name=subCatName;
    }
    public void addSubSubCategory(String subSubCatName){
            SubSubCategory subSubCategory= new SubSubCategory(subSubCatName);
            subSubCategories.put(name,subSubCategory);
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


    public void UpdateDiscountByCategory(int percentage) {
        for (Map.Entry<String, SubSubCategory> entry : subSubCategories.entrySet()) {
            entry.getValue().UpdateDiscountByCategory(percentage);
        }
    }

    public void UpdateDiscountBySupplier(int percentage, String supplierName) {
        for (Map.Entry<String, SubSubCategory> entry : subSubCategories.entrySet()) {
            entry.getValue().UpdateDiscountBySupplier(percentage, supplierName);
        }
    }
}
