package groupk.shared.BusinessLayer.Inventory.Service.Objects;

public class SubSubCategory {
    String name;

    public SubSubCategory(groupk.shared.BusinessLayer.Inventory.Categories.SubSubCategory subSubCategory) {
        name= subSubCategory.getName();
    }
}
