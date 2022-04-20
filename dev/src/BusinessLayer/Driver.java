package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Driver extends User {
    private List<DLicense> licenses;
    private List<Trucking> futureTruckings;


    public Driver(String name, String username, String password) throws Exception {
        super(name, username, password);
        this.role = Role.driver;
        futureTruckings = new LinkedList<Trucking>();
    }

    public void addTrucking(Trucking trucking) throws Exception {
        checkTrucking(trucking);
        synchronized (futureTruckings) {
            if (futureTruckings.size() == 0) {
                futureTruckings.add(trucking);
                return;
            }
            ListIterator<Trucking> truckingIterator = futureTruckings.listIterator();
            while (truckingIterator.hasNext()) {
                int compareDates = truckingIterator.next().getDate().compareTo(trucking.getDate());
                if (compareDates == 0)
                    throw new Exception("Oops, the driver " + trucking.getDriver().getName() + " has another trucking in the same moment: " + trucking.getDate());
                else if (compareDates > 0) {
                    truckingIterator.previous();
                    truckingIterator.add(trucking);
                    return;
                }
            }
        }
    }

    private void checkTrucking(Trucking trucking) {
        if (trucking.getDriver() != this)
            throw new IllegalArgumentException("The driver does not match the driver specified in the form");
        if (licenses.size() == 0)
            throw new IllegalArgumentException("Oops, the driver hasn't entered his driver's license yet");
        if(!licenses.contains(trucking.getVehicle().getLisence()))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license appropriate for the vehicle type");
    }

    public synchronized void removeTrucking(Trucking trucking) {
        synchronized (futureTruckings) {
            if (futureTruckings.contains(trucking))
                futureTruckings.remove(trucking);
        }
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

    public synchronized String printMyFutureTruckings() {
        if (!isLogin())
            throw new IllegalArgumentException("You need to be logged in");
        String toReturn = "";
        synchronized (futureTruckings) {
            for (Trucking trucking : futureTruckings) {
                if (trucking != null) {
                    synchronized (trucking) {
                        toReturn += trucking.printTrucking();
                    }
                }
            }
        }
        return toReturn;
    }

    public synchronized boolean updateTotalWeightOfTrucking(int truckingId, int weight) throws Exception {
        return findTruckingById(truckingId).updateWeight(weight);
    }

    public List<DLicense> getLicenses() {
        return licenses;
    }

    public String getName() {
        return name;
    }

    private boolean checkDriver(Trucking trucking) throws Exception {
        synchronized (futureTruckings) {
            boolean found = false;
            Iterator<Trucking> truckingIterator = futureTruckings.iterator();
            while (!found && truckingIterator.hasNext()) {
                int compareDates = truckingIterator.next().getDate().compareTo(trucking.getDate());
                if (compareDates == 0)
                    throw new Exception("Oops, the driver " + trucking.getDriver().getName() + " has another trucking in the same moment: " + trucking.getDate());
                if (compareDates > 0)
                    found = true;
            }
        }
        return true;
    }

    private Trucking findTruckingById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Illegal id");
        for (Trucking trucking : futureTruckings) {
            if (trucking.getId() == id)
                return trucking;
        }
        throw new IllegalArgumentException("You have no trucking with this number");
    }

}
