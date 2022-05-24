package adss_group_k.dataLayer.records.readonly;

import adss_group_k.dataLayer.records.OrderType;

import java.time.LocalDate;

public interface OrderData {
    int getId();

    int getSupplierPPN();

    OrderType getOrderType();

    float getPrice();

    LocalDate getOrdered();

    LocalDate getProvided();
}
