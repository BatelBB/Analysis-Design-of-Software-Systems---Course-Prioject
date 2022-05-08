package adss_group_k.inventory.BusinessLayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductItem {
    private int id;
    private String store;
    private String location;
    private String supplier;
    private LocalDateTime expirationDate;
    private List<DiscountPair> man_discount;
    private List<DiscountPair> cus_discount;
    private boolean is_defect;
    private boolean on_shelf;
    private String defect_reporter;

    //constructors
    public ProductItem(int id, String store, String location, String supplier, LocalDateTime expirationDate, List<DiscountPair> man_discount, List<DiscountPair> cus_discount, boolean is_defect) {
        this.id = id;
        this.store = store;
        this.location = location;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.man_discount = man_discount;
        this.cus_discount = cus_discount;
        this.is_defect = is_defect;
        defect_reporter = null;
    }

    public ProductItem(int id, String store, String location, String supplier, LocalDateTime expirationDate, boolean on_shelf) {
        this.id = id;
        this.store = store;
        this.location = location;
        this.supplier = supplier;
        this.expirationDate = expirationDate;
        this.man_discount = new ArrayList<>();
        this.cus_discount = new ArrayList<>();
        this.is_defect = false;
        this.on_shelf=on_shelf;
        defect_reporter = null;
    }

    //methods
    public void addManDiscount(DiscountPair pair) {
        man_discount.add(pair);
    }

    public void addCusDiscount(DiscountPair pair) {
        cus_discount.add(pair);
    }

    public double calculateDiscount() {
        double discount = 1;
        for (DiscountPair pair : man_discount)
            if (pair.getStart_date().isBefore(LocalDateTime.now()) && pair.getEnd_date().isAfter(LocalDateTime.now()))
                discount *= (1 - pair.getDiscount());
        return discount;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<DiscountPair> getMan_discount() {
        return man_discount;
    }

    public void setMan_discount(List<DiscountPair> man_discount) {
        this.man_discount = man_discount;
    }

    public List<DiscountPair> getCus_discount() {
        return cus_discount;
    }

    public void setCus_discount(List<DiscountPair> cus_discount) {
        this.cus_discount = cus_discount;
    }

    public boolean isIs_defect() {
        return is_defect;
    }

    public void setIs_defect(boolean is_defect) {
        this.is_defect = is_defect;
    }

    public String getDefect_reporter() {
        return defect_reporter;
    }

    public void setDefect_reporter(String defect_reporter) {
        this.defect_reporter = defect_reporter;
    }

    public boolean isOn_shelf() {
        return on_shelf;
    }

    public void setOn_shelf(boolean on_shelf) {
        this.on_shelf = on_shelf;
    }
}
