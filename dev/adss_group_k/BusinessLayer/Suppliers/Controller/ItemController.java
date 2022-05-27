package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.readonly.ItemData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemController {
    Map<String, Item> items;
    PersistenceController dal;
    public ItemController() {
        items = new HashMap<>();
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
        items.remove(key);
    }

    public void deleteDiscount(QuantityDiscount discount) {
        dal.getQuantityDiscounts().delete(discount.id);
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
        }
    }

    public boolean supplierHasAnyItems(Supplier supplier) {
        return items.values().stream().anyMatch(i -> i.getSupplier() == supplier);
    }

    public void setPrice(int supplier, int catalogNumber, float price) {
        Item item = get(supplier, catalogNumber);
        dal.getItems().updatePrice(
                new ItemRecord.ItemKey(supplier, catalogNumber),
                price
        );
        orderController.refreshPricesAndDiscounts(item);
    }
}
