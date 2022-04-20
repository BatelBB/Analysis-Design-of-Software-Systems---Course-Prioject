package ServiceLayer;

import BusinessLayer.*;

import java.util.List;

public class DriverService {

    private DriverController driverController;

    public DriverService()
    {
        driverController = DriverController.getInstance();
    }


    public Response showDriverHisFutureTruckings() {
        try {
            String truckings = driverController.printMyFutureTruckings();
            return new Response(truckings);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicense(DLicense dLicense) {
        try {
            driverController.addLicense(dLicense);
            return new Response("Added license");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicenses(List<DLicense> dLicenses) {
        try {
            driverController.addLicenses(dLicenses);
            return new Response("Added licenses");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeLicense(DLicense dLicense) {
        try {
            driverController.removeLicense(dLicense);
            return new Response("License removed");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
