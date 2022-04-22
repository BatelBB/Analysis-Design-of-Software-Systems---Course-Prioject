package assignment1.BusinessLayer.Controller;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Entity.Item;
import assignment1.BusinessLayer.Entity.Order;
import assignment1.BusinessLayer.Entity.Supplier;

import java.time.LocalDate;
import java.util.ArrayList;

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
        order.orderItem(item, amount);
        order.refreshPrice();
    }

    public void deleteAllFromSupplier(Supplier s) {
        orders.removeIf(order -> order.supplier == s);
    }

    public void delete(Order order) {
        orders.remove(order);
    }
}
