package groupk.logistics.business;

import groupk.logistics.DataLayer.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DriverController extends UserController{

    private static DriverController singletonDriverControllerInstance = null;
    private DriverLicencesMapper driverLicencesMapper;
    private TruckingMapper truckingMapper;
    private Truckings_SourcesMapper sourcesMapper;
    private Truckings_DestsMapper truckings_destsMapper;
    private VehicleMapper vehicleMapper;


    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() throws Exception {
        super(null);
        driverLicencesMapper = new DriverLicencesMapper();
        truckingMapper = new TruckingMapper();
        sourcesMapper = new Truckings_SourcesMapper();
        truckings_destsMapper = new Truckings_DestsMapper();
        vehicleMapper = new VehicleMapper();
    }

    public List<String> getMyLicenses() throws Exception {
        checkIfActiveUserIsDriver();
        List<String> toReturn = new LinkedList<String>();
        List<DLicense> licenses = driverLicencesMapper.getMyLicenses(getActiveUser().getUsername());
        for (DLicense license : licenses) {
            toReturn.add(license.name());
        }
        return toReturn;
    }

    public String printMyTruckings() throws Exception {
        checkIfActiveUserIsDriver();
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverBoard(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printMyTruckingsHistory() throws Exception {
        checkIfActiveUserIsDriver();
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverHistoryTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printMyFutureTruckings() throws Exception {
        checkIfActiveUserIsDriver();
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverFutureTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;

    }

    public boolean addLicense(DLicense license) throws Exception {
        checkIfActiveUserIsDriver();
        return driverLicencesMapper.addLicence(getActiveUser().getUsername(),license);
    }

    public boolean setWeightForTrucking(int truckingId, int weight) throws Exception {
        String registrationPlate = truckingMapper.getLicencePlate(truckingId);
        Vehicle vehicle = vehicleMapper.getVehicle(registrationPlate);
        checkWeight(vehicle,weight);
        checkIfActiveUserIsDriver();
        return truckingMapper.setWeightForTrucking(truckingId,weight);
    }

    private void checkWeight(Vehicle vehicle,int weight) throws Exception {
        if(weight<=0) throw new Exception("Negative weight ? are you drunk?");
        if(vehicle.getMaxWeight()-weight<vehicle.getWeight()) throw new Exception("To heavy boss");
    }

    private void checkIfActiveUserIsDriver() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new IllegalArgumentException("There is no user connected");
        if (getActiveUser().getRole() != Role.driver | !(getActiveUser() instanceof Driver))
            throw new IllegalArgumentException("Oops, you are not a driver");
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
        toReturn += printProducts(trucking.getId());
        if (trucking.getWeight() > 0)
            toReturn += "Total weight: " + trucking.getWeight() + "\n";
        else
            toReturn += "There is no data about the trucking weight\n";
        return toReturn;
    }

    private String printProducts(int TruckingID) {
        return ""; //TODO: need to implement function that get products by truckingID
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

    private String printSitesList(List<Site> sourcesOrDestinations) {
        String toReturn  = "";
        int siteCounter = 1;
        for (Site site : sourcesOrDestinations) {
            toReturn += siteCounter + ". " + site.printSite();
            siteCounter++;
        }
        return toReturn;
    }
}

