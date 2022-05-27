package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Inventory.Categories.SubSubCategory;
import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.readonly.ItemData;

import java.util.*;
import java.util.stream.Collectors;

public class ItemController {
    Map<String, Item> items;
    OrderController orderController;
    PersistenceController dal;

    public ItemController(OrderController orderController) {
        items = new HashMap<>();
        this.orderController = orderController;
    }

    public Item create(Supplier supplier, int catalogNumber,
                       int productId,
                       SubSubCategory subSubCategory, float price)
            throws BusinessLogicException {
        String key = tuple(supplier.getPpn(), catalogNumber);
        if(items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + supplier +" already has item with catalog number " + catalogNumber);
        }
        ItemData source = dal.getItems()
                .create(supplier.getPpn(), catalogNumber, productId, price)
                .getOrThrow(BusinessLogicException::new);
        Item item = new Item(source, supplier, subSubCategory);
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
        dal.getQuantityDiscounts().delete(discount.id);
        orderController.refreshPricesAndDiscounts(discount.item);
    }

    public Item get(int ppn, int catalog) throws BusinessLogicException{
        String key = tuple(ppn, catalog);
        if(!items.containsKey(key)) {
            throw new BusinessLogicException("The item doesn't exist");
        }
        return items.get(key);
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
        orderController.refreshPricesAndDiscounts(item);
        return created;
    }

    public List<QuantityDiscount> getDiscountList(Item item) {
        return discounts.computeIfAbsent(item, __ -> new ArrayList<QuantityDiscount>());
    }

    public void deleteAllFromSupplier(Supplier s) {
        for(Map.Entry<String, Item> entry: items.entrySet()) {
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
        ((Item) item).setPrice(price);
    }

    public void setItemName(Item item, String name) {
        ((Item) item).setName(name);
    }


    public void setItemCategory(Item item, String category) {
        ((Item) item).setCategory(category);
    }


}
