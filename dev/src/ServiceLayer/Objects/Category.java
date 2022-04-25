package ServiceLayer.Objects;

import BusinessLayer.SubCategory;

import java.util.Map;

public class Category {
    private Map<String, ServiceLayer.Objects.SubCategory> subC;
    private String name;

    public String getName() { return name; }

    public Category(BusinessLayer.Category category) {
        name= category.getName();
        Map<String, SubCategory> BusinessSubC= category.getSubC();
        for (Map.Entry<String, SubCategory> entry : BusinessSubC.entrySet()) {
            subC.put(entry.getKey(), new ServiceLayer.Objects.SubCategory(entry.getValue()));
        }
    }
}
