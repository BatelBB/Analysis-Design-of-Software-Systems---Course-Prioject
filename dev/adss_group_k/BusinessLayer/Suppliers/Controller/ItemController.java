package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Inventory.Categories.SubSubCategory;
import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.QuantityDiscountRecord;
import adss_group_k.dataLayer.records.readonly.ItemData;

import java.util.*;
import java.util.stream.Collectors;

public class ItemController {
    Map<String, Item> items;
    OrderController orderController;
    PersistenceController dal;
    QuantityDiscountController quantityDiscounts;

    public ItemController(OrderController orderController, QuantityDiscountController quantityDiscounts) {
        items = new HashMap<>();
        this.orderController = orderController;
        this.quantityDiscounts = quantityDiscounts;
    }

    public Item create(Supplier supplier, int catalogNumber,
                       int productId,
                       float price)
            throws BusinessLogicException {
        String key = tuple(supplier.getPpn(), catalogNumber);
        if(items.containsKey(key)) {
            throw new BusinessLogicException("Supplier " + supplier +" already has item with catalog number " + catalogNumber);
        }
        ItemData source = dal.getItems()
                .create(supplier.getPpn(), catalogNumber, productId, price)
                .getOrThrow(BusinessLogicException::new);
        Item item = new Item(source, supplier, this);
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
        quantityDiscounts.deleteAllFor(item);
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


    public void deleteAllFromSupplier(Supplier s) {
        for(Map.Entry<String, Item> entry: items.entrySet()) {
            items.remove(entry.getKey());
            quantityDiscounts.deleteAllFor(entry.getValue());
        }
    }

    public boolean supplierHasAnyItems(Supplier supplier) {
        return items.values().stream().anyMatch(i -> i.getSupplier() == supplier);
    }

    public void setItemPrice(Item item, float price) {
        ((Item) item).setPrice(price);
    }

}
