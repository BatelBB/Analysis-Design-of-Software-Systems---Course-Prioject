package ServiceLayer;

import BusinessLayer.Category;
import BusinessLayer.CategoryController;
import BusinessLayer.SubCategory;
import BusinessLayer.SubSubCategory;

public class CategoryService {
    private final CategoryController categoryController;

    public CategoryService(){categoryController= CategoryController.getInstance();}

    public void addCategory(String name){
        try {
            categoryController.addCategory(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCategory(String name) {
        try {
            categoryController.removeCategory(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ServiceLayer.Objects.Category getCategory(String name) {
        try {
            Category category= categoryController.getCategory(name);
            return new ServiceLayer.Objects.Category(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void addSubCategory(String categoryName, String SubCategoryName) {
        try {
            categoryController.addSubCategory(categoryName, SubCategoryName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeSubCategory(String category, String name) {
        try {
            categoryController.removeSubCategory(category, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ServiceLayer.Objects.SubCategory getSubCategory(String categoryName, String SubCategoryName) {
        try {
            SubCategory subCategory= categoryController.getSubCategory(categoryName, SubCategoryName);
            return new ServiceLayer.Objects.SubCategory(subCategory);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addSubSubCategory(String category, String sub_category, String name) {
        try {
            categoryController.addSubSubCategory(category, sub_category, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeSubSubCategory(String category, String sub_category, String name) {
        try {
            categoryController.removeSubSubCategory(category, sub_category, name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
