package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.DiscountPair;

import java.time.LocalDateTime;
import java.util.List;

public class ProductItem {
    public int getId() {
        return id;
    }

    private int id;
    private String store;
    private String location;
    private String supplier;
    private LocalDateTime expirationDate;
    private List<DiscountPair> man_discount;
    private List<DiscountPair> cus_discount;
    private boolean is_defect;
    private String defect_reporter;
    private String productName;


    public ProductItem(adss_group_k.BusinessLayer.Inventory.ProductItem PItem) {
        id=PItem.getId();
        store=PItem.getStore();
        productName=PItem.getProductName();
        location= PItem.getLocation();
        supplier= PItem.getSupplier();
        expirationDate= PItem.getExpirationDate();
        is_defect= PItem.is_defect();
        defect_reporter= PItem.getDefect_reporter();
        List<DiscountPair> BusinessCus_discount = PItem.getCus_discount();
        for (DiscountPair dp: BusinessCus_discount) {
            cus_discount.add(new DiscountPair(dp));
        }
        List<DiscountPair> BusinessMan_discount = PItem.getMan_discount();
        for (DiscountPair dp: BusinessMan_discount) {
            man_discount.add(new DiscountPair(dp));
        }
    }

    public String getProductName() {return productName;}

    public String getSupplier() {return supplier;}
}
