package adss_group_k.BusinessLayer.Inventory.Categories;

import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.SubSubCategoryRecord;
import adss_group_k.dataLayer.records.SubcategoryRecord;
import adss_group_k.dataLayer.records.readonly.SubSubcategoryData;
import adss_group_k.shared.response.ResponseT;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {

    private Map<String, SubSubCategory> subSubCategories;
    private final String name;
    private final PersistenceController pc;

    //CONSTRUCTORS

    //from addSubCategory
    public SubCategory(String subCatName, PersistenceController pc) {
        name = subCatName;
        subSubCategories = new HashMap<>();
        this.pc = pc;
    }

    //from DAL
    public SubCategory(SubcategoryRecord sub_category, PersistenceController pc) {
        this.name = sub_category.getName();
        this.pc = pc;
        pc.getSubSubCategories().all().forEach(this::addFromExisting);
    }

    //METHODS
    public void addSubSubCategory(String cat_name, String name) throws Exception {
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

    public void removeSubSubCategory(String cat_name, String name) throws Exception {
        if (subSubCategoryExists(name)) {
            int r = pc.getSubSubCategories().runDeleteQuery(cat_name, this.name, name);
            if (r == -1)
                throw new Exception("Error deleting subSubCategory from DB");
            else
                subSubCategories.remove(name);
        }
    }

    //GETTERS AND SETTERS
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

    //PRIVATE METHODS
    private boolean subSubCategoryExists(String name) {
        if (!subSubCategories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        return true;
    }

    private void addFromExisting(SubSubCategoryRecord sub_sub_category) {
        subSubCategories.put(sub_sub_category.getName(), new SubSubCategory(sub_sub_category));
    }
}
