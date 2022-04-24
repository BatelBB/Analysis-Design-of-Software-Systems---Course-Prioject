package ServiceLayer.Objects;

import BusinessLayer.SubSubCategory;

import java.util.Map;

public class SubCategory {
    protected Map<String, ServiceLayer.Objects.SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(BusinessLayer.SubCategory subCategory) {
        name= subCategory.getName();
        Map<String, SubSubCategory> BusinessSubSubCategories=subCategory.getSubSubCategories();
        for (Map.Entry<String, SubSubCategory> entry : BusinessSubSubCategories.entrySet()) {
            subSubCategories.put(entry.getKey(), new ServiceLayer.Objects.SubSubCategory(entry.getValue()));
        }
    }
}
