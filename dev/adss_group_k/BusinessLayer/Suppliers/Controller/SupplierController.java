package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.Controller.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.SupplierRecord;
import adss_group_k.dataLayer.records.readonly.SupplierData;
import adss_group_k.shared.dto.CreateSupplierDTO;

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
        dal.getSuppliers().all().forEach(this::addFromExisting);
    }

    public Supplier create(CreateSupplierDTO dto)
            throws BusinessLogicException {
        validatePPNIsNew(dto.ppn);
        SupplierData source = dal.getSuppliers().createSupplier(dto);
        Supplier object = new Supplier(source);
        suppliers.put(dto.ppn, object);
        return object;
    }

    private void validatePPNIsNew(int ppn) throws BusinessLogicException {
        if (suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("A supplier with this ppn already exists: " + ppn);
        }
    }

    public Supplier delete(int ppn) throws BusinessLogicException {
        if (!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("no such ppn: " + ppn);
        }
        Supplier s = suppliers.remove(ppn);
        orders.deleteAllFromSupplier(s);
        items.deleteAllFromSupplier(s);
        dal.getSuppliers().delete(ppn);
        return s;
    }

    public Supplier get(int ppn) throws BusinessLogicException {
        if (!dal.getSuppliers().exists(ppn)) {
            throw new BusinessLogicException("No suppliers with this ppn: " + ppn);
        }
        return suppliers.computeIfAbsent(ppn,
                k -> new Supplier(dal.getSuppliers().get(ppn).getOrThrow(RuntimeException::new)));
    }

    public void setPaymentCondition(int ppn, PaymentCondition payment) {
        dal.getSuppliers().updatePaymentCondition(ppn, payment);
    }

    private void addFromExisting(SupplierRecord supplierRecord) {
        suppliers.put(supplierRecord.getPpn(), new Supplier(supplierRecord));
    }
}
