package assignment1.BusinessLayer.Entity.readonly;

import assignment1.BusinessLayer.Entity.MutableContact;
import assignment1.BusinessLayer.Entity.PaymentCondition;

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
