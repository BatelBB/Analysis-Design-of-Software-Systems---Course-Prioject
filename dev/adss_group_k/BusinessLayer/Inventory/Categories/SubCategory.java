package adss_group_k.BusinessLayer.Inventory.Categories;

import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.readonly.SubSubcategoryData;
import adss_group_k.shared.response.ResponseT;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {

    private final Map<String, SubSubCategory> subSubCategories;
    private final String name;

    public SubCategory(String subCatName) {
        name = subCatName;
        subSubCategories = new HashMap<>();
    }

    public void addSubSubCategory(String cat_name, String name, PersistenceController pc) throws Exception {
        if (subSubCategories.containsKey(this.name))
            throw new IllegalArgumentException("The SubSubCategory already exists in the system");
        else {
            ResponseT<SubSubcategoryData> r = pc.getSubSubCategories().create(cat_name, this.name, name);
            if (r.success)
                subSubCategories.put(name, new SubSubCategory(name));
            else
                throw new Exception(r.error);
        }
    }

    public void removeSubSubCategory(String cat_name, String name, PersistenceController pc) throws Exception {
        if (subSubCategoryExists(name)) {
            int r = pc.getSubSubCategories().runDeleteQuery(cat_name, this.name, name);
            if (r == -1)
                throw new Exception("Error deleting subSubCategory from DB");
            else
                subSubCategories.remove(name);
        }
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
