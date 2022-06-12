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
            items.remove(entry.getKey());
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

    public Supplier checkBestSupplier(Item item) {
        List<Product> productList = Service.getProducts().data;
        Map<Integer, String> productMap = new HashMap<Integer, String>();
        for (Product prod : productList) {
            productMap.put(prod.getProduct_id(), prod.getName());
        }
        String nameProduct = "";
        int minPrice = 1000000;
        List<Item> itemList = items.values().stream().collect(Collectors.toList());
        for (Item it : itemList) {
            if (productMap.containsKey(item.getProductId()))
                nameProduct = productMap.get(item.getProductId());
            if (productMap.containsKey(it.getProductId()) && productMap.get(it.getProductId()).equals(nameProduct)) {
                minPrice = (int) Math.min(it.getPrice(), minPrice);
            }
            if (it.getPrice() == minPrice) {
                UserOutput.println("There is a better supplier that supplying this item: "+
                                        "with the better price: " + minPrice + " instead of the price: " +
                                        item.getPrice());
                return it.getSupplier();
            }
        }

        return item.getSupplier();
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
