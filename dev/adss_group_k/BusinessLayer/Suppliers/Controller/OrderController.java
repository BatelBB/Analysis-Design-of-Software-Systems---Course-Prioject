package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;

import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Order;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.OrderType;
import adss_group_k.dataLayer.records.readonly.OrderData;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderController {
    private final QuantityDiscountController discounts;
    ArrayList<Order> orders;
    private PersistenceController dal;

    public OrderController(PersistenceController dal, QuantityDiscountController discounts) {
        this.dal = dal;
        orders = new ArrayList<>();
        this.discounts = discounts;
    }

    public Order get(int id) throws BusinessLogicException {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("No order exists with id "+ id));
    }

    public Order create(Supplier supplier, OrderType type, LocalDate ordered, LocalDate delivered) throws BusinessLogicException {
        if(ordered.isAfter(delivered)) {
            throw new BusinessLogicException("delivery date can't be before ordering date.");
        }
        OrderData data = dal.getOrders().createOrder(supplier.getPpn(), type, ordered, delivered);
        Order order = new Order(supplier, data, dal, discounts);
        orders.add(order);
        return order;
    }

    public void removeItemFromOrders(Item item) {
        orders.forEach(o -> o.removeItemIfExists(item));
    }

    public void refreshPricesAndDiscounts(Item item) {
        for(Order order: orders) {
            if(order.containsItem(item)) {
                order.refreshPrice();
            }
        }
    }

    public void orderItem(Order order, Item item, int amount) {
        order.orderItem(item, amount);
    }

    public void deleteAllFromSupplier(Supplier s) {
        orders.removeIf(order -> order.supplier == s);
    }

    public void delete(int orderId) throws BusinessLogicException {
        Order order = orders.stream()
                .filter(x -> x.getId() == orderId)
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("Tried to delete order, but it doesn't " +
                "seem to exist (maybe it was already deleted?)"));
        orders.remove(order);
        dal.getOrders().delete(orderId);
    }

    public Collection<Order> all() {
        return new ArrayList<>(orders);
    }

    public void setOrdered(Order order, LocalDate ordered) throws BusinessLogicException {
        order.updateOrdered(ordered);
    }

    public void setProvided(Order order, LocalDate provided) throws BusinessLogicException {
        order.updateProvided(provided);
    }


    public int updateAmount(Order order, Item item, int amount) { // needs to check if the item amount is >= the min amount
        order.orderItem(item, amount);
        refreshPricesAndDiscounts(item);
        return order.getId();
    }

    public int createShortage(Supplier supplier, Item item, int amountOfProd, OrderType type, LocalDate ordered, LocalDate delivered){
        if(ordered.isAfter(delivered)) {
            throw new BusinessLogicException("delivery date can't be before ordering date.");
        }

        OrderData data = dal.getOrders().createOrder(supplier.getPpn(), type, ordered, delivered);
        Order order = new Order(supplier, data, dal, discounts);

        if(type == OrderType.Shortages){
            order.orderItem(item, amountOfProd);
        }

        orders.add(order);
        return order.getId();
    }
}
