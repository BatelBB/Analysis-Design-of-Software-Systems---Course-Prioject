package adss_group_k.dataLayer.records.readonly;

import adss_group_k.dataLayer.records.PaymentCondition;

import java.time.DayOfWeek;

public interface SupplierData {
    int getPpn();

    int getBankNumber();

    String getName();

    boolean isDelivering();

    PaymentCondition getPaymentCondition();

    DayOfWeek getRegularSupplyingDays();

    ContactData getContact();
}
