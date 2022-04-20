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


    public Response<String> Logout()
    {
        return userService.Logout();
    }

    public Response<String> printTruckings()
    {
        return truckManagerService.printTruckings();
    }

    public Response<String> printDoneTruckings()
    {
        return truckManagerService.printDoneTruckings();
    }

    public Response<String> updatePassword(String newPassowrd)
    {
        return userService.updatePassword(newPassowrd);
    }

    public Response<String> printFutureTruckings()
    {
        return truckManagerService.printFutureTruckings();
    }

    public  Response<String>  addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        return truckManagerService.addSourcesToTrucking(truckingId,sources);

    }

    public  Response<String>  addDestinationsToTrucking(int truckingId, List<Site> destinations) throws Exception {
        return truckManagerService.addDestinationsToTrucking(truckingId,destinations);
    }

    public  Response<String>  addProductsToTrucking(int truckingId, ProductForTrucking products) throws Exception {
        return truckManagerService.addProductsToTrucking(truckingId,products);
    }


    public  Response<String>  updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        return truckManagerService.updateSourcesOnTrucking(truckingId,sources);
    }

    public  Response<String>  updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        return truckManagerService.updateDestinationsOnTrucking(truckingId,destinations);
    }

    public  Response<String>  moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        return truckManagerService.moveProductsToTrucking(truckingId,productSKU);
    }

    public  Response<String>  updateVehicleOnTrucking(int truckingId, Vehicle vehicle) throws Exception {
        return truckManagerService.updateVehicleOnTrucking(truckingId,vehicle);
    }
    public  Response<String>  updateDriverOnTrucking(int truckingId, Driver driver) throws Exception {
        return truckManagerService.updateDriverOnTrucking(truckingId,driver);
    }

    public  Response<String>  updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        return truckManagerService.updateDateOnTrucking(truckingId,date);
    }


    public Response<String> addDriverToTM(Driver driver)
    {
        return truckManagerService.addDriverToTM(driver);
    }

    public Response<String> addVehicle(Vehicle vehicle)
    {
        return truckManagerService.addVehicle(vehicle);
    }



    public Response<String> Login(String userEmail,String password)
    {
        return userService.Login(userEmail,password);
    }

    public Response<String> registerUser(String name, String username, String password, Role role, String code)
    {
        return userService.registerUser(name,username,password,role,code);
    }

    public Response showDriverHisFutureTruckings(String name, String username, String password, Role role, String code)
    {
        return driverService.showDriverHisFutureTruckings();
    }

    public Response addLicenseToDriver(DLicense dLicense)
    {
        return driverService.addLicense(dLicense);
    }

    public Response addLicensesToDriver(List<DLicense> dLicenses)
    {
        return driverService.addLicenses(dLicenses);
    }

    public Response removeLicenseFromDriver(DLicense dLicense)
    {
        return driverService.removeLicense(dLicense);
    }




}



