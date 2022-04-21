package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Driver extends User {
    private List<DLicense> licenses;
    private TruckManager truckManager;

    public Driver(String name, String username, String password, TruckManager truckManager) throws Exception {
        super(name, username, password);
        this.role = Role.driver;
        licenses = new LinkedList<DLicense>();
        this.truckManager = truckManager;
    }

    public synchronized void checkTrucking(Trucking trucking) {
        if (trucking.getDate().compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("The date must be in the future");
        if (trucking.getDriver() != this)
            throw new IllegalArgumentException("The driver does not match the driver specified in the form");
        if (licenses.size() == 0)
            throw new IllegalArgumentException("Oops, the driver hasn't entered his driver's license yet");
        if(!licenses.contains(trucking.getVehicle().getLisence()))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license appropriate for the vehicle type");
    }

    public synchronized void addLicense(DLicense dLicense)
    {
        synchronized (licenses) {
            if (!(licenses.contains(dLicense)))
                licenses.add(dLicense);
        }
    }

    public synchronized void addLicenses(List<DLicense> DLicenseList) {
        synchronized (licenses) {
            for (DLicense dLicense : DLicenseList) {
                if (!(licenses.contains(dLicense)))
                    licenses.add(dLicense);
            }
        }
    }

    public synchronized void removeLicense(DLicense dLicense)
    {
        synchronized (licenses) {
            if (licenses.contains(dLicense))
                licenses.remove(dLicense);
        }
    }

    public synchronized void updateTotalWeightOfTrucking(int truckingId, int newWeight) throws Exception {
        truckManager.updateTotalWeight(truckingId, newWeight, this);
    }

    public synchronized String printTruckings() {
        return truckManager.printBoardOfDriver(this.getUsername());
    }

    public synchronized String printTruckingsHistory() {
        return truckManager.printTruckingsHistoryOfDriver(this.getUsername());
    }

    public synchronized String printFutureTruckings() {
        return truckManager.printFutureTruckingsOfDriver(this.getUsername());
    }

    public List<DLicense> getLicenses() {
        return licenses;
    }

    public String getName() {
        return name;
    }

}

