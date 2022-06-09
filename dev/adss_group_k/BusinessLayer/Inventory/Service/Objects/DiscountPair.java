package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import java.time.LocalDate;

public class DiscountPair {
    private LocalDate start_date;
    private LocalDate end_date;
    private double discount;

    public DiscountPair(adss_group_k.BusinessLayer.Inventory.DiscountPair dp) {
        start_date= dp.getStart_date();
        end_date= dp.getEnd_date();
        discount= dp.getDiscount();
    }
}
