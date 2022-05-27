package adss_group_k.BusinessLayer.Suppliers.Controller;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.QuantityDiscount;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.QuantityDiscountRecord;

import java.util.*;
import java.util.stream.Collectors;

public class QuantityDiscountController {
    private PersistenceController dal;
    private Map<Item, ArrayList<QuantityDiscount>> map;

    public QuantityDiscountController(PersistenceController dal, ItemController items) {
        this.dal = dal;
        map = new HashMap<>();

        dal.getQuantityDiscounts().all().forEach(record -> {
            try {
                Item item = items.get(record.itemKey.ppn, record.itemKey.catalogNumber);
                getList(item).add(new QuantityDiscount(
                        record.id, item, record.quantity, record.discount
                ));
            } catch (BusinessLogicException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ArrayList<QuantityDiscount> getList(Item item) {
        return map.computeIfAbsent(item, key -> new ArrayList<>());
    }

    public void deleteAllFor(Item item) {
        dal.getQuantityDiscounts().deleteAllForItem(
                new ItemRecord.ItemKey(item.getSupplier().getPpn(), item.getCatalogNumber())
        );
    }

    public Collection<QuantityDiscount> discountsFor(Item item) {
        return new ArrayList<>(getList(item));
    }

    public void delete(int id) {
        dal.getQuantityDiscounts().delete(id);
        map.values().forEach(list -> list.removeIf(discount -> discount.id == id));
    }


    public float priceForAmount(Item item, int amount) {
        float discount = 0;
        List<QuantityDiscount> discounts = getList(item);
        for(QuantityDiscount qd: discounts) {
            if(qd.quantity > amount) { break; }
            discount = qd.discount;
        }
        return amount * item.getPrice() * (1 - discount);
    }

    public Collection<QuantityDiscount> getAllDiscounts() {
        return map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }


    public QuantityDiscount createDiscount(Item item, int amount, float discount) throws BusinessLogicException {
        List<QuantityDiscount> discounts = getList(item);
        QuantityDiscount previous = null;
        for(int i = 0; i < discounts.size(); i++) {
            QuantityDiscount current = discounts.get(i);
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
        QuantityDiscountRecord dbCreated = dal.getQuantityDiscounts().createQuantityDiscount(
                amount, discount,
                new ItemRecord.ItemKey(item.getSupplier().getPpn(), item.getCatalogNumber())
        ).getOrThrow(BusinessLogicException::new);
        QuantityDiscount created = new QuantityDiscount(dbCreated.id, item, amount, discount);
        discounts.add(created.id, created);
        return created;
    }
}
