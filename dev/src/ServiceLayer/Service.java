package ServiceLayer;

import BusinessLayer.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

public class Service {

    UserService userService;

    public Service() throws Exception {
        userService = new UserService();
    }

    public Response getRegisterCode() throws Exception {return userService.getRegisterCode(); }

    public Response removeTrucking(int truckingId) throws Exception {return userService.removeTrucking(truckingId); }

    public Response printBoard() throws Exception {return userService.printBoard(); }

    public Response printTruckingsHistory() throws Exception { return userService.printTruckingsHistory(); }

    public Response getDriversUsernames() throws Exception {return userService.getDriversUsernames();}

    public Response getVehiclesRegistrationPlates() throws Exception { return userService.getVehiclesRegistrationPlates();}

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hours, long minutes) throws Exception { return userService.addTrucking(registrationPlateOfVehicle,date,driverUsername,sources,destinations,products,hours,minutes); }

    public Response printBoardOfDriver(String driverUsername) throws Exception {return userService.printBoardOfDriver(driverUsername); }

    public Response printTruckingsHistoryOfDriver(String driverUsername) throws Exception {return userService.printTruckingsHistoryOfDriver(driverUsername);}

    public Response printFutureTruckingsOfDriver(String driverUsername) throws Exception {return userService.printFutureTruckingsOfDriver(driverUsername);}

    public Response printBoardOfVehicle(String registrationPlate) throws Exception {return userService.printBoardOfVehicle(registrationPlate); }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception { return userService.printTruckingsHistoryOfVehicle(registrationPlate);}

    public Response printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {return  userService.printFutureTruckingsOfVehicle(registrationPlate);}

    public Response addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {return userService.addDestinationToTrucking(truckingId,destinations);}

    public Response addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {return userService.addProductToTrucking(truckingId,productForTrucking); }

    public Response Logout() { return userService.Logout();}

    public Response updatePassword(String newPassowrd) { return userService.updatePassword(newPassowrd); }

    public Response printFutureTruckings() { return userService.printFutureTruckings(); }

    public  Response addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception { return userService.addSourcesToTrucking(truckingId,sources); }

    public  Response updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception { return userService.updateSourcesOnTrucking(truckingId,sources); }

    public  Response  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception { return userService.updateDestinationsOnTrucking(truckingId,destinations); }

    public  Response moveProductsToTrucking(int truckingId, Products productSKU) throws Exception { return userService.moveProductsToTrucking(truckingId,productSKU); }

    public  Response  updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception { return userService.updateVehicleOnTrucking(truckingId,registrationPlateOfVehicle); }

    public  Response  updateDriverOnTrucking(int truckingId, String driverUserName) throws Exception { return userService.updateDriverOnTrucking(truckingId,driverUserName); }

    public  Response  updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception { return userService.updateDateOnTrucking(truckingId,date); }

    public Response addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) { return userService.addVehicle(lisence,registrationPlate,model,weight,maxWeight); }

    public Response Login(String userEmail,String password) { return userService.Login(userEmail,password);}

    public Response registerUser(String name, String username, String password, BusinessLayer.Role role, String code) { return userService.registerUser(name,username,password,role,code); }

    public Response showDriverHisFutureTruckings(String name, String username, String password, Role role, String code) { return userService.showDriverHisFutureTruckings(); }

    public Response addLicenseToDriver(DLicense dLicense) { return userService.addLicense(dLicense); }

    public Response addLicensesToDriver(List<DLicense> dLicenses) { return userService.addLicenses(dLicenses); }

    public Response removeLicenseFromDriver(DLicense dLicense) { return userService.removeLicense(dLicense); }

    public Response setWeightForTrucking(int truckingId, int weight) throws Exception { return userService.setWeightForTrucking(truckingId, weight);}

    public Response printMyTruckings() throws Exception {return userService.printMyTruckings(); }

    public Response printMyTruckingsHistory() throws Exception { return userService.printMyTruckingsHistory(); }
}



