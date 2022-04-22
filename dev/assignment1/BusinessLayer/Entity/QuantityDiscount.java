package assignment1.BusinessLayer.Entity;

public class QuantityDiscount {
    public final Item item;
    public final int quantity;
    public final float discount;

    public QuantityDiscount(Item item, int quantity, float discount){
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return  EntityUtils.table(
                2, 30, true,
                "  **** QUANTITY DISCOUNT **** ", "",
                "Item name", item.name,
                "Item catalog number", item.catalogNumber,
                "For amounts over", quantity,
                "Discount", String.format("%.2f%%", discount * 100),
                "Supplier (name)", item.getSupplier().name,
                "Supplier (ppn)", item.getSupplier().ppn
        );
    }
}
