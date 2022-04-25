package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDateTime;
import java.util.List;

public class TruckManagerService {


    private TruckManagerController truckManagerController;


    public Response removeTrucking(int truckingId) throws Exception {
        try {
            truckManagerController.removeTrucking(truckingId);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoard() throws Exception {
        try {
            return new Response(truckManagerController.printBoard());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistory() throws Exception {
        try {
            return new Response(truckManagerController.printTruckingsHistory());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response getDriversUsernames() throws Exception {
        try {
            List<String> userNamesOfDrivers = truckManagerController.getDriversUsernames();
            return new Response(userNamesOfDrivers);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response getVehiclesRegistrationPlates() throws Exception {
        try {
            List<String> vehiclesRegistrationPlates = truckManagerController.getVehiclesRegistrationPlates();
            return new Response(vehiclesRegistrationPlates);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hour, long minutes) throws Exception {
        try {
            truckManagerController.addTrucking(registrationPlateOfVehicle,date,driverUsername,sources,destinations,products,hour,minutes);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoardOfDriver(String driverUsername) throws Exception {
        try {
            return new Response(truckManagerController.printBoardOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        try {
            return new Response(truckManagerController.printTruckingsHistoryOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        try {
            return new Response(printFutureTruckingsOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoardOfVehicle(String registrationPlate) throws Exception {
        try {
            return new Response(truckManagerController.printBoardOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        try {
            return new Response(truckManagerController.printTruckingsHistoryOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        try {
            return new Response(truckManagerController.printFutureTruckingsOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            truckManagerController.addDestinationToTrucking(truckingId,destinations);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        try {
            truckManagerController.addProductToTrucking(truckingId,productForTrucking);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) {
        try {
            truckManagerController.addVehicle(lisence,registrationPlate,model,weight,maxWeight);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response printFutureTruckings() {
        try {
            return new Response(truckManagerController.printFutureTruckings());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public  Response  addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            truckManagerController.addSourcesToTrucking(truckingId,sources);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


    public  Response  updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            truckManagerController.updateSourcesOnTrucking(truckingId,sources);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            truckManagerController.updateDestinationsOnTrucking(truckingId,destinations);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response  moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        try {
            truckManagerController.moveProductsToTrucking(truckingId,productSKU);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        try {
            truckManagerController.updateVehicleOnTrucking(truckingId,registrationPlateOfVehicle);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        try {
            truckManagerController.updateDriverOnTrucking(truckingId,driverUsername);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        try {
            truckManagerController.updateDateOnTrucking(truckingId,date);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


}
