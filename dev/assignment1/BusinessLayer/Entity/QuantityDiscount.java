package assignment1.BusinessLayer.Entity;

public class QuantityDiscount {
    public final Item item;
    public int quantity;
    public float discount;

    public QuantityDiscount(Item item, int quantity, float discount){
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
    }
}
