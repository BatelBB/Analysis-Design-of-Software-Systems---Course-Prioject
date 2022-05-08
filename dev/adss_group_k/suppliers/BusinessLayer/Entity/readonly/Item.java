package adss_group_k.suppliers.BusinessLayer.Entity.readonly;

public interface Item {
    Supplier getSupplier();

    int getCatalogNumber();

    String getName();

    String getCategory();

    float getPrice();

    float priceForAmount(int amount);
}
