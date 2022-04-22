package assignment1.BusinessLayer.Entity;

import assignment1.BusinessLayer.BusinessLogicException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Order {
    LocalDate ordered;
    LocalDate provided;
    float totalPrice;
    Map<Item, Integer> itemsAmounts;
    public final Supplier supplier;

    public Order(Supplier supplier, LocalDate ordered, LocalDate provided){
        this.supplier = supplier;
        this.ordered = ordered;
        this.provided = provided;
        this.itemsAmounts = new HashMap<>();
    }

    public void removeItemIfExists(Item item) {
        itemsAmounts.remove(item);
    }

    public void refreshPrice() {
        float price = 0;
        for(Map.Entry<Item, Integer> e: itemsAmounts.entrySet()) {
            price += e.getKey().priceForAmount(e.getValue());
        }
        totalPrice = price;
    }

    public boolean containsItem(Item item) {
        return itemsAmounts.containsKey(item);
    }

    public void orderItem(Item item, int amount) {
        if(amount == 0) {
            itemsAmounts.remove(item);
        } else {
            itemsAmounts.put(item, amount);
        }
        refreshPrice();
    }

    public void updateOrdered(LocalDate ordered) throws BusinessLogicException {
        if(ordered.isAfter(provided)) {
            throw new BusinessLogicException("ordered date can't be after provided date.");
        }
        this.ordered = ordered;
    }

    public void updateProvided(LocalDate provided) throws BusinessLogicException {
        if(ordered.isAfter(provided)) {
            throw new BusinessLogicException("provided date can't be before ordered date.");
        }
        this.provided = provided;
    }
}
