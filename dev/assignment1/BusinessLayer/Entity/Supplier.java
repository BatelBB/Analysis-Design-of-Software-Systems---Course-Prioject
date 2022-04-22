package assignment1.BusinessLayer.Entity;


import java.time.DayOfWeek;

public class Supplier {
    int ppn;
    int bankNumber;
    String name;
    boolean isDelivering;
    PaymentCondition paymentCondition;
    DayOfWeek regularSupplyingDays;
    Contact contact;

    public Supplier(int ppn, int bankNumber, String name, boolean isDelivering, PaymentCondition pm,
                    DayOfWeek rsd, Contact contact){
        this.ppn = ppn;
        this.bankNumber = bankNumber;
        this.name = name;
        this.isDelivering = isDelivering;
        this.paymentCondition = pm;
        this.regularSupplyingDays = rsd;
        this.contact = contact;
    }

    public int getPpn() {
        return ppn;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public void setDelivering(boolean delivering) {
        isDelivering = delivering;
    }

    public PaymentCondition getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(PaymentCondition paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public DayOfWeek getRegularSupplyingDays() {
        return regularSupplyingDays;
    }

    public void setRegularSupplyingDays(DayOfWeek regularSupplyingDays) {
        this.regularSupplyingDays = regularSupplyingDays;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
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
