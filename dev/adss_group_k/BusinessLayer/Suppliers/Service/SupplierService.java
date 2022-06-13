package adss_group_k.BusinessLayer.Suppliers.Service;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
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
import adss_group_k.serviceLayer.ServiceBase;
import adss_group_k.shared.dto.CreateSupplierDTO;

import static adss_group_k.serviceLayer.ServiceBase.*;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SupplierService extends ServiceBase implements ISupplierService {

    private final ItemController items;
    private final OrderController orders;
    private final QuantityDiscountController discounts;
    private final PersistenceController dal;
    private final SupplierController suppliers;

    public SupplierService(PersistenceController dal) {
        this.dal = dal;
        suppliers = new SupplierController(dal);
        items = new ItemController(dal, suppliers);
        discounts = new QuantityDiscountController(dal, items);
        orders = new OrderController(dal, discounts);

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
        return responseFor(() -> suppliers.create(new CreateSupplierDTO(
                ppn, bankAccount, name, isDelivering,
                paymentCondition, regularSupplyingDays, contactEmail,
                contactName, contactPhone
        )));
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
        return responseForVoid(() -> {
            Supplier s = suppliers.get(ppn);
            items.deleteAllFromSupplier(s);
            orders.deleteAllFromSupplier(s);
            suppliers.delete(ppn);
        });
    }

    public ResponseT<Item> createItem(int supplierPPN, int catalogNumber, int productID, float price) {
        return responseFor(() -> {
            ResponseT<Supplier> response = getSupplier(supplierPPN);
            if (!response.success) {
                throw new BusinessLogicException(response.error);
            }
            return items.create(
                    response.data,
                    catalogNumber,
                    productID,
                    price
            );
        });
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
    public ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount) {
        return responseFor(() -> {
            Item item = items.get(supplierPPN, catalogN);
            QuantityDiscount ret = discounts.createDiscount(item, amount, discount);
            orders.refreshPricesAndDiscounts(item);
            return ret;
        });
    }

    @Override
    public Response deleteDiscount(QuantityDiscount discount) {
        return responseForVoid(() -> {
            discounts.delete(discount.id);
            orders.refreshPricesAndDiscounts(discount.item);
        });
    }

    @Override
    public ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered, OrderType type) {
        return responseFor(() -> {
                    ResponseT<Supplier> supplier = getSupplier(supplierPPN);
                    if (!supplier.success) {
                        throw new BusinessLogicException(supplier.error);
                    }
                    return orders.create(supplier.data, type, ordered, delivered);
                }
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
        return responseForVoid(() -> {
            items.setPrice(supplier, catalogNumber, price);
            orders.refreshPricesAndDiscounts(items.get(supplier, catalogNumber));
        });
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
    public Response setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek) {
        return responseForVoid(() -> suppliers.setRegularSupplyingDays(supplierPPN, dayOfWeek));
    }

    @Override
    public Response setSupplierContact(int supplierPPN, String name, String phoneNumber, String email) {

        return responseForVoid(() -> suppliers.setContact(supplierPPN, name, phoneNumber, email));
    }

    @Override
    public Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount) {
        return null;
    }

    @Override
    public ResponseT<Integer> createOrderShortage(ResponseT<Boolean> r, int product_id, int min_qty) {
        if(r.success){
            Item item = items.getItemsFromProdID(product_id);
            Supplier supplier = items.checkBestSupplier(item);
            return responseFor(()->orders.createShortage(supplier, item, min_qty, OrderType.Shortages,
                    LocalDate.now(), LocalDate.now().plusDays(7)));
        }else{
            return responseFor(()-> {throw new BusinessLogicException("NO NEED FOR SHORTAGE ORDER!");});
        }
    }

    @Override
    public ResponseT<Integer> createOrderPeriodic(Map<Integer, Integer> productAmount, int weekDay) {
        Map<Item, Integer> itemsWithAmount = items.getItemsWithAmount(productAmount);
        Supplier supplier = items.checkBestSupplier((Item) itemsWithAmount.keySet().toArray()[0]);
        Order order = orders.create(supplier, OrderType.Periodical,
                LocalDate.now(), LocalDate.from(DayOfWeek.of(weekDay)));
        orders.orderItemFromMap(order, itemsWithAmount);

        return responseFor(() -> order.getId());
    }

    @Override
    public Response createOrderPeriodicVoid(Map<Integer, Integer> productAmount, int weekDay) {
        Map<Item, Integer> itemsWithAmount = items.getItemsWithAmount(productAmount);
        Supplier supplier = items.checkBestSupplier((Item) itemsWithAmount.keySet().toArray()[0]);
        Order order = orders.create(supplier, OrderType.Periodical,
                LocalDate.now(), LocalDate.from(DayOfWeek.of(weekDay)));

        return responseForVoid(() -> orders.orderItemFromMap(order, itemsWithAmount));
    }
}
