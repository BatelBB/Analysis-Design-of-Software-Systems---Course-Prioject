package adss_group_k.BusinessLayer.Inventory.Categories;

import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.CategoryRecord;
import adss_group_k.dataLayer.records.SubcategoryRecord;
import adss_group_k.dataLayer.records.readonly.SubcategoryData;
import adss_group_k.shared.response.ResponseT;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private Map<String, SubCategory> subC;
    private final String name;
    private final PersistenceController pc;

    //CONSTRUCTORS

    //from addCategory
    public Category(String name, PersistenceController pc) {
        this.name = name;
        subC = new HashMap<>();
        this.pc = pc;
    }

    //from DAL
    public Category(CategoryRecord category, PersistenceController pc) {
        this.name = category.getName();
        this.pc = pc;
        pc.getSubcategories().all().forEach(this::addFromExisting);
    }

    //METHODS
    public void addSubCategory(String name) throws Exception {
        if (subC.containsKey(name))
            throw new IllegalArgumentException("The SubCategory already exists in the system");
        else {
            ResponseT<SubcategoryData> r = pc.getSubcategories().create(this.name, name);
            if (r.success)
                subC.put(name, new SubCategory(name, pc));
            else
                throw new Exception(r.error);
        }
    }

    public void removeSubCategory(String name) throws Exception {
        if (subCategoryExists(name)) {
            int r = pc.getSubcategories().runDeleteQuery(this.name, name);
            if (r == -1)
                throw new Exception("Error deleting subCategory from DB");
            else
                subC.remove(name);
        }
    }

    //GETTERS AND SETTERS
    public SubCategory getSubCategory(String name) {
        if (subCategoryExists(name))
            return subC.get(name);
        return null;
    }

    public Map<String, SubCategory> getSubC() {
        return subC;
    }

    public String getName() {
        return name;
    }

    //PRIVATE METHODS
    private boolean subCategoryExists(String name) {
        if (!subC.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        return true;
    }

    private void addFromExisting(SubcategoryRecord sub_category) {
        subC.put(sub_category.getName(), new SubCategory(sub_category, pc));
    }
}

