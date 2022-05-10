package groupk.logistics.business;

public class ProductForTrucking {
    public Products product;
    public int quantity;

    public ProductForTrucking(Products product, int quantity) {
        this.product = product;
        if(!(quantity>0))throw  new IllegalArgumentException("Quantity of a product must be posive");
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Products getProduct() {
        return product;
    }

    public String printProductForTrucking() {
        return ("Product: " + product + ", Quantity: " + quantity);
    }

    public String getProductString() {
        String product = "";
        if(this.product==Products.Water_7290019056966)product="water";
        if(this.product==Products.Milk_7290111607400)product="milk";
        if(this.product==Products.Eggs_4902505139314)product="eggs";
        return product;
    }
}
