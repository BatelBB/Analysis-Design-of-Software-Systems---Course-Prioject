package groupk.shared.BusinessLayer.Inventory.Service.Objects;

import groupk.shared.BusinessLayer.Inventory.Categories.SubSubCategory;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {
    protected Map<String, groupk.shared.BusinessLayer.Inventory.Service.Objects.SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(groupk.shared.BusinessLayer.Inventory.Categories.SubCategory subCategory) {
        name = subCategory.getName();
        subSubCategories = new HashMap<>();
        Map<String, SubSubCategory> BusinessSubSubCategories = subCategory.getSubSubCategories();
        for (Map.Entry<String, SubSubCategory> entry : BusinessSubSubCategories.entrySet()) {
            groupk.shared.BusinessLayer.Inventory.Service.Objects.SubSubCategory cat = new groupk.shared.BusinessLayer.Inventory.Service.Objects.SubSubCategory(entry.getValue());
            subSubCategories.put(entry.getKey(), cat);
        }
    }
}
