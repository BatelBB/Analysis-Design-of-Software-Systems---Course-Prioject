package groupk.logistics.business;

import groupk.logistics.DataLayer.DriverLicencesMapper;
import groupk.logistics.DataLayer.TruckingMapper;

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

    public String printMyTruckings() throws Exception {
        return "need to implement";
    }

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

