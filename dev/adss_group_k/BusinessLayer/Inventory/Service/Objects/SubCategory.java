package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.SubSubCategory;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {
    protected Map<String, adss_group_k.BusinessLayer.Inventory.Service.Objects.SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(adss_group_k.BusinessLayer.Inventory.SubCategory subCategory) {
        name = subCategory.getName();
        subSubCategories = new HashMap<>();
        Map<String, SubSubCategory> BusinessSubSubCategories = subCategory.getSubSubCategories();
        for (Map.Entry<String, SubSubCategory> entry : BusinessSubSubCategories.entrySet()) {
            adss_group_k.BusinessLayer.Inventory.Service.Objects.SubSubCategory cat = new adss_group_k.BusinessLayer.Inventory.Service.Objects.SubSubCategory(entry.getValue());
            subSubCategories.put(entry.getKey(), cat);
        }
    }
}
