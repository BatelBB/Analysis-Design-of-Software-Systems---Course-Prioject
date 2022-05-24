package adss_group_k.BusinessLayer.Suppliers.Entity.readonly;

import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;

import java.time.DayOfWeek;

public interface Supplier {
    int getPpn();

    int getBankNumber();

    String getName();

    boolean isDelivering();

    PaymentCondition getPaymentCondition();

    DayOfWeek getRegularSupplyingDays();

    Contact getContact();
}
