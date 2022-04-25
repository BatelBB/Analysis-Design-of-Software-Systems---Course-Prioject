package PresentaionLayer;

import ServiceLayer.Response;

public class PrintFuture  extends DriverFuctionality{
    public PrintFuture() throws Exception {
    }

    @Override
    public void act() {
        Response response = service.printFutureTruckings();
        if(response.ErrorOccured()) System.out.println(response.getErrorMessage());
    }
}
