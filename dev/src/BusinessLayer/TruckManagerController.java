package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckManagerController extends UserController{

    private Map<String, String> users;
    private Map<String,TruckManager> mapTM;
    private List<TruckManager> TruckManagers;

    public  TruckManagerController()
    {
        users= new ConcurrentHashMap<>();
        TruckManagers = new LinkedList<>();
        mapTM  = new ConcurrentHashMap<>();
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicle==null) throw new IllegalArgumentException("Empty vehicle ? no no no");
        boolean validVehicle = vehicle.checkVehicle();
        if(validVehicle) ((TruckManager)activeUser).addVehicle(vehicle);
    }

    public void addDriver(Driver driver) throws Exception {
        ((TruckManager)activeUser).addDriver(driver);
    }

    public void printTruckings() {
        ((TruckManager)activeUser).printBoard();
    }

    public void printDoneTruckings() {
        ((TruckManager)activeUser).printDoneTruckings();

    }

    public void printFutureTruckings() {
        ((TruckManager)activeUser).printFutureTruckings();
    }
    public synchronized void addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        ((TruckManager)activeUser).addSourcesToTrucking(truckingId, sources);
    }

    public synchronized void addDestinationsToTrucking(int truckingId, List<Site> destinations) throws Exception {
        ((TruckManager)activeUser).addDestinationsToTrucking(truckingId, destinations);
    }

    public synchronized void addProductsToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        ((TruckManager)activeUser).addProductsToTrucking(truckingId, productForTrucking);
    }

    public synchronized void updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        ((TruckManager)activeUser).updateSourcesOnTrucking(truckingId, sources);
    }

    public synchronized void updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        ((TruckManager)activeUser).updateDestinationsOnTrucking(truckingId, destinations);
    }

    public synchronized void moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        ((TruckManager)activeUser).moveProductsToTrucking(truckingId, productSKU);
    }

    public synchronized void updateVehicleOnTrucking(int truckingId, Vehicle vehicle) throws Exception {
        ((TruckManager)activeUser).updateVehicleOnTrucking(truckingId, vehicle);
    }

    public synchronized void updateDriverOnTrucking(int truckingId, Driver driver) throws Exception {
        ((TruckManager)activeUser).updateDriverOnTrucking(truckingId, driver);
    }

    public synchronized void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        ((TruckManager)activeUser).updateDateOnTrucking(truckingId, date);
    }


    /*

    public void showTruckings(String username) {
        if(!(mapTM.containsKey(username) && mapTM.get(username).isLogin())) throw new IllegalArgumentException("Not login");
        else  mapTM.get(username).showTruckings();
    }

    public void showTrucks(String username) {
        if(!(mapTM.containsKey(username) && mapTM.get(username).isLogin())) throw new IllegalArgumentException("Not login");
        else  mapTM.get(username).showTrucks();
    }

    public void showDrivers(String username) {
        if(!(mapTM.containsKey(username) && mapTM.get(username).isLogin())) throw new IllegalArgumentException("Not login");
        else  mapTM.get(username).showDrivers();
    }

    public void addTruck(String username, DLicense lisence, String registationPlate, String model, int weight, int maxWeight) {
        if(!validateMaxWeight(maxWeight)) throw new IllegalArgumentException("Max weight is positive");
        if(!validateModel(model)) throw new IllegalArgumentException("Invalid model");
        if(!validateRegistationPlate(registationPlate))  throw new IllegalArgumentException("Invalid registration plate");
        if(!validateWeight(weight))throw new IllegalArgumentException("Weight is positive");
        if(!validateWeightSmallerThanMaxWeight(weight,maxWeight))throw new IllegalArgumentException("Max weight is bigger then weight");
        if(!(mapTM.containsKey(username) && mapTM.get(username).isLogin())) throw new IllegalArgumentException("Not login");
        else  mapTM.get(username).addTruck(lisence,registationPlate,model,weight,maxWeight);
    }

    private boolean validateRegistationPlate(String registationPlate)
    {
        if(registationPlate.length()!=8) return  false;
        for(int i = 0 ; i < registationPlate.length(); i++)
        {
            if(! (Character.isDigit(registationPlate.charAt(i)) )) return  false;
        }
        return true;

    }

    private boolean validateWeight(int weight)
    {
        return weight>0;
    }

    private boolean validateWeightSmallerThanMaxWeight(int weight,int maxWeight)
    {
        return maxWeight>weight;
    }

    private boolean validateMaxWeight(int maxWeight)
    {
        return maxWeight>0;
    }
    private boolean validateModel(String model)
    {
        if(model.length()<3 | model.length()>7) return  false;
        for(int i = 0 ; i < model.length(); i++)
        {
            if(!(Character.isLetter(model.charAt(i))| Character.isDigit(model.charAt(i)))) return  false;
        }
        return true;
    }

     */
}
