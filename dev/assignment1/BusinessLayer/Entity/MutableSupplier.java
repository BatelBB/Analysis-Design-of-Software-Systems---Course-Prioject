package assignment1.BusinessLayer.Entity;


import assignment1.BusinessLayer.Entity.readonly.Supplier;

import java.time.DayOfWeek;

public class MutableSupplier implements Supplier {
    int ppn;
    int bankNumber;
    String name;
    boolean isDelivering;
    PaymentCondition paymentCondition;
    DayOfWeek regularSupplyingDays;
    MutableContact contact;

    public MutableSupplier(int ppn, int bankNumber, String name, boolean isDelivering, PaymentCondition pm,
                           DayOfWeek rsd, MutableContact contact){
        this.ppn = ppn;
        this.bankNumber = bankNumber;
        this.name = name;
        this.isDelivering = isDelivering;
        this.paymentCondition = pm;
        this.regularSupplyingDays = rsd;
        this.contact = contact;
    }

    @Override
    public int getPpn() {
        return ppn;
    }

    @Override
    public int getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }

    @Override
    public PaymentCondition getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(PaymentCondition paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    @Override
    public DayOfWeek getRegularSupplyingDays() {
        return regularSupplyingDays;
    }

    public void setRegularSupplyingDays(DayOfWeek regularSupplyingDays) {
        this.regularSupplyingDays = regularSupplyingDays;
    }

    @Override
    public MutableContact getContact() {
        return contact;
    }

    public void setContact(MutableContact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return EntityUtils.table(
                2, 25, true,
                "    **** SUPPLIER **** ", "",
                "PPN", ppn,
                "Name", name,
                "Contact name", contact.name,
                "Contact phone", contact.phoneNumber,
                "Contact email", contact.email,
                "Bank #", bankNumber,
                "Delivering", isDelivering,
                "Payment condition", paymentCondition,
                "Supplies on", (regularSupplyingDays == null ? "N/A" : regularSupplyingDays)
        );
    }

}
