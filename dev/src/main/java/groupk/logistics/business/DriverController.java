package groupk.logistics.business;

import groupk.logistics.DataLayer.DriverLicensesMapper;
import groupk.logistics.DataLayer.TruckingMapper;

import java.util.List;

public class DriverController extends UserController{

    private static DriverController singletonDriverControllerInstance = null;
    private DriverLicensesMapper driverLicensesMapper;
    private TruckingMapper truckingMapper;



    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() throws Exception {
        super(null);
        driverLicensesMapper = new DriverLicensesMapper();
        truckingMapper = new TruckingMapper();
    }

    public List<String> getMyLicenses() throws Exception {
        return driverLicensesMapper.getMyLicenses(getActiveUser().getUsername());
    }

    public String printMyTruckings() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            return ((Driver)getActiveUser()).printTruckings();
        }
    }

    public String printMyTruckingsHistory() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            return ((Driver)getActiveUser()).printTruckingsHistory();
        }
    }

    public String printMyFutureTruckings() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            return ((Driver)getActiveUser()).printFutureTruckings();
        }
    }

    public boolean addLicense(String license) throws Exception {
        return driverLicensesMapper.addLicence(getActiveUser().getUsername(),license);
    }

    public void addLicenses(List<String> licenses) throws Exception {
       for(String licence:licenses) addLicense(licence);
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

