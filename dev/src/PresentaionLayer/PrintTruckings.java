package PresentaionLayer;

import ServiceLayer.Response;

public class PrintTruckings extends DriverFuctionality {
    public PrintTruckings() throws Exception {
    }

    @Override
    public void act() throws Exception {
        Response response = service.printMyTruckings();
        if(response.ErrorOccured()) System.out.println(response.getErrorMessage());
    }
}
