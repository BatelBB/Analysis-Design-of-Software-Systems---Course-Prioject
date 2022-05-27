package adss_group_k.BusinessLayer.Suppliers.BussinessObject;

import adss_group_k.BusinessLayer.Inventory.Categories.SubSubCategory;
import adss_group_k.dataLayer.records.readonly.ItemData;
import adss_group_k.shared.utils.Utils;

public class Item {

    private final SubSubCategory subSubcategory;
    private ItemData data;
    private Supplier supplier;

    public Item(ItemData data, Supplier supplier, SubSubCategory subSubCategory) {
        this.supplier = supplier;
        this.data = data;
        this.subSubcategory = subSubCategory;
    }

    public Supplier getSupplier() {
        return supplier;
    }


    public int getCatalogNumber() {
        return data.getCatalogNumber();
    }

    public String getCategory() {
        return category;
    }

    @Override
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public float priceForAmount(int amount) {
        return controller.priceForAmount(this, amount);
    }

    @Override
    public String toString() {
        return Utils.table(
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
