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
}


