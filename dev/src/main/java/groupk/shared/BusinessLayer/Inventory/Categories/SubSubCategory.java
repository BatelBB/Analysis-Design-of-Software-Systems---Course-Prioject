package groupk.shared.BusinessLayer.Inventory.Categories;

import adss_group_k.dataLayer.records.SubSubCategoryRecord;

import java.util.Map;

public class SubSubCategory {

    private String name;

    //CONSTRUCTORS
    public SubSubCategory(String subSubCatName) {
        name = subSubCatName;
    }

    public SubSubCategory(SubSubCategoryRecord sub_sub_category) {
        this.name = sub_sub_category.getName();
    }

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }
}
