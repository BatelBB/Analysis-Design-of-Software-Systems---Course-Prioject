package BusinessLayer;


import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Trucking {
    private int id;
    private LocalDateTime date;
    private Driver driver;
    private List<Site> sources;
    private List<Site> destinations;
    private Vehicle vehicle;
    private int weightWithProducts;

    public Trucking(int id, Vehicle vehicle, LocalDateTime date, Driver driver, List<Site> sources, List<Site> destinations) throws Exception {
        if (vehicle == null | date == null | driver == null | sources == null | destinations == null | sources.size() == 0 | destinations.size() == 0)
            throw new Exception("One or more of the data is empty");
        this.id = id;
        this.vehicle = vehicle;
        this.date = date;
        this.driver = driver;
        this.sources = sources;
        this.destinations = destinations;
        checkDate();
        checkDLicense();
        checkSameArea(sources);//need to send warning if false
        checkSameArea(destinations);//need to send warning if false
    }

    private boolean checkDate() {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("the date must be in the future");
        return true;
    }

    private boolean checkSameArea(List<Site> sites) throws Exception {
        synchronized (sites) {
            if(sites == null | sites.size() == 0)
                throw new IllegalArgumentException("the sites cannot be empty");
            if (sites.get(0) == null)
                return false;
            Area area = sites.get(0).getArea();
            for (Site site: sites) {
                if (site == null || site.getArea() != area)
                    return false;
            }
            return true;
        }
    }

    private boolean checkDLicense() throws Exception {
        boolean found = false;
        for (DLicense dLicense : getDriver().getLicenses()) {
            if(dLicense == getVehicle().getLisence())
                return true;
        }
        throw new IllegalArgumentException("Oops, the driver does not hold a driver's license that matches the type of vehicle");
    }

    public synchronized void addSource(Site site) {
        synchronized (sources) {
            sources.add(site);
        }
    }

    public synchronized void addDestination(Site site) {
        synchronized (destinations) {
            destinations.add(site);
        }
    }

    public String printTrucking() {
        return "not implemented yet";
    }

    public void printSources() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public void printDestinations() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public synchronized void updateWeight(int newWeight) throws Exception {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public Driver getDriver() { return driver; }

    public Vehicle getVehicle() { return vehicle; }

    public LocalDateTime getDate() { return date; }

}
