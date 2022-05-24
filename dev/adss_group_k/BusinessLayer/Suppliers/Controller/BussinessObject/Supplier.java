package adss_group_k.BusinessLayer.Suppliers.Controller.BussinessObject;

import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.readonly.ContactData;
import adss_group_k.dataLayer.records.readonly.SupplierData;

import java.time.DayOfWeek;

public class Supplier {
    SupplierData source;

    public Supplier(SupplierData source) {
        this.source = source;
    }

    public int getPpn() {
        return source.getPpn();
    }

    public int getBankNumber() {
        return source.getBankNumber();
    }

    public String getName() {
        return source.getName();
    }

    public boolean isDelivering() {
        return source.isDelivering();
    }

    public PaymentCondition getPaymentCondition() {
        return source.getPaymentCondition();
    }

    public DayOfWeek getRegularSupplyingDays() {
        return source.getRegularSupplyingDays();
    }

    public ContactData getContact() {
        return source.getContact();
    }
}
