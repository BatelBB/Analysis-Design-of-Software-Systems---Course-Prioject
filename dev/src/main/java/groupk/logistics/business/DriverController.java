package groupk.logistics.business;

import groupk.logistics.DataLayer.DriverLicencesMapper;
import groupk.logistics.DataLayer.TruckingDTO;
import groupk.logistics.DataLayer.TruckingMapper;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class DriverController extends UserController{

    private static DriverController singletonDriverControllerInstance = null;
    private DriverLicencesMapper driverLicencesMapper;
    private TruckingMapper truckingMapper;



    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() throws Exception {
        super(null);
        driverLicencesMapper = new DriverLicencesMapper();
        truckingMapper = new TruckingMapper();
    }

    public List<String> getMyLicenses() throws Exception {
        List<String> toReturn = new LinkedList<String>();
        List<DLicense> licenses = driverLicencesMapper.getMyLicenses(getActiveUser().getUsername());
        for (DLicense license : licenses) {
            toReturn.add(license.name());
        }
        return toReturn;
    }

//    public String printMyTruckings() throws Exception {
//        String toReturn = "TRUCKINGS BOARD\n\n";
//        List<TruckingDTO> truckings = truckingMapper.getDriverBoard(getActiveUser().getUsername());
//        if (truckings.size() == 0 | truckings == null) {
//            toReturn += "[empty]";
//            return toReturn;
//        }
//        for (TruckingDTO trucking : truckings)
//            toReturn += printTrucking(trucking);
//        return toReturn;
//    }

//    private String printTrucking(TruckingDTO trucking) throws Exception {
//        String toReturn = "TRUCKING - " + trucking.getId() + "\n\n";
//        toReturn += "TRUCKING DETAILS:\n";
//        toReturn += "Date: " + trucking.getDate().getDayOfMonth() + "/" + trucking.getDate().getMonthValue() + "/" + trucking.getDate().getYear() + "\n";
//        toReturn += "Start Hour: " + printHour(trucking.getDate()) + "\n";
//        toReturn += "End Hour: " + printHour(trucking.getDate().plusHours(trucking.getHours()).plusMinutes(trucking.getMinutes())) + "\n";
//        toReturn += "Vehicle registration plate: " + trucking.getVehicleRegistrationPlate() + "\n";
//        toReturn += "Driver: " + trucking.getDriverUsername() + "\n";
//        toReturn += printSources(trucking.getId());
//        toReturn += printDestinations(trucking.getId());
//        toReturn += printProducts(trucking.getId());
//        if (trucking.getWeight() > 0)
//            toReturn += "Total weight: " + trucking.getWeight() + "\n";
//        else
//            toReturn += "There is no data about the trucking weight\n";
//        return toReturn;
//    }
//
//
//    public synchronized String printProducts(int TruckingID) {
//        return ""; //TODO: need to implement function that get products by truckingID
//    }
//
//    private String printHour(LocalDateTime date) {
//        String toReturn = "";
//        if(date.getHour()<10)
//            toReturn += "0" + date.getHour() + ":";
//        else
//            toReturn += date.getHour() + ":";
//        if(date.getMinute()<10)
//            toReturn += "0" + date.getMinute();
//        else
//            toReturn += date.getHour();
//        return toReturn;
//    }
////
////    private String printSources(int TruckingID) throws Exception {
////        String toReturn = "\nSOURCE DETAILS:\n";
////        toReturn += printSitesList(sourcesMapper.getSourcesByTruckingId(TruckingID));
////        return toReturn;
////    }
////
////    public synchronized String printDestinations(int TruckingID) throws Exception {
////        String toReturn = "\nDESTINATION DETAILS:\n";
////        toReturn += printSitesList(truckings_destsMapper.getDestinationsByTruckingId(TruckingID));
////        return toReturn;
////    }
//
//    private String printSitesList(List<Site> sourcesOrDestinations) {
//        String toReturn  = "";
//        int siteCounter = 1;
//        for (Site site : sourcesOrDestinations) {
//            toReturn += siteCounter + ". " + site.printSite();
//            siteCounter++;
//        }
//        return toReturn;
//    }

    public String printMyTruckingsHistory() throws Exception {
        return "need to implement";

    }

    public String printMyFutureTruckings() throws Exception {
        return "need to implement";

    }

    public boolean addLicense(DLicense license) throws Exception {
        return driverLicencesMapper.addLicence(getActiveUser().getUsername(),license);
    }

    public boolean setWeightForTrucking(int truckingId, int weight) throws Exception {
        return truckingMapper.setWeightForTrucking(truckingId,weight);
    }

    private void checkIfActiveUserIsDriver() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new IllegalArgumentException("There is no user connected");
        if (getActiveUser().getRole() != Role.driver | !(getActiveUser() instanceof Driver))
            throw new IllegalArgumentException("Oops, you are not a driver");
    }
}

