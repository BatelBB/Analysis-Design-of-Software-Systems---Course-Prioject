package adss_group_k.inventory.ServiceLayer.Objects;

import java.time.LocalDateTime;

public class DiscountPair {
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private double discount;

    public DiscountPair(adss_group_k.inventory.BusinessLayer.DiscountPair dp) {
        start_date= dp.getStart_date();
        end_date= dp.getEnd_date();
        discount= dp.getDiscount();
    }
}
