package PresentaionLayer;

import ServiceLayer.Service;

public abstract class DriverFuctionality {
    protected Service service = new Service();
    private String userChoice;
    public DriverFuctionality() throws Exception {
    }

    public abstract void act() throws Exception;


}
