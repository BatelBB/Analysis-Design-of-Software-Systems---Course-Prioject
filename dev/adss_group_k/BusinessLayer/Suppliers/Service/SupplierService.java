package adss_group_k.BusinessLayer.Suppliers.Service;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Order;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Controller.ItemController;
import adss_group_k.BusinessLayer.Suppliers.Controller.OrderController;
import adss_group_k.BusinessLayer.Suppliers.Controller.QuantityDiscountController;
import adss_group_k.BusinessLayer.Suppliers.Controller.SupplierController;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.OrderType;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.shared.dto.CreateSupplierDTO;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;

public class SupplierService implements ISupplierService {

    private final ItemController items;
    private final OrderController orders;
    private final QuantityDiscountController discounts;
    private PersistenceController dal;
    private SupplierController suppliers;

    public SupplierService(Connection connection) {
        dal = new PersistenceController(connection);
        items = new ItemController();
        discounts = new QuantityDiscountController(dal, items);
        orders = new OrderController(discounts);
        suppliers = new SupplierController(orders, items, dal);
    }

    @Override
    public ResponseT<Order> getOrder(int id) {
        return null;
    }

    @Override
    public ResponseT<Supplier> createSupplier(int ppn, int bankAccount, String name,
                                              boolean isDelivering, PaymentCondition paymentCondition,
                                              DayOfWeek regularSupplyingDays, String contactName,
                                              String contactPhone, String contactEmail) {
        return responseFor(() ->  {
            Supplier supplier = suppliers.create(new CreateSupplierDTO(
                    ppn, bankAccount, name, isDelivering,
                    paymentCondition, regularSupplyingDays, contactEmail,
                    contactName, contactPhone
            ));
            return ResponseT.success(supplier);
        });
    }

    @Override
    public Collection<Supplier> getSuppliers() {
        return suppliers.all();
    }

    @Override
    public ResponseT<Supplier> getSupplier(int ppn) throws BusinessLogicException {
        return responseFor(() -> suppliers.get(ppn));
    }

    @Override
    public Response deleteSupplier(int ppn) {
        return responseFor(() -> suppliers.delete(ppn));
    }

    public ResponseT<Item> createItem(int supplierPPN, int catalogNumber, int productID, float price) {
        try {
            Item item = items.create(
                    getSupplier(supplierPPN).getOrThrow(BusinessLogicException::new),
                    catalogNumber,
                    productID,
                    price
            );
            return ResponseT.success(item);
        } catch (BusinessLogicException e) {
            return ResponseT.error(e.getMessage());        }
    }

    @Override
    public Collection<Item> getItems() {
        return items.all();
    }

    @Override
    public ResponseT<Item> getItem(int ppn, int catalog) {
        return responseFor(() -> items.get(ppn, catalog));
    }

    @Override
    public Response deleteItem(int ppn, int catalogN) {
        return responseFor(() -> items.get(ppn, catalogN));
    }

    @Override
    public ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount) {
        return responseFor(() -> {
            Item item = items.get(supplierPPN, catalogN);
            return discounts.createDiscount(item, amount, discount);
        });
    }

    @Override
    public Response deleteDiscount(QuantityDiscount discount) {
        return responseForVoid(() -> discounts.delete(discount.id));
    }

    @Override
    public ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered, OrderType type) {
        return responseFor(() ->  {
            Supplier supplier = getSupplier(supplierPPN).getOrThrow(BusinessLogicException::new);
            return orders.create(supplier, type, ordered, delivered); }
        );
    }

    @Override
    public Collection<Order> getOrders() {
        return orders.all();
    }

    @Override
    public Response deleteOrder(int orderId) {
        return responseForVoid(() -> orders.delete(orderId));
    }

    @Override
    public QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException {
        return null;
    }

    @Override
    public Response orderItem(int orderId, int supplier, int catalogNumber, int amount) {
        return responseForVoid(() -> orders.orderItem(
                orders.get(orderId),
                items.get(supplier, catalogNumber),
                amount
        ));
    }

    @Override
    public Response setPrice(int supplier, int catalogNumber, float price) {
        return responseForVoid(() -> items.setPrice(supplier, catalogNumber, price));
    }

    @Override
    public Collection<QuantityDiscount> getDiscounts() {
        return discounts.getAllDiscounts();
    }

    @Override
    public Response setOrderOrdered(int orderID, LocalDate ordered) throws BusinessLogicException {
        return responseForVoid(() -> orders.setOrdered(orders.get(orderID), ordered));
    }

    @Override
    public Response setOrderProvided(int orderID, LocalDate provided) throws BusinessLogicException {
        return responseForVoid(() -> orders.setProvided(orders.get(orderID), provided));
    }

    @Override
    public Response setSupplierBankAccount(int supplierPPN, int bankAct) {
        return responseForVoid(() -> suppliers.setBankAccount(supplierPPN, bankAct));
    }

    @Override
    public Response setSupplierCompanyName(int supplierPPN, String newName) {
        return responseForVoid(() -> suppliers.setSupplierName(supplierPPN, newName));
    }

    @Override
    public Response setSupplierIsDelivering(int supplierPPN, boolean newValue) {
        return responseForVoid(() -> suppliers.setIsDelivering(supplierPPN, newValue));
    }

    @Override
    public Response setSupplierPaymentCondition(int supplierPPN, PaymentCondition payment) {

        return responseForVoid(() -> suppliers.setPaymentCondition(supplierPPN, payment));
    }

    @Override
    public Response setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek)
    {
        return responseForVoid(() -> suppliers.setRegularSupplyingDays(supplierPPN, dayOfWeek));
    }

    @Override
    public Response setSupplierContact(int supplierPPN, String name, String phoneNumber, String email) {

        return responseForVoid(() -> suppliers.setContact(supplierPPN, name, phoneNumber, email));
    }

    @Override
    public Response setItemName(int supplier, int catalogNumber, String name) {
        return responseForVoid(() -> items.setName(supplier, catalogNumber, name));
    }

    @Override
    public Response setItemCategory(int supplier, int catalogNumber, String category) {
        return responseForVoid(() -> items.setCategory(supplier, catalogNumber, category));
    }

    @Override
    public Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount) {
        return null;
    }

    @Override
    public Supplier findCheapestSupplierFor(int productID, int amount) {
        return discounts.findCheapestSupplierFor(productID, amount);
    }

    private <T> ResponseT responseFor(java.util.function.Supplier<T> operation) {
        try {
            return ResponseT.success(operation.get());
        } catch (Throwable e) {
            return ResponseT.error(e.getMessage());
        }
    }

    private Response responseForVoid(Runnable runnable) {
        try {
            runnable.run();
            return new Response(true, null);
        } catch (Throwable e) {
            return new Response(false, e.getMessage());
        }
    }
}
