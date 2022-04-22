package assignment1.BusinessLayer.Service;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Controller.ItemController;
import assignment1.BusinessLayer.Controller.OrderController;
import assignment1.BusinessLayer.Controller.SupplierController;
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
            DayOfWeek regularSupplyingDays, Contact contact) {
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
        return responseFor(() -> orders.create(supplier, ordered, delivered));
    }

    
    public Collection<Order> getOrders() {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponse deleteOrder(Order order) {
        return responseFor(() -> orders.delete(order));
    }

    public ServiceResponse seedExample() {
        return responseFor(() -> ExampleSeed.seedDatabase(this));
    }

    public QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException {
        List<QuantityDiscount> discounts = items.getDiscountList(getItem(ppn,catalog));
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
        item.setPrice(price);
        orders.refreshPricesAndDiscounts(item);
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
