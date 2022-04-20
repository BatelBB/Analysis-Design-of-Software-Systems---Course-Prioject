package assignment1.BusinessLayer.Service;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Controller.ItemController;
import assignment1.BusinessLayer.Controller.OrderController;
import assignment1.BusinessLayer.Controller.SupplierController;
import assignment1.BusinessLayer.Entity.*;

import java.time.LocalDate;
import java.util.*;
import java.time.DayOfWeek;

class ServiceResponse {
    public final boolean success;
    public final String error;

    ServiceResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}

class ServiceResponseWithData<T> extends ServiceResponse {
    public final T data;

    ServiceResponseWithData(boolean success, String error, T data) {
        super(success, error);
        this.data = data;
    }

    static <T> ServiceResponseWithData<T> success(T data) {
        return new ServiceResponseWithData<>(true, null, data);
    }

    static <T> ServiceResponseWithData<T> error(String error) {
        return new ServiceResponseWithData<>(false, error, null);
    }
}

public class Service {

    private final SupplierController suppliers;
    private final ItemController items;
    private final OrderController orders;

    public Service(SupplierController suppliers, ItemController items, OrderController orders) {
        this.suppliers = suppliers;
        this.items = items;
        this.orders = orders;
    }

    public ServiceResponseWithData<Supplier> createSupplier(
            int ppn, int bankAccount, String name,
            boolean isDelivering, PaymentCondition paymentCondition,
            List<DayOfWeek> regularSupplyingDays, Contact contact) {
        return responseFor(() -> suppliers.create(
            ppn, bankAccount, name, isDelivering,
            paymentCondition, regularSupplyingDays, contact
        ));
    }

    public Collection<Supplier> getSuppliers() {
        return suppliers.all();
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
        throw new RuntimeException("not yet implemented");
    }

    public ServiceResponse seedExample() {
        return responseFor(() -> ExampleSeed.seedDatabase(this));
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
