package adss_group_k.suppliers.BusinessLayer.Service;

import adss_group_k.suppliers.BusinessLayer.BusinessLogicException;
import adss_group_k.suppliers.BusinessLayer.Controller.ItemController;
import adss_group_k.suppliers.BusinessLayer.Controller.OrderController;
import adss_group_k.suppliers.BusinessLayer.Controller.SupplierController;
import adss_group_k.suppliers.BusinessLayer.Entity.MutableContact;
import adss_group_k.suppliers.BusinessLayer.Entity.PaymentCondition;
import adss_group_k.suppliers.BusinessLayer.Entity.QuantityDiscount;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Item;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Order;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Supplier;
import assignment1.BusinessLayer.Entity.*;

import java.time.LocalDate;
import java.util.*;
import java.time.DayOfWeek;

public class Service {
    private final SupplierController suppliers;
    private final ItemController items;
    private final OrderController orders;

    public Service() {
        this.orders = new OrderController();
        this.items = new ItemController(this.orders);
        this.suppliers = new SupplierController(this.orders, this.items);
    }

    public ServiceResponseWithData<Order> getOrder(int id) {
        return responseFor(() -> orders.get(id));
    }

    public ServiceResponseWithData<Supplier> createSupplier(
            int ppn, int bankAccount, String name,
            boolean isDelivering, PaymentCondition paymentCondition,
            DayOfWeek regularSupplyingDays, MutableContact contact) {
        return responseFor(() -> suppliers.create(
            ppn, bankAccount, name, isDelivering,
            paymentCondition, regularSupplyingDays, contact
        ));
    }

    public Collection<Supplier> getSuppliers() {
        return suppliers.all();
    }

    public Supplier getSupplier(int ppn) throws BusinessLogicException {
        return suppliers.get(ppn);
    }
    
    public ServiceResponse deleteSupplier(int ppn) {
        return responseFor(() -> suppliers.delete(ppn));
    }

    public ServiceResponseWithData<Item> createItem(int supplierPPN, int catalogNumber,
                                                    String name, String category, float price) {
        return responseFor(() -> items.create(
                suppliers.get(supplierPPN),
                catalogNumber, name,
                category, price
        ));
    }

    
    public Collection<Item> getItems() {
        return items.all();
    }

    public ServiceResponseWithData<Item> getItem(int ppn, int catalog) {
        return responseFor(() -> items.get(ppn, catalog));
    }

    
    public ServiceResponse deleteItem(Item item) {
        return responseFor(() -> items.delete(item));
    }

    
    public ServiceResponseWithData<QuantityDiscount> createDiscount(Item item, int amount, float discount) {
        return responseFor(() -> items.createDiscount(item, amount, discount));
    }

    
    public ServiceResponse deleteDiscount(QuantityDiscount discount) {
       return responseFor(() -> items.deleteDiscount(discount));
    }

    
    public ServiceResponseWithData<Order> createOrder(Supplier supplier, LocalDate ordered, LocalDate delivered) {
        if(!items.supplierHasAnyItems(supplier)) {
            return ServiceResponseWithData.error("This supplier doesn't have any items currently");
        }
        return responseFor(() -> orders.create(supplier, ordered, delivered));
    }

    
    public Collection<Order> getOrders() {
        return orders.all();
    }

    
    public ServiceResponse deleteOrder(Order order) {
        return responseFor(() -> orders.delete(order));
    }

    public ServiceResponse seedExample() {
        return responseFor(() -> ExampleSeed.seedDatabase(this));
    }

    public QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException {
        List<QuantityDiscount> discounts = items.getDiscountList(items.get(ppn,catalog));
        for(int i = 0; i < discounts.size(); i++) {
            QuantityDiscount current = discounts.get(i);
            if(current.quantity == amount) {
                return current;
            }
    }
        throw new BusinessLogicException("There is no discount with amount "+amount);
    }

    public void orderItem(Order order, Item item, int amount) {
        orders.orderItem(order, item, amount);
    }

    public void setPrice(Item item, float price) {
        items.setItemPrice(item, price);
        orders.refreshPricesAndDiscounts(item);
    }

    public Collection<QuantityDiscount> getDiscounts() {
        return items.getAllDiscounts();
    }
    public String toStringSupplier(){
        String supp = "";
        for(Supplier sup : getSuppliers())
            supp += sup.toString();
        return supp;
    }
    public String toStringItems(){
        String item = "";
        for(Item it : getItems())
            item += it.toString();
        return item;
    }
    public String toStringOrders(){
        String order = "";
        for(Order or : getOrders())
            order += or.toString();
        return order;
    }
    public String toStringQuantity(){
        String disc = "";
        for(Item it : getItems())
            for(QuantityDiscount qc : items.getDiscountList(it))
                disc += qc.toString();
        return disc;
    }

    public void setOrdered(Order order, LocalDate ordered) throws BusinessLogicException {
        orders.setOrdered(order, ordered);
    }

    public void setProvided(Order order, LocalDate provided) throws BusinessLogicException {
        orders.setProvided(order, provided);
    }

    public void setSupplierBankAccount(Supplier supplier, int bankAct) {
        suppliers.setBankAccount(supplier, bankAct);
    }

    public void setSupplierCompanyName(Supplier supplier, String newName) {
        suppliers.setCompanyName(supplier, newName);
    }

    public void setSupplierIsDelivering(Supplier supplier, boolean newValue) {
        suppliers.setDelivering(supplier, newValue);
    }

    public void setSupplierPaymentCondition(Supplier supplier, PaymentCondition payment) {
        suppliers.setPaymentCondition(supplier, payment);
    }

    public void setSupplierRegularSupplyingDays(Supplier supplier, DayOfWeek dayOfWeek) {
        suppliers.setRegularSupplyingDays(supplier, dayOfWeek);
    }

    public void setSupplierContact(Supplier supplier, String name, String phoneNumber, String email) {
        suppliers.setContact(supplier, name, phoneNumber, email);
    }

    public void setItemName(Item item, String name) {
        items.setItemName(item, name);
    }

    public void setItemCategory(Item item, String category) {
        items.setItemCategory(item, category);
    }

    public void updateOrderOrdered(Order order, LocalDate ordered) throws BusinessLogicException {
        orders.setOrdered(order, ordered);
    }

    public void updateOrderProvided(Order order, LocalDate delivered) throws BusinessLogicException {
        orders.setProvided(order, delivered);
    }

    public void updateOrderAmount(Order order, Item item, int amount) {
        orders.updateAmount(order, item, amount);
    }


    private interface BusinessLayerOperation<T> {
        T run() throws BusinessLogicException;
    }

    private interface VoidBusinessLayerOperation {
        void run() throws BusinessLogicException;
    }

    private <T> ServiceResponseWithData<T> responseFor(BusinessLayerOperation<T> operation) {
        try {
            T result = operation.run();
            return ServiceResponseWithData.success(result);
        } catch (BusinessLogicException e) {
            return ServiceResponseWithData.error(e.getMessage());
        }
    }

    private ServiceResponse responseFor(VoidBusinessLayerOperation operation) {
        try {
            operation.run();
            return new ServiceResponse(true, null);
        } catch (BusinessLogicException e) {
            return new ServiceResponse(false, e.getMessage());
        }
    }
}
