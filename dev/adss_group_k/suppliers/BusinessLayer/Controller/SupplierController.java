package adss_group_k.suppliers.BusinessLayer.Controller;

import adss_group_k.suppliers.BusinessLayer.BusinessLogicException;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Supplier;
import adss_group_k.suppliers.BusinessLayer.Entity.MutableContact;
import adss_group_k.suppliers.BusinessLayer.Entity.PaymentCondition;
import adss_group_k.suppliers.BusinessLayer.Entity.MutableSupplier;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SupplierController {
    Map<Integer, MutableSupplier> suppliers;
    private OrderController orders;
    private ItemController items;

    public SupplierController(OrderController orders, ItemController items) {
        suppliers = new HashMap<>();
        this.orders = orders;
        this.items = items;
    }

    public MutableSupplier create(int ppn, int bankAccount, String name, boolean isDelivering,
                                  PaymentCondition paymentCondition,
                                  DayOfWeek regularSupplyingDays, MutableContact contact)
            throws BusinessLogicException {
        if (suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("A supplier with this ppn already exists: " + ppn);
        }
        MutableSupplier supplier = new MutableSupplier(
                ppn, bankAccount, name,
                isDelivering, paymentCondition,
                regularSupplyingDays, contact
        );
        suppliers.put(ppn, supplier);
        return supplier;
    }


    public Collection<Supplier> all() {
        return new ArrayList<>(suppliers.values());
    }

    public Supplier delete(int ppn) throws BusinessLogicException {
        if (!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("no such ppn:" + ppn);
        }
        MutableSupplier s = suppliers.remove(ppn);
        orders.deleteAllFromSupplier(s);
        items.deleteAllFromSupplier(s);
        return s;
    }

    public MutableSupplier get(int ppn) throws BusinessLogicException{
        if(!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("No suppliers with this ppn: " + ppn);
        }
        return suppliers.get(ppn);
    }

    public void setBankAccount(Supplier supplier, int bankAct) {
        ((MutableSupplier) supplier).setBankNumber(bankAct);
    }

    public void setCompanyName(Supplier supplier, String newName) {
        ((MutableSupplier) supplier).setName(newName);
    }


    public void setDelivering(Supplier supplier, boolean newValue) {
        ((MutableSupplier) supplier).setDelivering(newValue);
    }


    public void setPaymentCondition(Supplier supplier, PaymentCondition payment) {
        ((MutableSupplier) supplier).setPaymentCondition(payment);
    }


    public void setRegularSupplyingDays(Supplier supplier, DayOfWeek dayOfWeek) {
        ((MutableSupplier) supplier).setRegularSupplyingDays(dayOfWeek);
    }


    public void setContact(Supplier supplier, String name, String phoneNumber, String email) {
        MutableContact contact = (MutableContact) supplier.getContact();
        contact.setName(name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
    }


}
