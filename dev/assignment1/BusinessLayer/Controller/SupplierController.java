package assignment1.BusinessLayer.Controller;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Entity.Contact;
import assignment1.BusinessLayer.Entity.PaymentCondition;
import assignment1.BusinessLayer.Entity.Supplier;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierController {
    Map<Integer, Supplier> suppliers;
    private OrderController orders;
    private ItemController items;

    public SupplierController(OrderController orders, ItemController items) {
        suppliers = new HashMap<>();
        this.orders = orders;
        this.items = items;
    }

    public Supplier create(int ppn, int bankAccount, String name, boolean isDelivering,
                           PaymentCondition paymentCondition,
                           DayOfWeek regularSupplyingDays, Contact contact)
            throws BusinessLogicException {
        if (suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("A supplier with this ppn already exists: " + ppn);
        }
        Supplier supplier = new Supplier(
                ppn, bankAccount, name,
                isDelivering, paymentCondition,
                regularSupplyingDays, contact
        );
        suppliers.put(ppn, supplier);
        return supplier;
    }


    public Collection<Supplier> all() {
        return suppliers.values();
    }

    public Supplier delete(int ppn) throws BusinessLogicException {
        if (!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("no such ppn:" + ppn);
        }
        Supplier s = suppliers.remove(ppn);
        orders.deleteAllFromSupplier(s);
        items.deleteAllFromSupplier(s);
        return s;
    }

    public Supplier get(int ppn) throws BusinessLogicException{
        if(!suppliers.containsKey(ppn)) {
            throw new BusinessLogicException("No suppliers with this ppn: " + ppn);
        }
        return suppliers.get(ppn);
    }
}
