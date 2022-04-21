package BusinessLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverController extends UserController{

    private static DriverController singletonDriverControllerInstance = null;

    public static DriverController getInstance() {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    private DriverController() {}

    public String printMyTruckings() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            return ((Driver)activeUser).printTruckings();
        }
    }

    public String printMyTruckingsHistory() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            return ((Driver)activeUser).printTruckingsHistory();
        }
    }

    public String printMyFutureTruckings() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            return ((Driver)activeUser).printFutureTruckings();
        }
    }

    public void addLicense(DLicense license) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            ((Driver)activeUser).addLicense(license);
        }
    }

    public void addLicenses(List<DLicense> licenses) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            ((Driver)activeUser).addLicenses(licenses);
        }
    }

    public void setWeightForTrucking(int truckingId, int weight) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            ((Driver)activeUser).updateTotalWeightOfTrucking(truckingId, weight);
        }
    }

    public void removeLicense(DLicense license) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            ((Driver)activeUser).removeLicense(license);
        }
    }

    private void checkIfActiveUserIsDriver() throws Exception {
        if (activeUser == null)
            throw new Exception("There is no user connected");
        if (activeUser.getRole() != Role.driver | !(activeUser instanceof Driver))
            throw new Exception("Oops, you are not a driver");
    }
}
