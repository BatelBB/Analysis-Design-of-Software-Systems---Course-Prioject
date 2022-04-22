package assignment1.BusinessLayer.Entity;

import assignment1.BusinessLayer.BusinessLogicException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order {
    LocalDate ordered;
    LocalDate provided;
    float totalPrice;
    Map<Item, Integer> itemsAmounts;
    public final Supplier supplier;
    public final int id;
    private static int instanceCounter = 0;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Order(Supplier supplier, LocalDate ordered, LocalDate provided){
        this.supplier = supplier;
        this.ordered = ordered;
        this.provided = provided;
        this.itemsAmounts = new HashMap<>();

        instanceCounter++;
        this.id = instanceCounter;
    }

    public void removeItemIfExists(Item item) {
        itemsAmounts.remove(item);
        refreshPrice();
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

    public float getTotalPrice() {
        return totalPrice;
    }
    @Override
    public String toString() {
        ArrayList<String> table = new ArrayList<>();
        table.add(" **** ORDER **** "); table.add(" "); table.add(" "); table.add(" ");

        table.add("Supplier name: "); table.add(supplier.name);
        table.add("Supplier PPN: "); table.add(supplier.getPpn() + "");

        table.add("Ordered: "); table.add(ordered.format(DATE_FORMAT));
        table.add("Provided: "); table.add(provided.format(DATE_FORMAT));

        table.add("----");
        table.add("----");
        table.add("----");
        table.add("----");

        for(Map.Entry<Item, Integer> entry: itemsAmounts.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();

            table.add(item.name);
            table.add(item.catalogNumber + "");
            table.add(amount + " units");
            table.add(String.format("$%.2f / ea", item.price));
        }

        table.add("----");
        table.add("----");
        table.add("----");
        table.add("----");

        table.add("Total price: ");
        table.add(String.format("$%.2f", totalPrice));
        table.add("");
        table.add("");

        return EntityUtils.table(
                4, 25, true,
                table.toArray()
        );
    }
}
