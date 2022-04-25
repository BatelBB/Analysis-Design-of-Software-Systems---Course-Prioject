package PresentaionLayer;

import ServiceLayer.Response;
import ServiceLayer.Service;

public class Logout extends UserFunctionality {
    private Service service = new Service();

    public Logout() throws Exception {
    }

    public void act()
    {
        Response response  = service.Logout();
        if(response.ErrorOccured()) System.out.println(response.getErrorMessage());
        else  System.out.println("Logout completed");

    }
}
