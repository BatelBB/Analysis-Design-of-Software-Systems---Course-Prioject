package groupk.logistics.business;

import groupk.logistics.DataLayer.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TruckManagerController extends UserController{

    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;
    private VehicleMapper vehicleMapper;
    private TruckingMapper truckingMapper;
    private UserMapper userMapper;
    private Truckings_DestsMapper truckings_destsMapper;
    private Truckings_SourcesMapper sourcesMapper;
    private Truckings_ProductsMapper productsMapper;



    public static TruckManagerController getInstance() throws Exception {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    private TruckManagerController() throws Exception {
        super(null);
        truckingMapper = new TruckingMapper();
        truckingIdCounter = truckingMapper.getNextIdForTrucking();
        vehicleMapper = new VehicleMapper();
        userMapper = new UserMapper();
        truckings_destsMapper = new Truckings_DestsMapper();
        sourcesMapper= new Truckings_SourcesMapper();
        productsMapper = new Truckings_ProductsMapper();
    }

    public void reserForTests()
    {
        truckingIdCounter = 1;
    }

    public void addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        checkIfActiveUserIsTruckManager();
        Vehicle newVehicle = new Vehicle(lisence, registrationPlate, model, weight, maxWeight);
        boolean success = vehicleMapper.addVehicle(lisence, registrationPlate, model, weight, maxWeight);
        if(success)
            vehicleMapper.addVehicle(newVehicle);
    }

    public List<String> getDriversUsernames() throws Exception {
        checkIfActiveUserIsTruckManager();
        return null; //TODO
    }

    public List<String> getVehiclesRegistrationPlates() throws Exception {
        checkIfActiveUserIsTruckManager();
        return vehicleMapper.selectAll();
    }

    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
        boolean checkTrucking = (TruckManager.checkTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes));
        if (!truckingMapper.checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        truckingMapper.checkConflicts(driverUsername, registrationPlateOfVehicle, date, hours, minutes);
        if(checkTrucking) {
            List<Site> sources_ = checkSites(sources);
            List<Site> destinations_ = checkSites(destinations);
            List<ProductForTrucking> productForTruckings = Trucking.productForTruckings(products);
            TruckingDTO trucking = new TruckingDTO(truckingIdCounter,date,getActiveUser().getUsername(),driverUsername,registrationPlateOfVehicle,hours,minutes,0);
            boolean added = truckingMapper.addTrucking(trucking);
            sourcesMapper.addTruckingSources(truckingIdCounter,sources_); //to fix
            truckings_destsMapper.addTruckingDestinations(truckingIdCounter,destinations_);
            productsMapper.addTruckingProducts(truckingIdCounter,productForTruckings);
            if(added)
                truckingIdCounter++;
        }
    }

    private List<Site> checkSites(List<List<String>> Sites) throws Exception {
        checkIfActiveUserIsTruckManager();
        List<Site> sites = new LinkedList<>();
        Area area = null;
        for(List<String> site : Sites) {
            sites.add(new Site(site.get(0), site.get(1), site.get(2), site.get(3), Integer.parseInt(site.get(4)), Integer.parseInt(site.get(5)), Integer.parseInt(site.get(6)), site.get(7)));
            if (area == null)
                area = Site.castStringToArea(site.get(7));
            else {
                if (area.equals(Site.castStringToArea(site.get(7))))
                    throw new IllegalArgumentException("Not all sites from the same area");
            }
        }
        return sites;
    }



    public void removeTrucking(int truckingId) throws Exception {
        checkIfActiveUserIsTruckManager();
        sourcesMapper.removeTrucking(truckingId);
        truckings_destsMapper.removeTrucking(truckingId);
        productsMapper.removeTrucking(truckingId);
        if(!truckingMapper.removeTrucking(truckingId))
            throw new Exception("seem like there is no trucking with that id");
    }

    public String printBoard() throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerBoard(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistory() throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerHistoryTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckings() throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerFutureTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printBoardOfDriver(String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverBoard(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverHistoryTruckings(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverFutureTruckings(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printBoardOfVehicle(String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleBoard(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleHistoryTruckings(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager();
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleFutureTruckings(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public List<String> addSourcesToTrucking(int truckingId, List<List<String>> sources) throws Exception {
        checkIfActiveUserIsTruckManager();
        List<Site> sources_ = checkSites(sources);
        List<String> exceptions = sourcesMapper.addTruckingSources(truckingId, sources_);
        if(exceptions.size() == sources_.size())
            throw new Exception("Oops, We could not add any source");
        return exceptions;
    }

    public List<String> addDestinationToTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        checkIfActiveUserIsTruckManager();
        List<Site> destinations_ = checkSites(destinations);
        List<String> exceptions = truckings_destsMapper.addTruckingDestinations(truckingId, destinations_);
        if(exceptions.size() == destinations_.size())
            throw new Exception("Oops, We could not add any destination");
        return exceptions;
    }

    public void addProductToTrucking(int truckingId, String pruductName,int quantity) throws Exception {
        if(quantity<1) throw new Exception("Quantity is positive");
        if(!(pruductName.equals("eggs") | pruductName.equals("water") | pruductName.equals("milk")))
            throw new Exception("Illegal product");
        if(productsMapper.existProduct(truckingId,pruductName))
            productsMapper.increaseQuantity(truckingId,pruductName,quantity);
        else {
            Products products;
            if ((pruductName.equals("eggs"))) products = Products.Eggs_4902505139314;
            else if ((pruductName.equals("milk"))) products = Products.Milk_7290111607400;
            else products = Products.Water_7290019056966;
            productsMapper.addTruckingProduct(truckingId, new ProductForTrucking(products, quantity));
        }
    }

    public void updateSourcesOnTrucking(int truckingId, List<List<String>> sources) throws Exception {
        checkIfActiveUserIsTruckManager();
        //TODO
    }

    public void updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        checkIfActiveUserIsTruckManager();
        //TODO
    }

    public void moveProductsToTrucking(int truckingId, String pruductName, int quantity) throws Exception {
        if(!productsMapper.existProduct(truckingId,pruductName)) throw new Exception("This product is not in the trucking");
        if(!(pruductName.equals("eggs") | pruductName.equals("water") | pruductName.equals("milk"))) throw new Exception("Illegal product");
        if(productsMapper.getProducts(truckingId).size()>1)
        {
            if(Integer.parseInt(productsMapper.getQuantity(truckingId,pruductName))==quantity)
            productsMapper.removeProductsByTruckingId(truckingId,pruductName);
            if(Integer.parseInt(productsMapper.getQuantity(truckingId,pruductName))>quantity)
                productsMapper.increaseQuantity(truckingId,pruductName,(-1)*quantity);
            else throw new Exception("You dont have this quantity of this item in the order");
        }

        else
        {
            if(Integer.parseInt(productsMapper.getQuantity(truckingId,pruductName))>quantity)
                productsMapper.increaseQuantity(truckingId,pruductName,(-1)*quantity);
            else throw new Exception("Your trucking will be emptyy, Sorry man its not gonna work");
        }
    }

    public void updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        checkIfActiveUserIsTruckManager();
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        if (!truckingMapper.checkDriverLicenseMatch(trucking.getDriverUsername(), registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        truckingMapper.checkConflicts(trucking.getDriverUsername(), registrationPlateOfVehicle, trucking.getDate(), trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateVehicle(truckingId, registrationPlateOfVehicle))
            throw new IllegalArgumentException("No change of vehicle was made to order: " + truckingId + ". It maybe the same vehicle of before the change.");
    }

    public void updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager();
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        if (!truckingMapper.checkDriverLicenseMatch(driverUsername, trucking.getVehicleRegistrationPlate()))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        truckingMapper.checkConflicts(driverUsername, trucking.getVehicleRegistrationPlate(), trucking.getDate(), trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateDriver(truckingId, driverUsername))
            throw new IllegalArgumentException("No change of driver was made to order: " + truckingId + ". It maybe the same driver of before the change.");
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        checkIfActiveUserIsTruckManager();
        checkDate(date);
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        truckingMapper.checkConflicts(trucking.getDriverUsername(), trucking.getVehicleRegistrationPlate(), date, trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateDate(truckingId, date))
            throw new IllegalArgumentException("No change of date was made to order: " + truckingId + ". It maybe the same driver of before the change.");
    }

    private void checkIfActiveUserIsTruckManager() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new Exception("There is no user connected");
        if (getActiveUser().getRole() != Role.truckingManager | !(getActiveUser() instanceof TruckManager))
            throw new Exception("Oops, you are not a truck manager");
    }

    private String printTrucking(TruckingDTO trucking) throws Exception {
        String toReturn = "TRUCKING - " + trucking.getId() + "\n\n";
        toReturn += "TRUCKING DETAILS:\n";
        toReturn += "Date: " + trucking.getDate().getDayOfMonth() + "/" + trucking.getDate().getMonthValue() + "/" + trucking.getDate().getYear() + "\n";
        toReturn += "Start Hour: " + printHour(trucking.getDate()) + "\n";
        toReturn += "End Hour: " + printHour(trucking.getDate().plusHours(trucking.getHours()).plusMinutes(trucking.getMinutes())) + "\n";
        toReturn += "Vehicle registration plate: " + trucking.getVehicleRegistrationPlate() + "\n";
        toReturn += "Driver: " + trucking.getDriverUsername() + "\n";
        toReturn += printSources(trucking.getId());
        toReturn += printDestinations(trucking.getId());
        toReturn += printProducts(trucking.getId()) + "\n";
        if (trucking.getWeight() > 0)
            toReturn += "Total weight: " + trucking.getWeight() + "\n";
        else
            toReturn += "There is no data about the trucking weight\n";
        return toReturn;
    }

    private String printHour(LocalDateTime date) {
        String toReturn = "";
        if(date.getHour()<10)
            toReturn += "0" + date.getHour() + ":";
        else
            toReturn += date.getHour() + ":";
        if(date.getMinute()<10)
            toReturn += "0" + date.getMinute();
        else
            toReturn += date.getHour();
        return toReturn;
    }

    private String printSources(int TruckingID) throws Exception {
        String toReturn = "\nSOURCE DETAILS:\n";
        toReturn += printSitesList(sourcesMapper.getSourcesByTruckingId(TruckingID));
        return toReturn;
    }

    private String printDestinations(int TruckingID) throws Exception {
        String toReturn = "\nDESTINATION DETAILS:\n";
        toReturn += printSitesList(truckings_destsMapper.getDestinationsByTruckingId(TruckingID));
        return toReturn;
    }

    private String printProducts(int TruckingID) throws Exception {
        return "\nProduct DETAILS:\n"  + printProductsList(productsMapper.getProducts(TruckingID));
    }

    private String printSitesList(List<Site> sourcesOrDestinations) {
        String toReturn  = "";
        int siteCounter = 1;
        for (Site site : sourcesOrDestinations) {
            toReturn += siteCounter + ". " + site.printSite();
            siteCounter++;
        }
        return toReturn;
    }

    private String printProductsList(List<ProductForTrucking> productForTruckings) {
        String toReturn  = "";
        int siteCounter = 1;
        for (ProductForTrucking productForTrucking : productForTruckings) {
            toReturn += siteCounter + ". " + productForTrucking.printProductForTrucking() + "\n";
            siteCounter++;
        }
        return toReturn;
    }

    private boolean checkDate(LocalDateTime date) {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Oops, the date must be in the future");
        return true;
    }

}
