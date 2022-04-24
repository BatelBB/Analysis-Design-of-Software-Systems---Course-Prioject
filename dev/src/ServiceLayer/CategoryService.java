package ServiceLayer;

import BusinessLayer.Category;
import BusinessLayer.CategoryController;

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

}
