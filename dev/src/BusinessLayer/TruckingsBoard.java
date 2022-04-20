package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingsBoard {

    private LinkedList<Trucking> truckings;

    public TruckingsBoard(TruckManager truckManager) {
        truckings = new LinkedList<Trucking>();
    }

    public synchronized void addTrucking(Trucking trucking) throws Exception {
        if (trucking == null)
            throw new IllegalArgumentException("The trucking is empty");
        if (truckings.size() == 0) {
            truckings.add(trucking);
            return;
        }
        else {
            ListIterator<Trucking> truckingIterator = truckings.listIterator();
            while (truckingIterator.hasNext()) {
                int compareDates = truckingIterator.next().getDate().compareTo(trucking.getDate());
                if (compareDates > 0) {
                    truckingIterator.previous();
                    truckingIterator.add(trucking);
                    return;
                }
            }
        }
    }

    public synchronized void removeTrucking(int truckingId) {
        if (truckingId < 0)
            throw new IllegalArgumentException("Illegal id");
        ListIterator<Trucking> truckingIterator = truckings.listIterator();
        while (truckingIterator.hasNext()) {
            Trucking trucking = truckingIterator.next();
            if(trucking.getId() == truckingId) {
                trucking.getDriver().removeTrucking(truckingId);
                //trucking.getVehicle().removeTrucking(truckingId); TODO
                truckingIterator.remove();
            }
        }
        throw new IllegalArgumentException("There is no trucking in the board with that id");
    }

    public synchronized String printBoard() {
        String toReturn = "             TRUCKINGS BOARD\n\n";
        for (Trucking trucking : truckings) {
            toReturn += trucking.printTrucking();
            toReturn += line;
        }
        return toReturn;
    }

    public synchronized String printDoneTruckings() {
        String toReturn = "             DONE TRUCKINGS\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            toReturn += trucking.printTrucking();
            toReturn += line;
        }
        return toReturn;
    }

    public synchronized String printFutureTruckings() {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0) {
                toReturn += trucking.printTrucking();
                toReturn += line;
            }
        }
        return toReturn;
    }

    public void addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addSources(sources);
    }

    public void addDestinationsToTrucking(int truckingId, List<Site> destinations) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addDestinations(destinations);
    }

    public void addProductsToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addProducts(productForTrucking);
    }

    public void updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateSources(sources);
    }

    public void updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateDestinations(destinations);
    }

    public void moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.moveProducts(productSKU);
    }

    public synchronized void updateVehicleOnTrucking(int truckingId, Vehicle vehicle) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateVehicle(vehicle);
    }

    public synchronized void updateDriverOnTrucking(int truckingId, Driver driver) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateDriver(driver);
    }

    public synchronized void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        int truckingIndex = truckings.indexOf(trucking);
        trucking.updateDate(date);
        truckings.remove(truckingIndex);
        addTrucking(trucking);
    }

    private Trucking findTruckingById(int truckingId) {
        if (truckingId < 0)
            throw new IllegalArgumentException("Illegal id");
        for (Trucking trucking : truckings) {
            if (trucking.getId() == truckingId)
                return trucking;
        }
        throw new IllegalArgumentException("There is no trucking in the board with that id");
    }

    private static String line = "\n____________________________________\n\n";
}