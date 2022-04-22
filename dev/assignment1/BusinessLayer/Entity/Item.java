package assignment1.BusinessLayer.Entity;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Controller.ItemController;

import java.util.List;

public class Item {
    private final Supplier supplier;
    private final ItemController controller;
    int catalogNumber;
    String name;
    String category;
    float price;

    public Item(Supplier supplier, int catalogNumber, String name, String category, float price, ItemController controller){
        this.supplier = supplier;
        this.catalogNumber = catalogNumber;
        this.name=name;
        this.category = category;
        this.price = price;
        this.controller = controller;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float priceForAmount(int amount) {
        return controller.priceForAmount(this, amount);
    }

    @Override
    public String toString() {
        return EntityUtils.table(
                2, 25, true,
                "    **** ITEM **** ", "",
                "Name", name,
                "Price", price,
                "Category", category,
                "Supplier PPN", supplier.getPpn(),
                "Supplier name", supplier.getName(),
                "Catalog number", catalogNumber
        );
    }

}
