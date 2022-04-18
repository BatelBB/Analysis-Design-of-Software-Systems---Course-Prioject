import java.util.LinkedList;
import java.util.Map;

public class SubCategory {

    protected Map<String,SubSubCategory> subSubCategories;
    public String name;

    public SubCategory(String subCatName) {
        name=subCatName;
    }
    public void addSubSubCategory(String subSubCatName){
        try {
            SubSubCategory subSubCategory= new SubSubCategory(subSubCatName);
            subSubCategories.put(name,subSubCategory);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void removeSubSubCategory(String name) throws Exception {
        try {
            subSubCategories.remove(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public SubSubCategory getSubSubCategory(String name) throws Exception {
        try {
            return subSubCategories.get(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
