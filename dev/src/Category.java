import java.util.LinkedList;
import java.util.Map;

public class Category {

    protected Map<String,SubCategory> subC;
    String name;

    Category(String name){
        this.name=name;
    }

    public void addSubCategory(String name){
        try {
            /*if (subC.containsKey(name))
                throw new IllegalArgumentException("The SubCategories already exists in the system");
            else {*/
            SubCategory subCategory = new SubCategory(name);
            subC.put(name,subCategory);
            //}
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void removeSubCategory(String name) throws Exception {
        try {
            subC.remove(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public SubCategory getSubCategory(String name) throws Exception {
        try {
            return subC.get(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



}
