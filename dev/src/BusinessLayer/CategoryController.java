package BusinessLayer;

import java.util.Map;

public class CategoryController {

    protected Map<String, Category> categories;

    private static CategoryController categoryController;

    public static CategoryController getInstance()
    {
        if (categoryController==null)
            categoryController= new CategoryController();
        return categoryController;
    }

    private CategoryController(){
        categoryController = CategoryController.getInstance();
    }

    public void addCategory(String name){
        if (categories.containsKey(name))
            throw new IllegalArgumentException("The categories already exists in the system");
        else {
            Category category = new Category(name);
            categories.put(name,category);
            }
    }

    public void removeCategory(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            categories.remove(name);
    }

    public Category getCategory(String name) {
        if (!categories.containsKey(name))
            throw new IllegalArgumentException("Category doesn't exists");
        else
            return categories.get(name);
    }

    public void UpdateDiscountByCategory(int percentage , String name){
        if (percentage > 100)
            throw new IllegalArgumentException("A discount of more than 100 percent is not possible");
        if (!categories.containsKey(name))
            throw new IllegalArgumentException(("This category does not exist"));
        else
            for (Map.Entry<String, Category> entry : categories.entrySet()) {
                entry.getValue().UpdateDiscountByCategory(percentage);
            }
    }

    //need to check that the supplier is exist
    public void UpdateDiscountBySupplier(int percentage , String SupplierName){
        if (percentage > 100)
            throw new IllegalArgumentException("A discount of more than 100 percent is not possible");
        else
            for (Map.Entry<String, Category> entry : categories.entrySet()) {
                entry.getValue().UpdateDiscountBySupplier(percentage,SupplierName);
            }
    }
    }


