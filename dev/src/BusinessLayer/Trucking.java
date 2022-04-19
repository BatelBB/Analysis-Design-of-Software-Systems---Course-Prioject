package BusinessLayer;


import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Trucking {
    private LocalDateTime date;
    private Driver driver;
    private List<Site> sources;
    private List<Site> destinations;
    private Truck truck;
    private int weightWithProducts;

    public Trucking(Truck truck, LocalDateTime date, Driver driver, List<Site> sources, List<Site> destinations) throws Exception {
        if (truck == null | date == null | driver == null | sources == null | destinations == null | sources.size() == 0 | destinations.size() == 0)
            throw new Exception("One or more of the data is empty");
        this.truck = truck;
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
            if(dLicense == getTruck().getLisence())
                return true;
        }
        throw new IllegalArgumentException("Oops, the driver does not hold a driver's license that matches the type of vehicle");
    }

    public void addSource(Site site) {
        synchronized (sources) {
            sources.add(site);
        }
    }

    public void addDestination(Site site) {
        synchronized (destinations) {
            destinations.add(site);
        }
    }

    public void printTrucking() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public void printSources() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public void printDestinations() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public void updateWeight(int newWeight) throws Exception {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    public Driver getDriver() { return driver; }

    public Truck getTruck() { return truck; }

    public LocalDateTime getDate() { return date; }

}
