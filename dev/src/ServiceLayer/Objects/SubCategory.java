package ServiceLayer.Objects;

import BusinessLayer.SubSubCategory;

import java.util.HashMap;
import java.util.Map;

public class SubCategory {
    protected Map<String, ServiceLayer.Objects.SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(BusinessLayer.SubCategory subCategory) {
        name = subCategory.getName();
        subSubCategories = new HashMap<>();
        Map<String, SubSubCategory> BusinessSubSubCategories = subCategory.getSubSubCategories();
        for (Map.Entry<String, SubSubCategory> entry : BusinessSubSubCategories.entrySet()) {
            ServiceLayer.Objects.SubSubCategory cat = new ServiceLayer.Objects.SubSubCategory(entry.getValue());
            subSubCategories.put(entry.getKey(), cat);
        }
    }
}
