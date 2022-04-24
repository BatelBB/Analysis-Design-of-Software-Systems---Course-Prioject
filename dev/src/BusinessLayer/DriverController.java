package BusinessLayer;

import java.util.List;

public class DriverController extends UserController{

    private static DriverController singletonDriverControllerInstance = null;

    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() throws Exception {
        super(null);
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

    public void addLicense(String license) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            ((Driver)getActiveUser()).addLicense(license);
        }
    }

    public void addLicenses(List<String> licenses) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            ((Driver)getActiveUser()).addLicenses(licenses);
        }
    }

    public void setWeightForTrucking(int truckingId, int weight) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            ((Driver)getActiveUser()).updateTotalWeightOfTrucking(truckingId, weight);
        }
    }

    public void removeLicense(String license) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsDriver();
            ((Driver)getActiveUser()).removeLicense(license);
        }
    }

    private void checkIfActiveUserIsDriver() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new Exception("There is no user connected");
        if (getActiveUser().getRole() != Role.driver | !(getActiveUser() instanceof Driver))
            throw new Exception("Oops, you are not a driver");
    }
}

