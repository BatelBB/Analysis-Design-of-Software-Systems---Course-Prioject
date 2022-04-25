package PresentaionLayer;

import ServiceLayer.Response;

public class AddLicense  extends DriverFuctionality{
    private String license;
    public AddLicense(String liscense) throws Exception {
        super();
        license = liscense;
    }

    @Override
    public void act() {
        Response response = service.addLicenseToDriver(license);
        if(response.ErrorOccured()) System.out.println(response.getErrorMessage());
        else System.out.println("Added successfully");
    }
}
