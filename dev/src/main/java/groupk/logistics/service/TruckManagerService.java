package groupk.logistics.service;

import groupk.logistics.business.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TruckManagerService {


    private TruckManagerController truckManagerController = TruckManagerController.getInstance();

    public TruckManagerService() throws Exception {
    }


    public Response removeTrucking(int truckingId) {
        try {
            truckManagerController.removeTrucking(truckingId);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<String> printBoard() {
        Response<String> response;
        try {
            response = new  Response(truckManagerController.printBoard());
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }

    public Response<String> printTruckingsHistory() {
        Response<String> response;
        try {
            response = new Response(truckManagerController.printTruckingsHistory());
        } catch (Exception e) {
            response = new Response(e.getMessage());
        }
        return response;
    }




    public Response getVehiclesRegistrationPlates() {
        try {
            List<String> vehiclesRegistrationPlates = truckManagerController.getVehiclesRegistrationPlates();
            return new Response(vehiclesRegistrationPlates);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hour, long minutes) {
        try {
            truckManagerController.addTrucking(registrationPlateOfVehicle,date,driverUsername,sources,destinations,products,hour,minutes);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoardOfDriver(String driverUsername) {
        try {
            return new Response(truckManagerController.printBoardOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfDriver(String driverUsername) {
        try {
            return new Response(truckManagerController.printTruckingsHistoryOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printFutureTruckingsOfDriver(String driverUsername) {
        try {
            return new Response(truckManagerController.printFutureTruckingsOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoardOfVehicle(String registrationPlate) {
        try {
            return new Response(truckManagerController.printBoardOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) {
        try {
            return new Response(truckManagerController.printTruckingsHistoryOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printFutureTruckingsOfVehicle(String registrationPlate) {
        try {
            return new Response(truckManagerController.printFutureTruckingsOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addDestinationToTrucking(int truckingId, List<List<String>> destinations) {
        try {
            truckManagerController.addDestinationToTrucking(truckingId,destinations);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addProductToTrucking(int truckingId, String pruductName,int quantity) {
        try {
            truckManagerController.addProductToTrucking(truckingId,pruductName,quantity);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) {
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
    public  Response  addSourcesToTrucking(int truckingId,List<List<String>> sources) {
        try {
            truckManagerController.addSourcesToTrucking(truckingId,sources);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


    public  Response  updateSourcesOnTrucking(int truckingId, List<List<String>> sources) {
        try {
            truckManagerController.updateSourcesOnTrucking(truckingId,sources);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response  updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) {
        try {
            truckManagerController.updateDestinationsOnTrucking(truckingId,destinations);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response  moveProductsToTrucking(int truckingId, String productSKU) {
        try {
            truckManagerController.moveProductsToTrucking(truckingId,productSKU);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) {
        try {
            truckManagerController.updateVehicleOnTrucking(truckingId,registrationPlateOfVehicle);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response updateDriverOnTrucking(int truckingId, String driverUsername) {
        try {
            truckManagerController.updateDriverOnTrucking(truckingId,driverUsername);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateDateOnTrucking(int truckingId, LocalDateTime date) {
        try {
            truckManagerController.updateDateOnTrucking(truckingId,date);
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


}
