package groupk.logistics.business;

import groupk.logistics.DataLayer.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class TruckManagerController {

    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;
    private VehicleMapper vehicleMapper;
    private TruckingMapper truckingMapper;
    private Truckings_DestsMapper truckings_destsMapper;
    private Truckings_SourcesMapper sourcesMapper;
    private Truckings_ProductsMapper productsMapper;
    private DriverLicencesMapper driverLicensesMapper;



    public static TruckManagerController getInstance() throws Exception {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    private TruckManagerController() throws Exception {
        truckingMapper = new TruckingMapper();
        truckingIdCounter = truckingMapper.getNextIdForTrucking();
        vehicleMapper = new VehicleMapper();
        truckings_destsMapper = new Truckings_DestsMapper();
        sourcesMapper= new Truckings_SourcesMapper();
        productsMapper = new Truckings_ProductsMapper();
        driverLicensesMapper = new DriverLicencesMapper();
    }

    public void reserForTests()
    {
        truckingIdCounter = 1;
    }

    public void addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        checkVehicle(lisence, registrationPlate, model, weight, maxWeight);
        VehicleDTO newVehicle = new VehicleDTO(lisence, registrationPlate, model, weight, maxWeight);
        boolean success = vehicleMapper.addVehicle(lisence, registrationPlate, model, weight, maxWeight);
        if(success)
            vehicleMapper.addVehicle(newVehicle);
    }

    private boolean checkVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) {
        if (weight <= 0)
            throw new IllegalArgumentException("Weight is positive");
        if (!validateRegistationPlate(registrationPlate))
            throw new IllegalArgumentException("Invalid registration plate");
        if (maxWeight <= weight)
            throw new IllegalArgumentException("Max wight is bigger then weight");
        if (!validateModel(model))
            throw new IllegalArgumentException("Invalid model");
        return true;
    }



    public List<String> getVehiclesRegistrationPlates() throws Exception {
        return vehicleMapper.getAllRegistrationPlates();
    }

    public void addTrucking(int truckManagerId,String registrationPlateOfVehicle, LocalDateTime date, int driverUsername, List<String[]> sources, List<String[]> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
        boolean checkTrucking = (checkTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes));
        if (!checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        checkConflicts(driverUsername, registrationPlateOfVehicle, date, hours, minutes);
        if(checkTrucking) {
            List<SiteDTO> sources_ = checkSites(sources);
            List<SiteDTO> destinations_ = checkSites(destinations);
            List<ProductForTruckingDTO> productForTruckings = productForTruckings(products);
            TruckingDTO trucking = new TruckingDTO(truckingIdCounter,date,truckManagerId,driverUsername,registrationPlateOfVehicle,hours,minutes,0);
            boolean added = truckingMapper.addTrucking(trucking);
            sourcesMapper.addTruckingSources(truckingIdCounter,sources_); //to fix
            truckings_destsMapper.addTruckingDestinations(truckingIdCounter,destinations_);
            productsMapper.addTruckingProducts(truckingIdCounter,productForTruckings);
            if(added)
                truckingIdCounter++;
        }
    }

    private List<SiteDTO> checkSites(List<String[]> Sites) throws Exception {
        List<SiteDTO> sites = new LinkedList<SiteDTO>();
        Area area = null;
        for(String[] site : Sites) {
            if(site == null | site.length != 7)
                throw new IllegalArgumentException("Oops, one or more details about the site is empty");
            try {
                checkSite(site[0], site[1], site[2], site[3], Integer.parseInt(site[4]), Integer.parseInt(site[5]), Integer.parseInt(site[6]), site[7]);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Oops, you needed to enter number at the site details");
            }
            sites.add(new SiteDTO(site[0], site[1], site[2], site[3], Integer.parseInt(site[4]), Integer.parseInt(site[5]), Integer.parseInt(site[6]), site[7]));
            if (area == null)
                area = Area.castStringToArea(site[7]);
            else {
                if (area.equals(Area.castStringToArea(site[7])))
                    throw new IllegalArgumentException("Not all sites from the same area");
            }
        }
        return sites;
    }

    private List<ProductForTruckingDTO> productForTruckings(List <Map<String,Integer>> map)
    {
        List<ProductForTruckingDTO> productForTruckings = new LinkedList<>();
        for(int i = 0 ; i < map.size();i++)
        {
            Map<String,Integer> prod = map.get(0);
            String[] productsArr = Products.getProductsSKUList();
            for (int j = 0; j < productsArr.length; j++) {
                if(prod.containsKey(productsArr[j])) {
                    int quantity = prod.get(productsArr[j]);
                    if (quantity < 1)
                        throw new IllegalArgumentException("Oops, the quantity of " + productsArr[j] + " most be positive");
                    productForTruckings.add(new ProductForTruckingDTO(productsArr[j], prod.get(productsArr[j])));
                }
            }
        }
        return productForTruckings;
    }

    public void removeTrucking(int truckingId) throws Exception {
        sourcesMapper.removeTrucking(truckingId);
        truckings_destsMapper.removeTrucking(truckingId);
        productsMapper.removeTrucking(truckingId);
        if(!truckingMapper.removeTrucking(truckingId))
            throw new Exception("seem like there is no trucking with that id");
    }

    public String printBoard(int truckManagerID) throws Exception {
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerBoard(truckManagerID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistory(int truckManagerID) throws Exception {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerHistoryTruckings(truckManagerID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckings(int truckManagerID) throws Exception {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerFutureTruckings(truckManagerID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printBoardOfDriver(int driverUsername) throws Exception {
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

    public String printTruckingsHistoryOfDriver(int driverUsername) throws Exception {
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

    public String printFutureTruckingsOfDriver(int driverUsername) throws Exception {
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

    public List<String> addSourcesToTrucking(int truckingId, List<String[]> sources) throws Exception {
        List<SiteDTO> sources_ = checkSites(sources);
        List<String> exceptions = sourcesMapper.addTruckingSources(truckingId, sources_);
        if(exceptions.size() == sources_.size())
            throw new Exception("Oops, We could not add any source");
        return exceptions;
    }

    public List<String> addDestinationToTrucking(int truckingId, List<String[]> destinations) throws Exception {
        List<SiteDTO> destinations_ = checkSites(destinations);
        List<String> exceptions = truckings_destsMapper.addTruckingDestinations(truckingId, destinations_);
        if(exceptions.size() == destinations_.size())
            throw new Exception("Oops, We could not add any destination");
        return exceptions;
    }

    public void addProductToTrucking(int truckingId, String productName,int quantity) throws Exception {
        if(quantity<1) throw new Exception("Quantity is positive");
        if (!Products.contains(productName))
            throw new IllegalArgumentException("Oops, the product SKU doesn't exist");
        if(productsMapper.existProduct(truckingId,productName))
            productsMapper.increaseQuantity(truckingId,productName,quantity);
        else {
            productsMapper.addTruckingProduct(truckingId, new ProductForTruckingDTO(productName, quantity));
        }
    }

    public String getProductString(Products product) {
        String productToReturn = "";
        if(product==Products.Water_7290019056966)
            productToReturn = "water";
        if(product==Products.Milk_7290111607400)
            productToReturn = "milk";
        if(product==Products.Eggs_4902505139314)
            productToReturn = "eggs";
        return productToReturn;
    }

    public void updateSourcesOnTrucking(int truckingId, List<List<String>> sources) throws Exception {
        //TODO
    }

    public void updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) throws Exception {
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

    public void updateVehicleOnTrucking(int driverUsername, int truckingId, String registrationPlateOfVehicle) throws Exception {
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        if (!checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        checkConflicts(trucking.getDriverUsername(), registrationPlateOfVehicle, trucking.getDate(), trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateVehicle(truckingId, registrationPlateOfVehicle))
            throw new IllegalArgumentException("No change of vehicle was made to order: " + truckingId + ". It maybe the same vehicle of before the change.");
    }

    public void updateDriverOnTrucking(int truckingId, int driverUsername) throws Exception {
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        if (!checkDriverLicenseMatch(driverUsername, trucking.getVehicleRegistrationPlate()))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        checkConflicts(driverUsername, trucking.getVehicleRegistrationPlate(), trucking.getDate(), trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateDriver(truckingId, driverUsername))
            throw new IllegalArgumentException("No change of driver was made to order: " + truckingId + ". It maybe the same driver of before the change.");
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        checkDate(date);
        TruckingDTO trucking = truckingMapper.getTruckingByID(truckingId);
        if (trucking == null)
            throw new IllegalArgumentException("There is no trucking with id: " + truckingId);
        checkConflicts(trucking.getDriverUsername(), trucking.getVehicleRegistrationPlate(), date, trucking.getHours(), trucking.getMinutes());
        if (!truckingMapper.updateDate(truckingId, date))
            throw new IllegalArgumentException("No change of date was made to order: " + truckingId + ". It maybe the same driver of before the change.");
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

    private String printSitesList(List<SiteDTO> sourcesOrDestinations) {
        String toReturn  = "";
        int siteCounter = 1;
        for (SiteDTO site : sourcesOrDestinations) {
            toReturn += siteCounter + ". " + printSite(site);
            siteCounter++;
        }
        return toReturn;
    }

    private String printSite(SiteDTO site) {
        String toReturn = "Area: " + site.getArea() + "\n";
        toReturn += "Address: " + site.getStreet() + " " + site.getHouseNumber() + ", " + site.getCity() + "\n";
        if (site.getApartment() != 0 | site.getFloor() != 0)
            toReturn += "floor: " + site.getFloor() + " apartment: " + site.getApartment() + "\n";
        toReturn += "Contact guy: " + site.getContactGuy() + "  phone number: " + site.getPhoneNumber() + "\n";
        return toReturn;
    }

    private String printProductsList(List<ProductForTruckingDTO> productForTruckings) {
        String toReturn  = "";
        int siteCounter = 1;
        for (ProductForTruckingDTO productForTrucking : productForTruckings) {
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

    private boolean checkDriverLicenseMatch(int driverUserName, String RegistrationPlate) throws Exception{
        List<String> driverLicenses = driverLicensesMapper.getMyLicenses(driverUserName);
        String license = vehicleMapper.getLicense(RegistrationPlate);
        if (driverLicenses == null)
            throw new IllegalArgumentException("There is not licenses we can see of that driver");
        if (license == null)
            throw new IllegalArgumentException("We cannot found the vehicle's license");
        if (driverLicenses.contains(license))
            return true;
        return false;
    }

    private boolean checkConflicts(int driverUserName, String VehicleRegristationPlate, LocalDateTime date, long hoursOfTrucking, long minutesOfTrucking) throws Exception {
        List<TruckingDTO> conflictingEvents = truckingMapper.getRelevantTruckings(date);
        LocalDateTime endTruck = date.plusHours(hoursOfTrucking).plusMinutes(minutesOfTrucking);
        ListIterator<TruckingDTO> truckingListIterator = conflictingEvents.listIterator();
        while (truckingListIterator.hasNext()) {
            TruckingDTO currentTrucking = truckingListIterator.next();
            LocalDateTime startCurr = currentTrucking.getDate();
            LocalDateTime endCurr = currentTrucking.getDate().plusHours(currentTrucking.getHours()).plusMinutes(currentTrucking.getMinutes());
            if (!(endTruck.isBefore(startCurr) | date.isAfter(endCurr))) {
                checkAvailibility(currentTrucking.getVehicleRegistrationPlate() ,VehicleRegristationPlate, currentTrucking.getDriverUsername(),driverUserName);
            }
        }
        return true;
    }

    private boolean checkAvailibility(String registrationPlate1, String registrationPlate2,int driverUserName1,int driverUserName2) {
        if (registrationPlate1 == registrationPlate2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same vehicle");
        if (driverUserName1 == driverUserName2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same driver");
        return true;
    }

    private boolean checkTrucking(int id, String registrationPlateOfVehicle, LocalDateTime date, int driverUsername, List<String[]> sources, List<String[]> destinations, List<Map<String,Integer>> products,long hours, long minutes) {
        if (registrationPlateOfVehicle == null)
            throw new NullPointerException("The registration plate is empty");
        if (date == null)
            throw new NullPointerException("The date is empty");
        if (sources == null | sources.size() == 0)
            throw new NullPointerException("The sources list is empty");
        if (destinations == null | destinations.size() == 0)
            throw new NullPointerException("The destinations list is empty");
        if (products == null | products.size() == 0)
            throw new NullPointerException("The products list is empty");
        return true;
    }

    private void checkSite(String contactGuy, String city, String phoneNumber, String street, int houseNumber, int floor, int apartment, String area) {
        if (contactGuy == null | contactGuy.length() == 0 | city == null | city.length() == 0 | phoneNumber == null | phoneNumber.length() == 0 | street == null | street.length() == 0)
            throw new IllegalArgumentException("One or more of the site details are empty");
        validateInt(floor, "floor", 0, 100);
        validateInt(apartment, "apartment", 0, 100);
        validateInt(houseNumber, "house number", 1, 300);
        validateString(city, "city", 2, 20);
        validateString(city, "city", 2, 20);
        validateString(city, "contact guy", 2, 15);
        validatePhoneNumber(phoneNumber);
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberWithoutHyphens = "";
        for (int index = 0; index < phoneNumber.length(); index++) {
            char charAt = phoneNumber.charAt(index);
            if (charAt > 47 && charAt < 58)
                phoneNumberWithoutHyphens += charAt;
            else if (charAt != 45)
                throw new IllegalArgumentException("The phone number is not validate. the phone number must contains only digits");
        }
        if (phoneNumberWithoutHyphens.length() < 8 | phoneNumberWithoutHyphens.length() > 12)
            throw new IllegalArgumentException("The phone number is too short/long");
        phoneNumber = phoneNumberWithoutHyphens;
        return true;
    }

    private boolean validateInt(int fieldToCheck, String fieldToCheckName, int min, int max) {
        if(fieldToCheck >= min & fieldToCheck <= max)
            return true;
        throw new IllegalArgumentException(fieldToCheckName + " isn't valid. Need to be between " + String.valueOf(min) + "-" + String.valueOf(max) + ".");
    }

    private boolean validateString(String fieldToCheck, String fieldToCheckName, int minLength, int maxLength) {
        if (fieldToCheck == null)
            throw new IllegalArgumentException(fieldToCheckName + " is empty");
        if (fieldToCheck.length() < minLength | fieldToCheck.length() > maxLength)
            throw new IllegalArgumentException(fieldToCheckName + " isn't valid");
        return true;
    }

    private boolean validateRegistationPlate(String registationPlate) {
        if (registationPlate == null)
            throw new IllegalArgumentException("Oops, registration plate is empty");
        if (registationPlate.length() != 8 & registationPlate.length() != 7)
            return  false;
        for(int i = 0 ; i < registationPlate.length(); i++) {
            if (!(Character.isDigit(registationPlate.charAt(i))))
                throw new IllegalArgumentException("Registration plate must has only digits");
        }
        return true;
    }

    private boolean validateModel(String model) {
        if (model == null)
            throw new IllegalArgumentException("model is empty");
        if (model.length() <3 | model.length() > 15)
            return  false;
        for (int i = 0; i < model.length(); i++) {
            if (!(Character.isLetter(model.charAt(i)) | Character.isDigit(model.charAt(i)) | model.charAt(i) == ' '))
                return  false;
        }
        return true;
    }

}
