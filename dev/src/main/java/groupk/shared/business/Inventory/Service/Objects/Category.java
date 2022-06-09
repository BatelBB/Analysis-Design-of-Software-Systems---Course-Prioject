package groupk.shared.business.Inventory.Service.Objects;

import groupk.shared.business.Inventory.Categories.SubCategory;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private Map<String, SubCategory> subC;
    private String name;

    public String getName() {
        return name;
    }

    public Category(groupk.shared.business.Inventory.Categories.Category category) {
        name = category.getName();
        subC = new HashMap<>();
        Map<String, SubCategory> BusinessSubC = category.getSubC();
        for (Map.Entry<String, SubCategory> entry : BusinessSubC.entrySet()) {
            subC.put(entry.getKey(), new SubCategory(entry.getValue()));
        }
    }
}
