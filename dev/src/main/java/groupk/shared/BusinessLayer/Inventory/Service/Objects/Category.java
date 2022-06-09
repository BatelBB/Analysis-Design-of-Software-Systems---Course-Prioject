package groupk.shared.BusinessLayer.Inventory.Service.Objects;

import groupk.shared.BusinessLayer.Inventory.Categories.SubCategory;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private Map<String, groupk.shared.BusinessLayer.Inventory.Service.Objects.SubCategory> subC;
    private String name;

    public String getName() {
        return name;
    }

    public Category(groupk.shared.BusinessLayer.Inventory.Categories.Category category) {
        name = category.getName();
        subC = new HashMap<>();
        Map<String, SubCategory> BusinessSubC = category.getSubC();
        for (Map.Entry<String, SubCategory> entry : BusinessSubC.entrySet()) {
            subC.put(entry.getKey(), new groupk.shared.BusinessLayer.Inventory.Service.Objects.SubCategory(entry.getValue()));
        }
    }
}
