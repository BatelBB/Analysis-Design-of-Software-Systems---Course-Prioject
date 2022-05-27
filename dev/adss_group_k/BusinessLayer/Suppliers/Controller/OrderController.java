package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;

import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Order;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderController {
    ArrayList<Order> orders;

    public OrderController(){
        orders = new ArrayList<>();
    }

    public Order get(int id) throws BusinessLogicException {
        return orders.stream()
                .filter(o -> o.id == id)
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("No order exists with id "+ id));
    }

    public Order create(Supplier supplier, LocalDate ordered, LocalDate delivered) throws BusinessLogicException {
        if(ordered.isAfter(delivered)) {
            throw new BusinessLogicException("delivery date can't be before ordering date.");
        }
        Order order = new Order(supplier, ordered, delivered);
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
        Order mutable = (Order) order;
        mutable.orderItem(item, amount);
        mutable.refreshPrice();
    }

    public void deleteAllFromSupplier(Supplier s) {
        orders.removeIf(order -> order.supplier == s);
    }

    public void delete(Order order) throws BusinessLogicException {
        if(!orders.contains(order)) {
            throw new BusinessLogicException("Tried to delete order, but it doesn't " +
                    "seem to exist (maybe it was already deleted?)");
        }
        orders.remove(order);
    }

    public Collection<Order> all() {
        return new ArrayList<>(orders);
    }

    public void setOrdered(Order order, LocalDate ordered) throws BusinessLogicException {
        ((Order) order).updateOrdered(ordered);
    }

    public void setProvided(Order order, LocalDate provided) throws BusinessLogicException {
        ((Order) order).updateProvided(provided);
    }


    public int updateAmount(Order order, Item item, int amount) { // needs to check if the item amount is >= the min amount
        ((Order) order).orderItem(item, amount);
        refreshPricesAndDiscounts(item);
        return ((Order) order).id;
    }

    public int createDeficienciesOrder(Map<String, Integer> proAmount) { //returns id of order
        throw new NotImplementedException();
    }


    public int addProductToOrder(int orderId, String proName, int proAmount, int minAmount) {
        throw new NotImplementedException();
    }
}
