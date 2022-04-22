package BusinessLayer;


import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Trucking {
    private final int id;
    private LocalDateTime date;
    private Driver driver;
    private ConcurrentHashMap<Area, List<Site>> sources;
    private ConcurrentHashMap<Area, List<Site>> destinations;
    private Vehicle vehicle;
    private int weightWithProducts;
    private List<ProductForTrucking> products;
    private long hours;
    private long minutes;

    public Trucking(int id, Vehicle vehicle, LocalDateTime date, Driver driver, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hours, long minutes) throws Exception {
        if (vehicle == null | date == null | driver == null | sources == null | destinations == null | sources.size() == 0 | destinations.size() == 0 | products == null | products.size() == 0)
            throw new Exception("One or more of the data is empty");
        this.id = id;
        this.vehicle = vehicle;
        this.date = date;
        this.driver = driver;
        this.sources = new ConcurrentHashMap<Area, List<Site>>();
        this.destinations = new ConcurrentHashMap<Area, List<Site>>();
        this.products = products;
        this.weightWithProducts = 0;
        this.hours = hours;
        this.minutes = minutes;
        checkTime();
        checkDate();
        checkDLicense();
        checkSameArea(sources);//TODO: need to send warning if false
        checkSameArea(destinations);//TODO: need to send warning if false
        addSources(sources);
        addDestinations(destinations);
    }

    private void checkTime() {
        if (hours<0 | hours >6)
            throw new IllegalArgumentException("To long or minus hour ? no no no");
        if (minutes<0 | minutes >59)
            throw new IllegalArgumentException("Minute is between 0 to 59 dont mess with me clown");
    }


    public LocalDateTime finalDate() {

        LocalDateTime dateEnd =  date.plusHours(hours);
        dateEnd.plusMinutes(minutes);
        return dateEnd;
    }


    public String printTrucking() {
        String toReturn = "TRUCKING - " + id + "\n\n";
        toReturn += "TRUCKING DETAILS:\n";
        toReturn += "Date: " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + "\n";
        toReturn += "Hour: " + date.getHour() + ":" + date.getMinute() + "\n";
        toReturn += "Vehicle registration plate: " + vehicle.getRegistationPlate() + "\n";
        toReturn += "Driver: " + driver.getName() + "\n";
        toReturn += printSources();
        toReturn += printDestinations();
        toReturn += printProducts();
        if (weightWithProducts > 0)
            toReturn += "Total weight: " + weightWithProducts + "\n";
        else
            toReturn += "There is no data about the trucking weight\n";
        return toReturn;

    }

    public synchronized boolean updateWeight(int newWeight) throws Exception {
        if (newWeight <= 0)
            throw new IllegalArgumentException("The weight must be positive number");
        if (newWeight <= vehicle.getWeight())
            throw new IllegalArgumentException("Oops, the weight with the product is lower than the vehicle weight alone");
        if (newWeight + vehicle.getWeight() > vehicle.getMaxWeight())
            throw new IllegalArgumentException("Oops, the weight with the products is higher than the maximum allowable weight");
        weightWithProducts = newWeight;
        return true;
    }

    public synchronized void addSources(List<Site> sourcesList) throws Exception {
        checkDateForUpdateTrucking();
        addSites(sourcesList, this.sources);
    }

    public synchronized void addDestinations(List<Site> destinationsList) throws Exception {
        checkDateForUpdateTrucking();
        addSites(destinationsList, this.destinations);
    }

    public synchronized void addProducts(ProductForTrucking productForTrucking) throws Exception {
        synchronized (products) {
            checkDateForUpdateTrucking();
            products.add(productForTrucking);
        }
    }

    public synchronized void moveProducts(Products productSKU) throws Exception {
        synchronized (products) {
            checkDateForUpdateTrucking();
            ListIterator<ProductForTrucking> truckingIterator = products.listIterator();
            while (truckingIterator.hasNext()) {
                ProductForTrucking productForTrucking = truckingIterator.next();
                if(productForTrucking.product == productSKU) {
                    truckingIterator.remove();
                    return;
                }
            }
            throw new IllegalArgumentException("The product: \"" + productSKU + "\" doesn't exist in the trucking");
        }
    }

    public synchronized void updateSources(List<Site> sources) throws Exception {
        checkDateForUpdateTrucking();
        checkSameArea(sources);
        this.sources = new ConcurrentHashMap<Area, List<Site>>();
        addSources(sources);
    }

    public synchronized void updateDestinations(List<Site> destinations) throws Exception {
        checkDateForUpdateTrucking();
        checkSameArea(destinations);
        this.destinations = new ConcurrentHashMap<Area, List<Site>>();
        addDestinations(destinations);
    }

    public synchronized void updateVehicle(Vehicle vehicle) throws Exception {
        checkDateForUpdateTrucking();
        if (vehicle == null)
            throw new IllegalArgumentException("The vehicle is empty");
        if (!(driver.getLicenses().contains(vehicle.getLisence())))
            throw new IllegalArgumentException("The driver does not have a suitable driver's license for this vehicle");
        this.vehicle = vehicle;
    }

    public synchronized void updateDriver(Driver driver) throws Exception {
        checkDateForUpdateTrucking();
        if (driver == null)
            throw new IllegalArgumentException("The driver is empty");
        if (!(driver.getLicenses().contains(vehicle.getLisence())))
            throw new IllegalArgumentException("That driver does not have a suitable driver's license for the vehicle");
        this.driver = driver;
    }

    public synchronized void updateDate(LocalDateTime date) throws Exception {
        checkDateForUpdateTrucking();
        if (date == null)
            throw new IllegalArgumentException("The data is empty");
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("The date must be in the future");
        this.date = date;
    }

    public synchronized String printSources() {
        String toReturn = "\nSOURCE DETAILS:\n";
        toReturn += printSitesList(this.sources);
        return toReturn;
    }

    public synchronized String printDestinations() {
        String toReturn = "\nDESTINATION DETAILS:\n";
        toReturn += printSitesList(this.destinations);
        return toReturn;
    }

    public synchronized String printProducts() {
        String toReturn = "\nPRODUCTS:\n";
        for(ProductForTrucking product : products) {
            toReturn += product.printProductForTrucking() + "\n";
        }
        return toReturn;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    private String printSitesList(Map<Area, List<Site>> sourcesOrDestinations) {
        String toReturn  = "";
        int siteCounter = 1;
        for (Area area : sourcesOrDestinations.keySet()) {
            List<Site> sites = sourcesOrDestinations.get(area);
            for (Site site : sites) {
                toReturn += siteCounter + ". " + site.printSite();
                siteCounter++;
            }
        }
        return toReturn;
    }

    private void checkDateForUpdateTrucking() throws Exception{
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Sorry, it's too late to update the trucking");
    }

    private boolean checkDate() {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Oops, the date must be in the future");
        return true;
    }

    private boolean checkSameArea(List<Site> sites) throws Exception {
        synchronized (sites) {
            if(sites == null | sites.size() == 0)
                throw new IllegalArgumentException("Oops, the sites cannot be empty");
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
        for (DLicense dLicense : getDriver().getLicenses()) {
            if(dLicense == getVehicle().getLisence())
                return true;
        }
        throw new IllegalArgumentException("Oops, the driver does not hold a driver's license that matches the type of vehicle");
    }

    private void addSites(List<Site> sites, Map<Area, List<Site>> sourcesOrDestinations) {
        for (int index = 0; index < sites.size(); index++) {
            Site Source = sites.get(index);
            if(Source == null | Source.getArea() == null)
                throw new IllegalArgumentException("illegal details of site");
            if(sourcesOrDestinations.containsKey(Source.getArea()))
                sourcesOrDestinations.get(Source.getArea()).add(Source);
            else {
                List<Site> sourcesList = new LinkedList<Site>();
                sourcesList.add(Source);
                sourcesOrDestinations.put(Source.getArea(), sourcesList);
            }
        }
    }
}

