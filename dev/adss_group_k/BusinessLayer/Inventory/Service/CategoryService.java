package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Category;
import adss_group_k.BusinessLayer.Inventory.CategoryController;
import adss_group_k.BusinessLayer.Inventory.SubCategory;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;

import java.util.List;

public class CategoryService {
    private final CategoryController category_controller;

    public CategoryService() {
        category_controller = CategoryController.getInstance();
    }

    public ResponseT<List<String>> getCategoriesNames() {
        try {
            return ResponseT.fromValue(category_controller.getCategoriesNames());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    public Response addCategory(String name) {
        try {
            category_controller.addCategory(name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeCategory(String name, boolean safe_remove) {
        try {
            category_controller.removeCategory(name, safe_remove);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<adss_group_k.BusinessLayer.Inventory.Service.Objects.Category> getCategory(String name) {
        try {
            Category category = category_controller.getCategory(name);
            return ResponseT.fromValue(new adss_group_k.BusinessLayer.Inventory.Service.Objects.Category(category));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addSubCategory(String categoryName, String SubCategoryName) {
        try {
            category_controller.addSubCategory(categoryName, SubCategoryName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeSubCategory(String category, String name, boolean safe_remove) {
        try {
            category_controller.removeSubCategory(category, name, safe_remove);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<adss_group_k.BusinessLayer.Inventory.Service.Objects.SubCategory> getSubCategory(String categoryName, String SubCategoryName) {
        try {
            SubCategory subCategory = category_controller.getSubCategory(categoryName, SubCategoryName);
            return ResponseT.fromValue(new adss_group_k.BusinessLayer.Inventory.Service.Objects.SubCategory(subCategory));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addSubSubCategory(String category, String sub_category, String name) {
        try {
            category_controller.addSubSubCategory(category, sub_category, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeSubSubCategory(String category, String sub_category, String name, boolean safe_remove) {
        try {
            category_controller.removeSubSubCategory(category, sub_category, name, safe_remove);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }

    public void restart() {
        category_controller.restart();
    }
}
