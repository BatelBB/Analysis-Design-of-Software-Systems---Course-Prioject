package groupk.logistics.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Service {

    TruckManagerService truckManagerService;
    DriverService driverService;
    UserService userService;

    public Service() throws Exception {
        driverService = new DriverService();
        truckManagerService = new TruckManagerService();
        userService = new UserService();
    }


    public Response removeTrucking(int truckingId) {
        return truckManagerService.removeTrucking(truckingId);
    }

    public Response<String> printBoard() {
        return truckManagerService.printBoard();
    }

    public Response<String> printTruckingsHistory() {
        return truckManagerService.printTruckingsHistory();
    }

    public Response<List<String>> getDriversUsernames() {
        return userService.getDriversUsernames();
    }

    public Response getVehiclesRegistrationPlates() {
        return truckManagerService.getVehiclesRegistrationPlates();
    }

    public Response addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) {
        return truckManagerService.addTrucking(registrationPlateOfVehicle, date, driverUsername, sources, destinations, products,hours,minutes);
    }

    public Response printBoardOfDriver(String driverUsername) {
        return truckManagerService.printBoardOfDriver(driverUsername);
    }

    public Response printTruckingsHistoryOfDriver(String driverUsername) {
        return truckManagerService.printTruckingsHistoryOfDriver(driverUsername);
    }

    public Response printFutureTruckingsOfDriver(String driverUsername) {
        return truckManagerService.printFutureTruckingsOfDriver(driverUsername);
    }

    public Response printBoardOfVehicle(String registrationPlate) {
        return truckManagerService.printBoardOfVehicle(registrationPlate);
    }

    public Response printTruckingsHistoryOfVehicle(String registrationPlate) {
        return truckManagerService.printTruckingsHistoryOfVehicle(registrationPlate);
    }

    public Response printFutureTruckingsOfVehicle(String registrationPlate) {
        return truckManagerService.printFutureTruckingsOfVehicle(registrationPlate);
    }

    public Response addDestinationToTrucking(int truckingId, List<List<String>> destinations) {
        return truckManagerService.addDestinationToTrucking(truckingId, destinations);
    }

    public Response addProductToTrucking(int truckingId, String pruductName,int quantity) {
        return truckManagerService.addProductToTrucking(truckingId, pruductName,quantity);
    }

    public Response getRegisterCode() {
        return truckManagerService.getRegisterCode();
    }

    public Response Logout() {
        return userService.Logout();
    }

    public Response updatePassword(String newPassowrd) {
        return userService.updatePassword(newPassowrd);
    }

    public Response<String> printFutureTruckings() {
        return truckManagerService.printFutureTruckings();
    }

    public Response addSourcesToTrucking(int truckingId, List<List<String>> sources) {
        return truckManagerService.addSourcesToTrucking(truckingId, sources);
    }

    public Response updateSourcesOnTrucking(int truckingId, List<List<String>> sources) {
        return truckManagerService.updateSourcesOnTrucking(truckingId, sources);
    }

    public Response updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) {
        return truckManagerService.updateDestinationsOnTrucking(truckingId, destinations);
    }

    public Response moveProductsToTrucking(int truckingId, String productSKU) {
        return truckManagerService.moveProductsToTrucking(truckingId, productSKU);
    }

    public Response updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) {
        return truckManagerService.updateVehicleOnTrucking(truckingId, registrationPlateOfVehicle);
    }

    public Response updateDriverOnTrucking(int truckingId, String driverUserName) {
        return truckManagerService.updateDriverOnTrucking(truckingId, driverUserName);
    }

    public Response updateDateOnTrucking(int truckingId, LocalDateTime date) {
        return truckManagerService.updateDateOnTrucking(truckingId, date);
    }

    public Response addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) {
        return truckManagerService.addVehicle(lisence, registrationPlate, model, weight, maxWeight);
    }

    public Response Login(String userEmail, String password) {
        return userService.Login(userEmail, password);
    }

    public Response registerUser(String name, String username, String password, String role, String code) {
        return userService.registerUser(name, username, password, role, code);
    }

    public Response<String> showDriverHisFutureTruckings() {
        return driverService.showDriverHisFutureTruckings();
    }

    public Response addLicenseToDriver(String dLicense) {
        return driverService.addLicense(dLicense);
    }

    public Response addLicensesToDriver(List<String> dLicenses) {
        return driverService.addLicenses(dLicenses);
    }


    public Response setWeightForTrucking(int truckingId, int weight) {
        return driverService.setWeightForTrucking(truckingId, weight);
    }

    public Response<String> printMyTruckings() {
        return driverService.printMyTruckings();
    }

    public Response<String> printMyTruckingsHistory() {
        return driverService.printMyTruckingsHistory();
    }

    public Response<List<String>> getLicenses() {
        return driverService.getLicenses();
    }

}
