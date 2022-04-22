package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDateTime;
import java.util.List;


public class UserService {

    private UserController userController;

    public UserService() throws Exception {
        userController = UserController.getInstance();
    }

    public Response setWeightForTrucking(int truckingId, int weight) throws Exception {
        try {
            userController.setWeightForTrucking(truckingId,weight);
            return new Response("Set weight successfully");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printMyTruckings() throws Exception {
        try {
            return new Response(userController.printMyTruckings());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printMyTruckingsHistory() throws Exception {
        try {
            return new Response(userController.printMyTruckingsHistory());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response showDriverHisFutureTruckings() {
        try {
            String truckings = userController.printMyFutureTruckings();
            return new Response(truckings);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicense(DLicense dLicense) {
        try {
            userController.addLicense(dLicense);
            return new Response("Added license");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addLicenses(List<DLicense> dLicenses) {
        try {
            userController.addLicenses(dLicenses);
            return new Response("Added licenses");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeLicense(DLicense dLicense) {
        try {
            userController.removeLicense(dLicense);
            return new Response("License removed");
        }
        catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response removeTrucking(int truckingId) throws Exception {
        try {
            userController.removeTrucking(truckingId);
            return new Response("Trucking removed");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoard() throws Exception {
        try {
            return new Response(userController.printBoard());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistory() throws Exception {
        try {
            return new Response(userController.printTruckingsHistory());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response getRegisterCode() throws Exception {
        try {
            return new Response(userController.getRegisterCode());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }



    public Response getDriversUsernames() throws Exception {
        try {
            List<String> userNamesOfDrivers = userController.getDriversUsernames();
            return new Response(userNamesOfDrivers);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response getVehiclesRegistrationPlates() throws Exception {
        try {
            List<String> vehiclesRegistrationPlates = userController.getVehiclesRegistrationPlates();
            return new Response(vehiclesRegistrationPlates);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hours, long minutes) throws Exception {
        try {
            userController.addTrucking(registrationPlateOfVehicle,date,driverUsername,sources,destinations,products,hours,minutes);
            return new Response("Trucking added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printBoardOfDriver(String driverUsername) throws Exception {
        try {
            return new Response(userController.printBoardOfDriver(driverUsername));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        try {
            return new Response(userController.printTruckingsHistoryOfDriver(driverUsername));
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
            return new Response(userController.printBoardOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        try {
            return new Response(userController.printTruckingsHistoryOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        try {
            return new Response(userController.printFutureTruckingsOfVehicle(registrationPlate));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            userController.addDestinationToTrucking(truckingId,destinations);
            return new Response("Destinations added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        try {
            userController.addProductToTrucking(truckingId,productForTrucking);
            return new Response("Product added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) {
        try {
            userController.addVehicle(lisence,registrationPlate,model,weight,maxWeight);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response printFutureTruckings() {
        try {
            return new Response(userController.printFutureTruckings());
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public  Response  addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            userController.addSourcesToTrucking(truckingId,sources);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


    public  Response  updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        try {
            userController.updateSourcesOnTrucking(truckingId,sources);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        try {
            userController.updateDestinationsOnTrucking(truckingId,destinations);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response  moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        try {
            userController.moveProductsToTrucking(truckingId,productSKU);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        try {
            userController.updateVehicleOnTrucking(truckingId,registrationPlateOfVehicle);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}
    public  Response updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        try {
            userController.updateDriverOnTrucking(truckingId,driverUsername);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}

    public  Response updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        try {
            userController.updateDateOnTrucking(truckingId,date);
            return new Response("Well added");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }}


    public Response registerUser(String name, String username, String password, Role role, String code)
    {
        try
        {
            boolean success = userController.registerUser( name,  username,  password,  role,  code);
            return new Response("Register success");
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response Login(String userEmail, String password)
    {
        try
        {
            boolean success = userController.login(userEmail,password);
            if(success) return new Response("Login success");
            else return new Response("Wrong username or wrong password");
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response Logout()
    {
        try
        {
            boolean success = userController.logout();
            if(success) return new Response("Logout success");
            else return new Response("Logut failed");
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response<String> updatePassword(String newPassword) {
        try
        {
            boolean success = userController.updatePassword(newPassword);
            return new Response(success);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
}
