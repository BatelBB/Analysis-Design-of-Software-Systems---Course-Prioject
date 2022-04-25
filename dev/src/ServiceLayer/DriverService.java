package ServiceLayer;

import BusinessLayer.*;

import java.util.List;

public class DriverService {

    private DriverController driverController;

    public DriverService() throws Exception {
        driverController = DriverController.getInstance();
    }

    public Response setWeightForTrucking(int truckingId, int weight) throws Exception {
        try {
            driverController.setWeightForTrucking(truckingId,weight);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printMyTruckings() throws Exception {
        try {
            return new Response(driverController.printMyTruckings());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printMyTruckingsHistory() throws Exception {
        try {
            return new Response(driverController.printMyTruckingsHistory());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response showDriverHisFutureTruckings() {
        try {
            String truckings = driverController.printMyFutureTruckings();
            return new Response(truckings);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicense(String dLicense) {
        try {
            driverController.addLicense(dLicense);
            return new Response(true);
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicenses(List<String> dLicenses) {
        try {
            driverController.addLicenses(dLicenses);
            return new Response(true);
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeLicense(String dLicense) {
        try {
            driverController.removeLicense(dLicense);
            return new Response(true);
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
