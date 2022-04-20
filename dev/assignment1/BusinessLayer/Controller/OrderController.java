package assignment1.BusinessLayer.Controller;

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

    public Order create(Supplier supplier, LocalDate ordered, LocalDate delivered) {
        return new Order(supplier, ordered,delivered);
    }

    public void removeItem(Item item) {
        orders.forEach(o -> o.removeItemIfExists(item));
    }

    public void refreshDiscounts(Item item) {
        for(Order order: orders) {
            if(order.containsItem(item)) {
                order.refreshPrice();
            }
        }
    }
}
