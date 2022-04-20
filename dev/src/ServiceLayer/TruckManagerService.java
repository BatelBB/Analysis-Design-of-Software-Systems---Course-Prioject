package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDateTime;
import java.util.List;

public class TruckManagerService {


    private TruckManagerController truckManagerController;



    public Response Register(String id, String name, String username, String password, Role role, String code) {
        try {
            truckManagerController.registerTM(id,name,username,password,role,code);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response Login(String username, String password) {
        try {
            truckManagerController.LoginTruckManager(username,password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response showTruckings(String username) {
        try {
            truckManagerController.showTruckings(username);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response showTrucks(String username) {
        try {
            truckManagerController.showTrucks(username);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response showDrivers(String username) {
        try {
            truckManagerController.showDrivers(username);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }




    public Response addTruck(String username, DLicense lisence, String registationPlate, String model, int weight, int maxWeight) {
        try {
            truckManagerController.addTruck(username,lisence , registationPlate, model, weight, maxWeight);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response<String> addVehicle(Vehicle vehicle) {
        try {
            truckManagerController.addVehicle(vehicle);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<String> addDriverToTM(Driver driver) {
        try {
            truckManagerController.addDriver(driver);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<String> printTruckings() {
        truckManagerController.printTruckings();
        return new Response("Well printed");
    }

    public Response<String> printDoneTruckings() {
        truckManagerController.printDoneTruckings();
        return new Response("Well printed");
    }

    public Response<String> printFutureTruckings() {
        truckManagerController.printFutureTruckings();
        return new Response("Well printed");
    }
    public  Response<String>  addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            truckManagerController.addSourcesToTrucking(truckingId,sources);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response<String>  addDestinationsToTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            truckManagerController.addDestinationsToTrucking(truckingId,destinations);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response<String>  addProductsToTrucking(int truckingId, ProductForTrucking products) throws Exception {
        try {
            truckManagerController.addProductsToTrucking(truckingId,products);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


    public  Response<String>  updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            truckManagerController.updateSourcesOnTrucking(truckingId,sources);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response<String>  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            truckManagerController.updateDestinationsOnTrucking(truckingId,destinations);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response<String>  moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        try {
            truckManagerController.moveProductsToTrucking(truckingId,productSKU);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response<String>  updateVehicleOnTrucking(int truckingId, Vehicle vehicle) throws Exception {
        try {
            truckManagerController.updateVehicleOnTrucking(truckingId,vehicle);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response<String>  updateDriverOnTrucking(int truckingId, Driver driver) throws Exception {
        try {
            truckManagerController.updateDriverOnTrucking(truckingId,driver);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response<String>  updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        try {
            truckManagerController.updateDateOnTrucking(truckingId,date);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


}


