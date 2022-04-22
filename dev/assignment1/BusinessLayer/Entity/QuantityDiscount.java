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
}
