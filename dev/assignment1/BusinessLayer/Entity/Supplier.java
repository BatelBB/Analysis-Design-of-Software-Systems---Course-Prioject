package assignment1.BusinessLayer.Entity;

import java.time.DayOfWeek;
import java.util.List;

public class Supplier {
    int ppn;
    int bankNumber;
    String name;
    boolean isDelivering;
    PaymentCondition paymentCondition;
    List<DayOfWeek> regularSupplyingDays;
    Contact contact;

    public Supplier(int ppn, int bankNumber, String name, boolean isDelivering, PaymentCondition pm,
                    List<DayOfWeek> rsd, Contact contact){
        this.ppn = ppn;
        this.bankNumber = bankNumber;
        this.name = name;
        this.isDelivering = isDelivering;
        this.paymentCondition = pm;
        this.regularSupplyingDays = rsd;
        this.contact = contact;
    }
}
