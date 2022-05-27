package adss_group_k.BusinessLayer.Suppliers.Service;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.shared.response.Response;
import adss_group_k.shared.response.ResponseT;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;

public class SupplierService implements ISupplierService {

    private PersistenceController dal;

    public SupplierService(Connection connection) {
        dal = new PersistenceController(connection);
    }

    @Override
    public ResponseT<Order> getOrder(int id) {
        return null;
    }

    @Override
    public ResponseT<Supplier> createSupplier(int ppn, int bankAccount, String name, boolean isDelivering, PaymentCondition paymentCondition, DayOfWeek regularSupplyingDays, String contactName, String contactPhone, String contactEmail) {
        return null;
    }

    @Override
    public Collection<Supplier> getSuppliers() {
        return null;
    }

    @Override
    public Supplier getSupplier(int ppn) throws BusinessLogicException {
        return null;
    }

    @Override
    public Response deleteSupplier(int ppn) {
        return null;
    }

    @Override
    public ResponseT<Item> createItem(int supplierPPN, int catalogNumber, int productID, String name, String category, float price) {
        return null;
    }

    @Override
    public Collection<Item> getItems() {
        return null;
    }

    @Override
    public ResponseT<Item> getItem(int ppn, int catalog) {
        return null;
    }

    @Override
    public Response deleteItem(int ppn, int catalogN) {
        return null;
    }

    @Override
    public ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount) {
        return null;
    }

    @Override
    public Response deleteDiscount(QuantityDiscount discount) {
        return null;
    }

    @Override
    public ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered, Order.OrderType type) {
        return null;
    }

    @Override
    public Collection<Order> getOrders() {
        return null;
    }

    @Override
    public Response deleteOrder(int orderId) {
        return null;
    }

    @Override
    public Response seedExample() {
        return null;
    }

    @Override
    public QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException {
        return null;
    }

    @Override
    public void orderItem(int orderId, int supplier, int catalogNumber, int amount) {

    }

    @Override
    public void setPrice(int supplier, int catalogNumber, float price) {

    }

    @Override
    public Collection<QuantityDiscount> getDiscounts() {
        return null;
    }

    @Override
    public void setOrdered(int orderID, LocalDate ordered) throws BusinessLogicException {

    }

    @Override
    public void setProvided(int orderID, LocalDate provided) throws BusinessLogicException {

    }

    @Override
    public void setSupplierBankAccount(int supplierPPN, int bankAct) {

    }

    @Override
    public void setSupplierCompanyName(int supplierPPN, String newName) {

    }

    @Override
    public void setSupplierIsDelivering(int supplierPPN, boolean newValue) {

    }

    @Override
    public void setSupplierPaymentCondition(int supplierPPN, PaymentCondition payment) {

    }

    @Override
    public void setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek) {

    }

    @Override
    public void setSupplierContact(int supplierPPN, String name, String phoneNumber, String email) {

    }

    @Override
    public void setItemName(int supplier, int catalogNumber, String name) {

    }

    @Override
    public void setItemCategory(int supplier, int catalogNumber, String category) {

    }

    @Override
    public void updateOrderOrdered(int orderID, LocalDate ordered) throws BusinessLogicException {

    }

    @Override
    public void updateOrderProvided(int orderID, LocalDate delivered) throws BusinessLogicException {

    }

    @Override
    public Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount) {
        return null;
    }

    @Override
    public Supplier findCheapestSupplierFor(int productID, int amount) {
        return null;
    }
}
