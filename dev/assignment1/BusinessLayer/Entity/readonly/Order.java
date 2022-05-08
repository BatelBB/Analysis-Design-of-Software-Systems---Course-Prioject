package assignment1.BusinessLayer.Entity.readonly;

import java.time.LocalDate;

public interface Order {
    boolean containsItem(Item item);

    float getTotalPrice();

    LocalDate getOrdered();

    LocalDate getProvided();
}
