package assignment1.BusinessLayer.Service;

import assignment1.BusinessLayer.Entity.*;

import java.time.DayOfWeek;
import java.util.List;

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

    public ServiceResponseWithData<Supplier> createSupplier(int ppn, int bankAccount, String name,
                                                            boolean isDelivering, PaymentCondition paymentCondition,
                                                            List<DayOfWeek> regularSupplyingDays, Contact contact) {
        throw new RuntimeException("not yet implemented");
    }

    
    public List<Supplier> getSuppliers() {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponse deleteSupplier(Supplier supplier) {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponseWithData<Item> createItem(int supplierPPN, int catalogNumber,
                                                    String name, String category, float price) {
        throw new RuntimeException("not yet implemented");
    }

    
    public List<Item> getItems() {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponse deleteItem(Item item) {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponseWithData<QuantityDiscount> createDiscount(Item item, int amount, float discount) {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponse deleteDiscount(QuantityDiscount discount) {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponseWithData<Order> createOrder(Supplier supplier) {
        throw new RuntimeException("not yet implemented");
    }

    
    public List<Order> getOrders() {
        throw new RuntimeException("not yet implemented");
    }

    
    public ServiceResponse deleteOrder(Order order) {
        throw new RuntimeException("not yet implemented");
    }

    public ServiceResponse seedExample() {
        throw new RuntimeException("not yet implemented");
    }

}
