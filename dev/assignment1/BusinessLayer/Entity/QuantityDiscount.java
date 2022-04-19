package assignment1.BusinessLayer.Entity;

public class QuantityDiscount {
    private final Item item;
    int quantity;
    float discount;

    public QuantityDiscount(Item item, int quantity, float discount){
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
    }
}
