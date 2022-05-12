package adss_group_k.BusinessLayer.Inventory;

import java.time.LocalDateTime;

public class DiscountPair {
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private double discount;

    public DiscountPair(LocalDateTime start_date, LocalDateTime end_date, double discount) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.discount = discount;
    }

    //getters and setters
    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
