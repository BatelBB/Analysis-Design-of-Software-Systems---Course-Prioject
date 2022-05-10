package groupk.logistics.service;

import groupk.logistics.business.*;

import java.util.List;

public class DriverService {

    private DriverController driverController;

    public DriverService() throws Exception {
        driverController = DriverController.getInstance();
    }

    public Response setWeightForTrucking(int truckingId, int weight) {
        try {
            driverController.setWeightForTrucking(truckingId,weight);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<String> printMyTruckings() {
        Response<String> response;
        try {
            response = new Response(driverController.printMyTruckings());
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response<String> printMyTruckingsHistory() {
        Response<String> response;
        try {
            response = new Response(driverController.printMyTruckingsHistory());
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response<String> showDriverHisFutureTruckings() {
        Response<String> response;
        try {
            String truckings = driverController.printMyFutureTruckings();
            response = new Response<>(truckings);
        } catch (Exception e) {
            response = new Response<>(e.getMessage());
        }
        return response;
    }

    public Response addLicense(String dLicense) {
        try {
            boolean added = driverController.addLicense(Vehicle.castDLicenseFromString(dLicense));
            return new Response(added);
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }



    public Response<List<String>> getLicenses() {
        List<String> licenses;
        Response<List<String>> toReturn;
        try {
            licenses = driverController.getMyLicenses();
            toReturn = new Response(licenses);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }
}
