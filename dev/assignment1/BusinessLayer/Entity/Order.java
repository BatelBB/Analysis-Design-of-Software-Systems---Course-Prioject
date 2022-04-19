package assignment1.BusinessLayer.Entity;

import java.util.Date;

public class Order {
    Date ordered;
    Date provided;
    float totalPrice;

    public Order(Date ordered, Date provided, float totalPrice){
        this.ordered = ordered;
        this.provided = provided;
        this.totalPrice = totalPrice;
    }
}
