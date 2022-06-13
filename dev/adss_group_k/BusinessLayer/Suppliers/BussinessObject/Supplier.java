package adss_group_k.BusinessLayer.Suppliers.BussinessObject;


import adss_group_k.dataLayer.dao.SupplierDAO;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.readonly.SupplierData;
import adss_group_k.shared.utils.Utils;
import java.time.DayOfWeek;

public class Supplier {

    private final SupplierDAO dao;
    private SupplierData source;

    public Supplier(SupplierData source, SupplierDAO dao){
        this.source = source;
        this.dao = dao;
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

    public void setPaymentCondition(PaymentCondition condition) {
        dao.updatePaymentCondition(getPpn(), condition);
    }

    public DayOfWeek getRegularSupplyingDays() {
        return source.getRegularSupplyingDays();
    }
    @Override
    public String toString() {
        DayOfWeek regularSupplyingDays = source.getRegularSupplyingDays();
        return Utils.table(
                2, 25, true,
                "    **** SUPPLIER **** ", "",
                "PPN", getPpn(),
                "Name", getName(),
                "Contact name", source.getContact().getName(),
                "Contact phone", source.getContact().getPhoneNumber(),
                "Contact email", source.getContact().getEmail(),
                "Bank #", source.getBankNumber(),
                "Delivering", source.isDelivering(),
                "Payment condition", source.getPaymentCondition(),
                "Supplies on", (regularSupplyingDays == null ? "N/A" : regularSupplyingDays)
        );
    }

    public void setIsDelivering(boolean newValue) {
        dao.updateIsDelivering(getPpn(), newValue);
    }

    public void setBankAccount(int bankAct) {
       dao.updateBankAccount(getPpn(), bankAct);
    }

    public void setName(String newName) {
        dao.updateName(getPpn(), newName);
    }
}
