package adss_group_k.inventory.ServiceLayer.Objects;

public class SubSubCategory {
    String name;

    public SubSubCategory(adss_group_k.inventory.BusinessLayer.SubSubCategory subSubCategory) {
        name= subSubCategory.getName();
    }
}
