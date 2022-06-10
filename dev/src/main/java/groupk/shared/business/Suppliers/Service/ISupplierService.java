package groupk.shared.business.Suppliers.Service;

import groupk.shared.business.Suppliers.BusinessLogicException;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.shared.business.Suppliers.BussinessObject.Order;
import groupk.shared.business.Suppliers.BussinessObject.QuantityDiscount;

import groupk.shared.business.Suppliers.BussinessObject.Supplier;
import groupk.inventory_suppliers.dataLayer.dao.records.OrderType;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;
import groupk.shared.service.ServiceBase;

import static groupk.shared.service.ServiceBase.*;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;

public interface ISupplierService {
    ServiceBase.ResponseT<Order> getOrder(int id);

    ResponseT<Supplier> createSupplier(
            int ppn, int bankAccount, String name,
            boolean isDelivering, PaymentCondition paymentCondition,
            DayOfWeek regularSupplyingDays,
            String contactName, String contactPhone, String contactEmail);

    Collection<Supplier> getSuppliers();

    ResponseT<Supplier> getSupplier(int ppn) throws BusinessLogicException;

    Response deleteSupplier(int ppn);

    ResponseT<Item> createItem(int supplierPPN, int catalogNumber,
                               int productID, float price);

    Collection<Item> getItems();

    ResponseT<Item> getItem(int ppn, int catalog);

    ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount);

    Response deleteDiscount(QuantityDiscount discount);

    ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered,
                                 OrderType type);

    Collection<Order> getOrders();

    Response deleteOrder(int orderId);

    QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException;

    Response orderItem(int orderId, int supplier, int catalogNumber, int amount);

    Response setPrice(int supplier, int catalogNumber, float price);

    Collection<QuantityDiscount> getDiscounts();

    Response setOrderOrdered(int orderID, LocalDate ordered) throws BusinessLogicException;

    Response setOrderProvided(int orderID, LocalDate provided) throws BusinessLogicException;

    Response setSupplierBankAccount(int supplierPPN, int bankAct);

    Response setSupplierCompanyName(int supplierPPN, String newName);

    Response setSupplierIsDelivering(int supplierPPN, boolean newValue);

    Response setSupplierPaymentCondition(int supplierPPN, PaymentCondition payment);

    Response setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek);

    Response setSupplierContact(int supplierPPN, String name, String phoneNumber, String email);

    Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount);
}
