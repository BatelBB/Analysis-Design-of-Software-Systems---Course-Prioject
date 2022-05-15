package groupk.logistics.DataLayer;

import groupk.logistics.business.Products;

public class ProductForTruckingDTO {
    public String product;
    public int quantity;

    public ProductForTruckingDTO(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }

    public String printProductForTrucking() {
        return ("Product: " + product + ", Quantity: " + quantity);
    }
}
