package adss_group_k.shared.dto;

import adss_group_k.dataLayer.records.PaymentCondition;

import java.time.DayOfWeek;

public class CreateSupplierDTO {
    public final int ppn;
    public final int bankNumber;
    public final String name;
    public final boolean isDelivering;
    public final PaymentCondition paymentCondition;
    public final DayOfWeek regularSupplyDays;
    public final String contactEmail;
    public final String contactName;
    public final String contactPhone;

    public CreateSupplierDTO(int ppn, int bankNumber, String name, boolean isDelivering, PaymentCondition paymentCondition, DayOfWeek regularSupplyDays, String contactEmail, String contactName, String contactPhone) {
        this.ppn = ppn;
        this.bankNumber = bankNumber;
        this.name = name;
        this.isDelivering = isDelivering;
        this.paymentCondition = paymentCondition;
        this.regularSupplyDays = regularSupplyDays;
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

}
