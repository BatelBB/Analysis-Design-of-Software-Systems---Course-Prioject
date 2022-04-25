package PresentaionLayer;

import ServiceLayer.Response;

public class PrintHistory extends DriverFuctionality {
    public PrintHistory() throws Exception {
    }

    @Override
    public void act() throws Exception {
        Response response = service.printMyTruckingsHistory();
        if(response.ErrorOccured()) System.out.println(response.getErrorMessage());
    }
}
