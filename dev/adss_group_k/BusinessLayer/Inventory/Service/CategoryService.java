package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Categories.Category;
import adss_group_k.BusinessLayer.Inventory.Controllers.CategoryController;
import adss_group_k.BusinessLayer.Inventory.Categories.SubCategory;
import adss_group_k.serviceLayer.ServiceBase;

import java.util.List;

public class CategoryService extends ServiceBase {
    private final CategoryController category_controller;

    public CategoryService(CategoryController category_controller) {
        this.category_controller = category_controller;
    }

    public ResponseT<List<String>> getCategoriesNames() {
        return responseFor(category_controller::getCategoriesNames);
    }

    public Response addCategory(String name) {
        return responseForVoid(() -> category_controller.addCategory(name));
    }

    public Response addSubCategory(String categoryName, String subCategoryName) {
        return responseForVoid(() -> category_controller.addSubCategory(categoryName, subCategoryName));
    }

    public Response addSubSubCategory(String category, String sub_category, String name) {
        return responseForVoid(() -> category_controller.addSubSubCategory(category, sub_category, name));
    }

    public Response removeCategory(String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeCategory(name, safe_remove));
    }

    public Response removeSubCategory(String category, String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeSubCategory(category, name, safe_remove));
    }

    public Response removeSubSubCategory(String category, String sub_category, String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeSubSubCategory(category, sub_category, name, safe_remove))
    }

    public ResponseT<Category> getCategory(String name) {
        return responseFor(() -> category_controller.getCategory(name));
    }

    public ResponseT<adss_group_k.BusinessLayer.Inventory.Service.Objects.SubCategory> getSubCategory(String categoryName, String SubCategoryName) {
        return responseFor(() -> {
            SubCategory subCategory = category_controller.getSubCategory(categoryName, SubCategoryName);
            return new adss_group_k.BusinessLayer.Inventory.Service.Objects.SubCategory(subCategory);
        });
    }
}
