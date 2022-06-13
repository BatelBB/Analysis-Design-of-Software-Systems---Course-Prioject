package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Inventory.Controllers.ProductController;
import adss_group_k.BusinessLayer.Inventory.Product;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Supplier;
import adss_group_k.PresentationLayer.Suppliers.UserOutput;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.readonly.ItemData;
import adss_group_k.shared.utils.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class ItemController {
    private final SupplierController suppliers;
    Map<String, Item> items;
    PersistenceController dal;

    public ItemController(PersistenceController dal, SupplierController suppliers) {
        this.dal = dal;
        this.suppliers = suppliers;
        items = new HashMap<>();

        this.dal.getItems().all().forEach(this::createFromExisting);

    }

    private void createFromExisting(ItemRecord itemRecord) {
        items.put(
            tuple(itemRecord.getSupplierPPN(), itemRecord.getCatalogNumber()),
            new Item(itemRecord, suppliers.get(itemRecord.getSupplierPPN()), this)
        );
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
                ;
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
            String key = entry.getKey();
            Item item = entry.getValue();
            items.remove(key);
            dal.getItems().delete(new ItemRecord.ItemKey(item.getSupplier().getPpn(), item.getCatalogNumber()));
            UserOutput.getInstance().println("Item with catalog number: " +entry.getValue().getCatalogNumber() +
                    " is deleted.");
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
    }

    public Item getItemsFromProdID(int prodId){
        Item it = null;
        for(Map.Entry<String, Item> entry: items.entrySet()) {
            if(entry.getValue().getProductId() == prodId)
                it = entry.getValue();
        }
        return it;
    }

    public Tuple<Supplier, Item> checkBestSupplier(int given_prod_id) {
        /**
         * take the given_prod_id and take all the items that have this prod_id, compare the price of these items and
         * take the minimal one.
         * Return map of <Supplier, Item> that the supplier is the one with the cheapest item.
         */

        List<Item> itemList = items.values().stream().collect(Collectors.toList());
        List<Item> items_with_given_prod_id = new ArrayList<>();
        for (Item it : itemList) {
            if(it.getProductId()==given_prod_id)
                items_with_given_prod_id.add(it);
        }
        int minPrice = 1000000;
        for(Item it: items_with_given_prod_id){
            minPrice = (int) Math.min(it.getPrice(), minPrice);
        }
        Item retItem = null;
        for(Item it: items_with_given_prod_id){
            if(it.getPrice() == minPrice)
                retItem = it;
        }
        return new Tuple<>(retItem.getSupplier(), retItem);
    }

    public Map<Item, Integer> getItemsWithAmount(Map<Integer, Integer> productAmount) {
        Map<Item, Integer> itemProdAmountMap = new HashMap<>();
        int id = 0;
        Item item;
        for(int i =0; i< productAmount.size(); i++){
            id = (int) productAmount.keySet().toArray()[i];
            item = (Item) items.values().toArray()[i];
            itemProdAmountMap.put(item, productAmount.get(id));
        }
        return itemProdAmountMap;
    }
}
