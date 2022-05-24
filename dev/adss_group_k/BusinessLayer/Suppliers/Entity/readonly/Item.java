package adss_group_k.BusinessLayer.Suppliers.Entity.readonly;

public interface Item {
    Supplier getSupplier();

    int getCatalogNumber();

    String getName();

    String getCategory();

    float getPrice();

    float priceForAmount(int amount);
}
