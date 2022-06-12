package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Inventory.Product;
import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.SupplierRecord;
import adss_group_k.dataLayer.records.readonly.SupplierData;
import adss_group_k.shared.dto.CreateSupplierDTO;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class SupplierController {
    Map<Integer, Supplier> suppliers;
    private PersistenceController dal;

    public SupplierController(PersistenceController dal) {
        suppliers = new HashMap<>();
        this.dal = dal;
        dal.getSuppliers().all().forEach(this::addFromExisting);
    }

    public Supplier create(CreateSupplierDTO dto)
            throws BusinessLogicException {
        validatePPNIsNew(dto.ppn);
        SupplierData source = dal.getSuppliers().createSupplier(dto);
        Supplier object = new Supplier(source, dal.getSuppliers());
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
      //  orders.deleteAllFromSupplier(s);
//        items.deleteAllFromSupplier(s);
        dal.getSuppliers().delete(ppn);
        return s;
    }

    public Supplier get(int ppn) throws BusinessLogicException {
        if (!dal.getSuppliers().exists(ppn)) {
            throw new BusinessLogicException("No suppliers with this ppn: " + ppn);
        }
        return suppliers.computeIfAbsent(ppn,
                k -> {
                    SupplierData data = dal.getSuppliers().get(ppn);

                    return new Supplier(data, dal.getSuppliers());
                });
    }

    public void setPaymentCondition(int ppn, PaymentCondition payment) {
        dal.getSuppliers().updatePaymentCondition(ppn, payment);
    }

    private void addFromExisting(SupplierRecord supplierRecord) {
        suppliers.put(supplierRecord.getPpn(), new Supplier(supplierRecord, dal.getSuppliers()));
    }

    public Collection<Supplier> all() {
        return suppliers.values();
    }

    public void setBankAccount(int supplierPPN, int bankAct) {
        suppliers.get(supplierPPN).setBankAccount(bankAct);
    }

    public void setIsDelivering(int supplierPPN, boolean newValue) {
        suppliers.get(supplierPPN).setIsDelivering(newValue);
    }

    public void setSupplierName(int supplierPPN, String newName) {
        suppliers.get(supplierPPN).setName(newName);
    }

    public void setRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek) {
    }

    public void setContact(int supplierPPN, String name, String phoneNumber, String email) {
    }


    public Supplier checkBestSupplier(Item item) {
        List<Product> productList = inventory.getProducts().data;
        Map<Integer, String> productMap = new HashMap<Integer, String>();
        for (Product prod : productList) {
            productMap.put(prod.getProduct_id(), prod.getName());
        }
        String nameProduct = "";
        List<Item> itemList = service.getItems().stream().collect(Collectors.toList());
        for (Item it : itemList) {
            if (productMap.containsKey(item.getProductId()))
                nameProduct = productMap.get(item.getProductId());
            if (productMap.containsKey(it.getProductId()) && productMap.get(it.getProductId()).equals(nameProduct)) {
                minPrice = (int) Math.min(it.getPrice(), minPrice);
            }
            if (it.getPrice() == minPrice)
                return it.getSupplier();
        }

        return item.getSupplier();
    }
}
