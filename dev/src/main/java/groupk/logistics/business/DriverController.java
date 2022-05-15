package groupk.logistics.business;

import groupk.logistics.DataLayer.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DriverController {

    private static DriverController singletonDriverControllerInstance = null;
    private DriverLicencesMapper driverLicencesMapper;
    private TruckingMapper truckingMapper;
    private Truckings_SourcesMapper sourcesMapper;
    private Truckings_DestsMapper truckings_destsMapper;
    private VehicleMapper vehicleMapper;
    private Truckings_ProductsMapper productsMapper;


    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() throws Exception {
        driverLicencesMapper = new DriverLicencesMapper();
        truckingMapper = new TruckingMapper();
        sourcesMapper = new Truckings_SourcesMapper();
        truckings_destsMapper = new Truckings_DestsMapper();
        vehicleMapper = new VehicleMapper();
        productsMapper = new Truckings_ProductsMapper();
    }

    public List<String> getMyLicenses(int driverUsername) throws Exception {
        List<String> toReturn = new LinkedList<String>();
        List<String> licenses = driverLicencesMapper.getMyLicenses(driverUsername);
        for (String license : licenses) {
            toReturn.add(license);
        }
        return toReturn;
    }

    public String printMyTruckings(int driverID) throws Exception {
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverBoard(driverID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printMyTruckingsHistory(int driverID) throws Exception {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverHistoryTruckings(driverID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printMyFutureTruckings(int driverID) throws Exception {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverFutureTruckings(driverID);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;

    }

    public boolean addLicense(int driverID, DLicense license) throws Exception {
        return driverLicencesMapper.addLicence(driverID ,license);
    }

    public boolean setWeightForTrucking(int truckingId, int weight) throws Exception {
        String registrationPlate = truckingMapper.getLicencePlate(truckingId);
        VehicleDTO vehicle = vehicleMapper.getVehicle(registrationPlate);
        checkWeight(vehicle,weight);
        return truckingMapper.setWeightForTrucking(truckingId,weight);
    }

    private void checkWeight(VehicleDTO vehicle,int weight) throws Exception {
        if(weight<=0) throw new Exception("Negative weight ? are you drunk?");
        if(vehicle.getMaxWeight()-weight<vehicle.getWeight()) throw new Exception("To heavy boss");
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
}

