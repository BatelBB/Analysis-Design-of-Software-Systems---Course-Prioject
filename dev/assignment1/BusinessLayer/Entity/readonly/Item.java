package assignment1.BusinessLayer.Entity.readonly;

import assignment1.BusinessLayer.Entity.MutableSupplier;

public interface Item {
    Supplier getSupplier();

    int getCatalogNumber();

    String getName();

    String getCategory();

    float getPrice();

    float priceForAmount(int amount);
}
