package assignment1.BusinessLayer.Entity;

public class Item {
    int catalogNumber;
    String name;
    String category;
    float price;

    public Item(int catalogNumber, String name, String category, float price){
        this.catalogNumber = catalogNumber;
        this.name=name;
        this.category = category;
        this.price = price;
    }
}
