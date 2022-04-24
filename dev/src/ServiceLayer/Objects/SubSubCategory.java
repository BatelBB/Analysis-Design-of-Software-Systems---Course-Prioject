package ServiceLayer.Objects;

public class SubSubCategory {
    String name;

    public SubSubCategory(BusinessLayer.SubSubCategory subSubCategory) {
        name= subSubCategory.getName();
    }
}
