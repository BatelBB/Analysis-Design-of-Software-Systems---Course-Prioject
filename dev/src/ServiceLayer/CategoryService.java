package ServiceLayer;

import BusinessLayer.Category;
import BusinessLayer.CategoryController;
import BusinessLayer.SubCategory;

import java.util.List;

public class CategoryService {
    private final CategoryController categoryController;

    public CategoryService() {
        categoryController = CategoryController.getInstance();
    }

    public static ResponseT<List<String>> getCategoriesNames() {
        try {
            return ResponseT.fromValue(CategoryController.getCategoriesNames());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    public Response addCategory(String name) {
        try {
            categoryController.addCategory(name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeCategory(String name) {
        try {
            categoryController.removeCategory(name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.Category> getCategory(String name) {
        try {
            Category category = categoryController.getCategory(name);
            return ResponseT.fromValue(new ServiceLayer.Objects.Category(category));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addSubCategory(String categoryName, String SubCategoryName) {
        try {
            categoryController.addSubCategory(categoryName, SubCategoryName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeSubCategory(String category, String name) {
        try {
            categoryController.removeSubCategory(category, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.SubCategory> getSubCategory(String categoryName, String SubCategoryName) {
        try {
            SubCategory subCategory = categoryController.getSubCategory(categoryName, SubCategoryName);
            return ResponseT.fromValue(new ServiceLayer.Objects.SubCategory(subCategory));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addSubSubCategory(String category, String sub_category, String name) {
        try {
            categoryController.addSubSubCategory(category, sub_category, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeSubSubCategory(String category, String sub_category, String name) {
        try {
            categoryController.removeSubSubCategory(category, sub_category, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }

    public void restart() {
        categoryController.restart();
    }
}
