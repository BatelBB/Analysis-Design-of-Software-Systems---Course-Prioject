package ServiceLayer;

import BusinessLayer.*;

import java.util.List;

public class DriverService {

    private DriverController driverController;

    public DriverService()
    {
        driverController = DriverController.getInstance();
    }

    public Response setWeightForTrucking(int truckingId, int weight) throws Exception {
        try {
            driverController.setWeightForTrucking(truckingId,weight);
            return new Response("Set weight successfully");
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
