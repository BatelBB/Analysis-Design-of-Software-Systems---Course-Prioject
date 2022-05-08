package adss_group_k.suppliers.BusinessLayer.Entity.readonly;

import java.time.LocalDate;

public interface Order {
    boolean containsItem(Item item);

    float getTotalPrice();

    LocalDate getOrdered();

    LocalDate getProvided();
}
