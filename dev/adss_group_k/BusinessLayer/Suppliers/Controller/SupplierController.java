package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.Controller.BussinessObject.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Entity.MutableItem;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Entity.MutableContact;
import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;
import adss_group_k.BusinessLayer.Suppliers.Entity.MutableSupplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.ProductRecord;
import adss_group_k.dataLayer.records.readonly.SupplierData;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SupplierController {
    Map<Integer, Supplier> suppliers;
    private OrderController orders;
    private ItemController items;
    private PersistenceController dal;

    public SupplierController(OrderController orders, ItemController items, PersistenceController dal) {
        suppliers = new HashMap<>();
        this.orders = orders;
        this.items = items;
        this.dal = dal;
    }

    public Supplier create(int ppn, int bankAccount, String name, boolean isDelivering,
                           PaymentCondition paymentCondition,
                           DayOfWeek regularSupplyingDays, String contactName,
                           String contactPhone, String contactEmail)
            throws BusinessLogicException {
        validatePPNIsNew(ppn);
        SupplierData source = dal.suppliers.createSupplier(
                ppn, bankAccount, name, isDelivering,
                paymentCondition, regularSupplyingDays,
                contactEmail, contactName, contactPhone
        );
        Supplier object = new Supplier(source);
        suppliers.put(ppn, object);
        return object;
    }

    private void validatePPNIsNew(int ppn) throws BusinessLogicException {
        if (suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("A supplier with this ppn already exists: " + ppn);
        }
    }


    public Collection<Supplier> all() {
        return new ArrayList<>(suppliers.values());
    }

    public Supplier delete(int ppn) throws BusinessLogicException {
        if (!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("no such ppn:" + ppn);
        }
        Supplier s = suppliers.remove(ppn);
        orders.deleteAllFromSupplier(s);
        items.deleteAllFromSupplier(s);
        dal.suppliers.delete(ppn);
        return s;
    }

    public Supplier get(int ppn) throws BusinessLogicException {
        if (!dal.suppliers.exists(ppn)) {
            throw new BusinessLogicException("No suppliers with this ppn: " + ppn);
        }
        return suppliers.computeIfAbsent(ppn,
                k -> new Supplier(dal.suppliers.get(ppn).getOrThrow(RuntimeException::new)));

    }

    public void setPaymentCondition(int ppn, PaymentCondition payment) {
        dal.suppliers.updatePaymentCondition(ppn, payment);
    }
}
