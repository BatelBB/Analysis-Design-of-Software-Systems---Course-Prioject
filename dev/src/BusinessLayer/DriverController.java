package BusinessLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverController {

    private static DriverController singletonDriverControllerInstance = null;

    public static DriverController getInstance() throws Exception {
        if (singletonDriverControllerInstance == null)
            singletonDriverControllerInstance = new DriverController();
        return singletonDriverControllerInstance;
    }

    DriverController() throws Exception { }

    public String printMyTruckings(Driver driver) throws Exception {
            checkIfActiveUserIsDriver(driver);
            return driver.printTruckings();
    }

    public String printMyTruckingsHistory(Driver driver) throws Exception {
            checkIfActiveUserIsDriver(driver);
            return driver.printTruckingsHistory();

    }

    public String printMyFutureTruckings(Driver driver) throws Exception {
            checkIfActiveUserIsDriver(driver);
            return driver.printFutureTruckings();
    }

    public void addLicense(Driver driver,DLicense license) throws Exception {
            checkIfActiveUserIsDriver(driver);
            driver.addLicense(license);

    }

    public void addLicenses(Driver driver,List<DLicense> licenses) throws Exception {
            checkIfActiveUserIsDriver(driver);
        driver.addLicenses(licenses);

    }

    public void setWeightForTrucking(Driver driver,int truckingId, int weight) throws Exception {
        synchronized (driver) {
            checkIfActiveUserIsDriver(driver);
            driver.updateTotalWeightOfTrucking(truckingId, weight);
        }
    }

    public void removeLicense(Driver driver,DLicense license) throws Exception {
            checkIfActiveUserIsDriver(driver);
            driver.removeLicense(license);

    }

    private void checkIfActiveUserIsDriver(Driver driver) throws Exception {
        if (driver == null)
            throw new Exception("There is no user connected");
        if (driver.getRole() != Role.driver )
            throw new Exception("Oops, you are not a driver");
    }
}

