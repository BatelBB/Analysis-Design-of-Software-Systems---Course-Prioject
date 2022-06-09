package groupk.shared.business.Inventory.Service.Objects;

public class SubSubCategory {
    String name;

    public SubSubCategory(groupk.shared.business.Inventory.Categories.SubSubCategory subSubCategory) {
        name= subSubCategory.getName();
    }
}
