package assignment1.BusinessLayer.Controller;

import assignment1.BusinessLayer.BusinessLogicException;
import assignment1.BusinessLayer.Entity.Item;
import assignment1.BusinessLayer.Entity.QuantityDiscount;
import assignment1.BusinessLayer.Entity.Supplier;

import java.util.*;

public class ItemController {
    Map<String, Item> items;
    Map<Item, List<QuantityDiscount>> discounts;
    OrderController orderController;

    public ItemController(){
        items = new HashMap<>();
    }

    public Item create(Supplier supplier, int catalogNumber, String name, String category, float price)
            throws BusinessLogicException {
        String key = tuple(supplier.getPpn(), catalogNumber);
        if(items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + supplier +" already has item with catalog number " + catalogNumber);
        }
        Item item = new Item(supplier, catalogNumber, name, category, price, this);
        items.put(key, item);
        return item;
    }

    private static String tuple(int ppn, int catalogNumber) {
        return ppn + ":" + catalogNumber;
    }

    public Collection<Item> all() {
        return items.values();
    }

    public void delete(Item item) throws BusinessLogicException {
        int ppn = item.getSupplier().getPpn();
        int catalogNumber = item.getCatalogNumber();
        String key = tuple(ppn, catalogNumber);
        if(items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + ppn +" has no item with catalog number " + catalogNumber);
        }
        orderController.removeItem(item);
        items.remove(key);
    }

    public void deleteDiscount(QuantityDiscount discount) {
        getDiscountList(discount.item).remove(discount);
        orderController.refreshDiscounts(discount.item);
    }


    public float priceForAmount(Item item, int amount) {
        float discount = 0;
        List<QuantityDiscount> discounts = getDiscountList(item);
        for(QuantityDiscount qd: discounts) {
            if(qd.quantity > amount) { break; }
            discount = qd.discount;
        }
        return amount * item.getPrice() * (1 - discount);
    }

    public QuantityDiscount createDiscount(Item item, int amount, float discount) throws BusinessLogicException {
        int index = 0;
        List<QuantityDiscount> discounts = getDiscountList(item);
        QuantityDiscount previous = null;
        for(; index < discounts.size(); index++) {
            QuantityDiscount current = discounts.get(index);
            if(current.quantity == amount) {
                throw new BusinessLogicException("Item " + this + " already has discount for " + amount);
            }
            if(current.quantity > amount) {
                break;
            }
            previous = current;
        }
        if(previous != null && previous.discount > discount) {
            throw new BusinessLogicException(
                "can't add a discount of " + (100 * discount) + "% for amount over " + amount +
                " for item " + this + "! Already has a discount of " + (int)(previous.discount * 100)
                + "% for over " + previous.quantity
            );
        }
        QuantityDiscount created = new QuantityDiscount(item, amount, discount);
        discounts.add(index, created);
        orderController.refreshDiscounts(item);
        return created;
    }

    private List<QuantityDiscount> getDiscountList(Item item) {
        return discounts.computeIfAbsent(item, __ -> new ArrayList<QuantityDiscount>());
    }
}
