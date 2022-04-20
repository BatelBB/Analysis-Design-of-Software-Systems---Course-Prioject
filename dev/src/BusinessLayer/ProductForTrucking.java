package BusinessLayer;

public class ProductForTrucking {
    public Products product;
    public int quantity;

    public ProductForTrucking(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String printProductForTrucking() {
        return ("Product: " + product + ", Quantity: " + quantity);
    }
}
