package ServiceLayer;

import BusinessLayer.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

public class Service {

    TruckManagerService truckManagerService;
    DriverService driverService;
    UserService userService;

    public Service()
    {
        driverService = new DriverService();
        truckManagerService = new TruckManagerService();
        userService = new UserService();
    }

    public Response removeTrucking(int truckingId) throws Exception {return truckManagerService.removeTrucking(truckingId); }

    public Response printBoard() throws Exception {return truckManagerService.printBoard(); }

    public Response printTruckingsHistory() throws Exception { return truckManagerService.printTruckingsHistory(); }

    public Response getDriversUsernames() throws Exception {return truckManagerService.getDriversUsernames();}

    public Response getVehiclesRegistrationPlates() throws Exception { return truckManagerService.getVehiclesRegistrationPlates();}

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products) throws Exception { return truckManagerService.addTrucking(registrationPlateOfVehicle,date,driverUsername,sources,destinations,products); }

    public Response printBoardOfDriver(String driverUsername) throws Exception {return truckManagerService.printBoardOfDriver(driverUsername); }

    public Response printTruckingsHistoryOfDriver(String driverUsername) throws Exception {return truckManagerService.printTruckingsHistoryOfDriver(driverUsername);}

    public Response printFutureTruckingsOfDriver(String driverUsername) throws Exception {return truckManagerService.printFutureTruckingsOfDriver(driverUsername);}

    public Response printBoardOfVehicle(String registrationPlate) throws Exception {return truckManagerService.printBoardOfVehicle(registrationPlate); }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception { return truckManagerService.printTruckingsHistoryOfVehicle(registrationPlate);}

    public Response printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {return  truckManagerService.printFutureTruckingsOfVehicle(registrationPlate);}

    public Response addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {return truckManagerService.addDestinationToTrucking(truckingId,destinations);}

    public Response addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {return truckManagerService.addProductToTrucking(truckingId,productForTrucking); }

    public Response Logout() { return userService.Logout();}

    public Response updatePassword(String newPassowrd) { return userService.updatePassword(newPassowrd); }

    public Response printFutureTruckings() { return truckManagerService.printFutureTruckings(); }

    public  Response addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception { return truckManagerService.addSourcesToTrucking(truckingId,sources); }

    public  Response updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception { return truckManagerService.updateSourcesOnTrucking(truckingId,sources); }

    public  Response  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception { return truckManagerService.updateDestinationsOnTrucking(truckingId,destinations); }

    public  Response moveProductsToTrucking(int truckingId, Products productSKU) throws Exception { return truckManagerService.moveProductsToTrucking(truckingId,productSKU); }

    public  Response  updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception { return truckManagerService.updateVehicleOnTrucking(truckingId,registrationPlateOfVehicle); }

    public  Response  updateDriverOnTrucking(int truckingId, String driverUserName) throws Exception { return truckManagerService.updateDriverOnTrucking(truckingId,driverUserName); }

    public  Response  updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception { return truckManagerService.updateDateOnTrucking(truckingId,date); }

    public Response addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) { return truckManagerService.addVehicle(lisence,registrationPlate,model,weight,maxWeight); }

    public Response Login(String userEmail,String password) { return userService.Login(userEmail,password);}

    public Response registerUser(String name, String username, String password, BusinessLayer.Role role, String code) { return userService.registerUser(name,username,password,role,code); }

    public Response showDriverHisFutureTruckings(String name, String username, String password, Role role, String code) { return driverService.showDriverHisFutureTruckings(); }

    public Response addLicenseToDriver(DLicense dLicense) { return driverService.addLicense(dLicense); }

    public Response addLicensesToDriver(List<DLicense> dLicenses) { return driverService.addLicenses(dLicenses); }

    public Response removeLicenseFromDriver(DLicense dLicense) { return driverService.removeLicense(dLicense); }

    public Response setWeightForTrucking(int truckingId, int weight) throws Exception { return driverService.setWeightForTrucking(truckingId, weight);}

    public Response printMyTruckings() throws Exception {return driverService.printMyTruckings(); }

    public Response printMyTruckingsHistory() throws Exception { return driverService.printMyTruckingsHistory(); }
}



