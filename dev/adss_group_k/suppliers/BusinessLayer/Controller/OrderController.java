package adss_group_k.suppliers.BusinessLayer.Controller;

import adss_group_k.suppliers.BusinessLayer.BusinessLogicException;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Item;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Order;
import adss_group_k.suppliers.BusinessLayer.Entity.readonly.Supplier;
import adss_group_k.suppliers.BusinessLayer.Entity.MutableOrder;
import adss_group_k.suppliers.BusinessLayer.Entity.MutableSupplier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class OrderController {
    ArrayList<MutableOrder> orders;

    public OrderController(){
        orders = new ArrayList<>();
    }

    public MutableOrder get(int id) throws BusinessLogicException {
        return orders.stream()
                .filter(o -> o.id == id)
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException("No order exists with id "+ id));
    }

    public MutableOrder create(Supplier supplier, LocalDate ordered, LocalDate delivered) throws BusinessLogicException {
        if(ordered.isAfter(delivered)) {
            throw new BusinessLogicException("delivery date can't be before ordering date.");
        }
        MutableOrder order = new MutableOrder(supplier, ordered, delivered);
        orders.add(order);
        return order;
    }

    public void removeItemFromOrders(Item item) {
        orders.forEach(o -> o.removeItemIfExists(item));
    }

    public void refreshPricesAndDiscounts(Item item) {
        for(MutableOrder order: orders) {
            if(order.containsItem(item)) {
                order.refreshPrice();
            }
        }
    }

    public void orderItem(Order order, Item item, int amount) {
        MutableOrder mutable = (MutableOrder) order;
        mutable.orderItem(item, amount);
        mutable.refreshPrice();
    }

    public void deleteAllFromSupplier(MutableSupplier s) {
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
        ((MutableOrder) order).updateOrdered(ordered);
    }

    public void setProvided(Order order, LocalDate provided) throws BusinessLogicException {
        ((MutableOrder) order).updateProvided(provided);
    }


    public void updateAmount(Order order, Item item, int amount) {
        ((MutableOrder) order).orderItem(item, amount);
        refreshPricesAndDiscounts(item);
    }
}
