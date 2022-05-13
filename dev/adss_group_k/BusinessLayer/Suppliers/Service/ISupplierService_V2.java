package adss_group_k.BusinessLayer.Suppliers.Service;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;
import adss_group_k.BusinessLayer.Suppliers.Entity.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Item;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Order;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Supplier;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;

/**
 * TEMPORARY NAME!
 */
public interface ISupplierService_V2 {
    ResponseT<Order> getOrder(int id);

    ResponseT<Supplier> createSupplier(
            int ppn, int bankAccount, String name,
            boolean isDelivering, PaymentCondition paymentCondition,
            DayOfWeek regularSupplyingDays,
            String contactName, String contactPhone, String contactEmail);

    Collection<Supplier> getSuppliers();

    Supplier getSupplier(int ppn) throws BusinessLogicException;

    Response deleteSupplier(int ppn);

    ResponseT<Item> createItem(int supplierPPN, int catalogNumber,
                               int productID,
                               String name, String category, float price);

    Collection<Item> getItems();

    ResponseT<Item> getItem(int ppn, int catalog);

    Response deleteItem(int ppn, int catalogN);

    ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount);

    Response deleteDiscount(QuantityDiscount discount);

    ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered,
                                 Order.OrderType type);

    Collection<Order> getOrders();

    Response deleteOrder(int orderId);

    Response seedExample();

    QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException;

    void orderItem(int orderId, int supplier, int catalogNumber, int amount);

    void setPrice(int supplier, int catalogNumber, float price);

    Collection<QuantityDiscount> getDiscounts();

    void setOrdered(int orderID, LocalDate ordered) throws BusinessLogicException;

    void setProvided(int orderID, LocalDate provided) throws BusinessLogicException;

    void setSupplierBankAccount(int supplierPPN, int bankAct);

    void setSupplierCompanyName(int supplierPPN, String newName);

    void setSupplierIsDelivering(int supplierPPN, boolean newValue);

    void setSupplierPaymentCondition(int supplierPPN, PaymentCondition payment);

    void setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek);

    void setSupplierContact(int supplierPPN, String name, String phoneNumber, String email);

    void setItemName(int supplier, int catalogNumber, String name);

    void setItemCategory(int supplier, int catalogNumber, String category);

    void updateOrderOrdered(int orderID, LocalDate ordered) throws BusinessLogicException;

    void updateOrderProvided(int orderID, LocalDate delivered) throws BusinessLogicException;

    Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount);

    Supplier findCheapestSupplierFor(int productID, int amount);
}
