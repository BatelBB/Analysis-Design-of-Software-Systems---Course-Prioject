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

    private DriverController()
    {
        UserController.getInstance();
    }

    public String printMyFutureTruckings() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsDriver();
            return ((Driver)activeUser).printMyFutureTruckings();
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

    public void setWeightForTrucking(int weight) {
        //TODO
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
