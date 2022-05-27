package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.readonly.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.readonly.Supplier;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.MutableItem;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;

import java.util.*;
import java.util.stream.Collectors;

public class ItemController {
    Map<String, MutableItem> items;
    Map<Item, List<QuantityDiscount>> discounts;
    OrderController orderController;

    public ItemController(OrderController orderController) {
        items = new HashMap<>();
        discounts = new HashMap<>();
        this.orderController = orderController;
    }

    public MutableItem create(Supplier supplier, int catalogNumber, String name, String category, float price)
            throws BusinessLogicException {
        String key = tuple(supplier.getPpn(), catalogNumber);
        if(items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + supplier +" already has item with catalog number " + catalogNumber);
        }
        MutableItem item = new MutableItem(supplier, catalogNumber, name, category, price, this);
        items.put(key, item);
        return item;
    }

    private static String tuple(int ppn, int catalogNumber) {
        return ppn + ":" + catalogNumber;
    }

    public Collection<Item> all() {
        return new ArrayList<>(items.values());
    }

    public void delete(Item item) throws BusinessLogicException {
        int ppn = item.getSupplier().getPpn();
        int catalogNumber = item.getCatalogNumber();
        String key = tuple(ppn, catalogNumber);
        if(!items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + ppn +" has no item with catalog number " + catalogNumber);
        }
        discounts.remove(item);
        orderController.removeItemFromOrders(item);
        items.remove(key);
    }

    public void deleteDiscount(QuantityDiscount discount) {
        getDiscountList(discount.item).remove(discount);
        orderController.refreshPricesAndDiscounts(discount.item);
    }

    public MutableItem get(int ppn, int catalog) throws BusinessLogicException{
        String key = tuple(ppn, catalog);
        if(!items.containsKey(key)) {
            throw new BusinessLogicException("The item doesn't exist");
        }
        return items.get(key);
    }
    public float priceForAmount(MutableItem item, int amount) {
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
        orderController.refreshPricesAndDiscounts(item);
        return created;
    }

    public List<QuantityDiscount> getDiscountList(Item item) {
        return discounts.computeIfAbsent(item, __ -> new ArrayList<QuantityDiscount>());
    }

    public void deleteAllFromSupplier(Supplier s) {
        for(Map.Entry<String, MutableItem> entry: items.entrySet()) {
            items.remove(entry.getKey());
            discounts.remove(entry.getValue());
        }
    }

    public boolean supplierHasAnyItems(Supplier supplier) {
        return items.values().stream().anyMatch(i -> i.getSupplier() == supplier);
    }

    public Collection<QuantityDiscount> getAllDiscounts() {
        return discounts.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setItemPrice(Item item, float price) {
        ((MutableItem) item).setPrice(price);
    }

    public void setItemName(Item item, String name) {
        ((MutableItem) item).setName(name);
    }


    public void setItemCategory(Item item, String category) {
        ((MutableItem) item).setCategory(category);
    }


}
