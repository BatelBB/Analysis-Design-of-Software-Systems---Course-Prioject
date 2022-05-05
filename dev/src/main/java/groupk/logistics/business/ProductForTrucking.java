package BusinessLayer;

public class ProductForTrucking {
    public Products product;
    public int quantity;

    public ProductForTrucking(Products product, int quantity) {
        this.product = product;
        if(!(quantity>0))throw  new IllegalArgumentException("Quantity of a product must be posive");
        this.quantity = quantity;
    }

    public String printProductForTrucking() {
        return ("Product: " + product + ", Quantity: " + quantity);
    }
}
