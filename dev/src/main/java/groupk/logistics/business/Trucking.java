package groupk.logistics.business;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Trucking {
    private final int id;
    private LocalDateTime date;
    private String driverUsername;
    private ConcurrentHashMap<Area, List<Site>> sources;
    private ConcurrentHashMap<Area, List<Site>> destinations;
    private String vehicleRegistrationPlate;
    private int weightWithProducts;
    private List<ProductForTrucking> products;
    private long hours;
    private long minutes;

    public Trucking(int id, String vehicleRegistrationPlate, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<ProductForTrucking> products,long hours, long minutes) throws Exception {
        if (vehicleRegistrationPlate == null | vehicleRegistrationPlate == null | date == null | driverUsername == null | sources == null | destinations == null | sources.size() == 0 | destinations.size() == 0 | products == null | products.size() == 0)
            throw new IllegalArgumentException("One or more of the data is empty");
        this.id = id;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
        this.date = date;
        this.driverUsername = driverUsername;
        this.sources = new ConcurrentHashMap<Area, List<Site>>();
        this.destinations = new ConcurrentHashMap<Area, List<Site>>();
        this.products = products;
        this.weightWithProducts = 0;
        this.hours = hours;
        this.minutes = minutes;
        checkTime();
        checkDate();
        checkSameArea(sources);
        checkSameArea(destinations);
        addSources(sources);
        addDestinations(destinations);
    }

    public static List<ProductForTrucking> productForTruckings(List <Map<String,Integer>> map)
    {
        List<ProductForTrucking> productForTruckings = new LinkedList<>();
        for(int i = 0 ; i < map.size();i++)
        {
            Map<String,Integer> prod = map.get(0);
            if(prod.containsKey("eggs")) productForTruckings.add(new ProductForTrucking(Products.Eggs_4902505139314,prod.get("eggs")));
            if(prod.containsKey("milk")) productForTruckings.add(new ProductForTrucking(Products.Eggs_4902505139314,prod.get("milk")));
            if(prod.containsKey("water")) productForTruckings.add(new ProductForTrucking(Products.Eggs_4902505139314,prod.get("water")));

        }
        return productForTruckings;
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
        toReturn += "Vehicle registration plate: " + vehicleRegistrationPlate + "\n";
        toReturn += "Driver: " + driverUsername + "\n";
        toReturn += printSources();
        toReturn += printDestinations();
        toReturn += printProducts();
        if (weightWithProducts > 0)
            toReturn += "Total weight: " + weightWithProducts + "\n";
        else
            toReturn += "There is no data about the trucking weight\n";
        return toReturn;

    }

    public synchronized void addSources(List<List<String>> sourcesList) throws Exception {
        checkDateForUpdateTrucking();
        addSites(sourcesList, this.sources);
    }

    public synchronized void addDestinations(List<List<String>> destinationsList) throws Exception {
        checkDateForUpdateTrucking();
        addSites(destinationsList, this.destinations);
    }

    public synchronized void addProducts(String pruductName,int quantity) {
        synchronized (products) {
            checkDateForUpdateTrucking();
            products.add(new ProductForTrucking(castFromString(pruductName),quantity));
        }
    }

    private Products castFromString(String sProuduct)
    {
        if(sProuduct.equals("eggs")) return Products.Eggs_4902505139314;
        else if (sProuduct.equals("milk")) return Products.Milk_7290111607400;
        else if (sProuduct.equals("water")) return Products.Water_7290019056966;
        else throw new IllegalArgumentException("wrong license");
    }

    public synchronized void moveProducts(String productSKU) {
        synchronized (products) {
            checkDateForUpdateTrucking();
            ListIterator<ProductForTrucking> truckingIterator = products.listIterator();
            while (truckingIterator.hasNext()) {
                ProductForTrucking productForTrucking = truckingIterator.next();
                if(productForTrucking.product == castFromString(productSKU)) {
                    if (products.size() == 1)
                        throw new IllegalArgumentException("You can't stay empty products list");
                    truckingIterator.remove();
                    return;
                }
            }
            throw new IllegalArgumentException("The product: \"" + productSKU + "\" doesn't exist in the trucking");
        }
    }

    public synchronized void updateSources(List<List<String>> sources) throws Exception {
        checkDateForUpdateTrucking();
        checkSameArea(sources);
        this.sources = new ConcurrentHashMap<Area, List<Site>>();
        addSources(sources);
    }

    public synchronized void updateDestinations(List<List<String>> destinations) throws Exception {
        checkDateForUpdateTrucking();
        checkSameArea(destinations);
        this.destinations = new ConcurrentHashMap<Area, List<Site>>();
        addDestinations(destinations);
    }

    public synchronized void updateDate(LocalDateTime date) {
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

    private void checkDateForUpdateTrucking() {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Sorry, it's too late to update the trucking");
    }

    private boolean checkDate() {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Oops, the date must be in the future");
        return true;
    }

    private boolean checkSameArea(List<List<String>> sites) throws Exception {
        synchronized (sites) {
            if(sites == null | sites.size() == 0)
                throw new IllegalArgumentException("Oops, the sites cannot be empty");
            if (sites.get(0) == null)
                return false;
            Area area = castFromString(sites.get(0)).getArea();
            for (List<String> siteS: sites) {
                Site site = castFromString(siteS);
                if (site == null || site.getArea() != area)
                    return false;
            }
            return true;
        }
    }



    private void addSites(List<List<String>> sites, Map<Area, List<Site>> sourcesOrDestinations) throws Exception {
        for (List<String> site : sites) {
            Site Source = castFromString(site);
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

    private Site castFromString(List<String> detailsOfSite) throws Exception {
        if(!(detailsOfSite.size()==8)) throw new IllegalArgumentException("illegal number of details of site");
        else
            return new Site(detailsOfSite.get(0),detailsOfSite.get(1),detailsOfSite.get(2),detailsOfSite.get(3),
                    Integer.parseInt(detailsOfSite.get(4)),Integer.parseInt(detailsOfSite.get(5)),Integer.parseInt(detailsOfSite.get(6)),
                    detailsOfSite.get(7));

    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public String getDriverUsername() {
        return driverUsername;
    }
}

