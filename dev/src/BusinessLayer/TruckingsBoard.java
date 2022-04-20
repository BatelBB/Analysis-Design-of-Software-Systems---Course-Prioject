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
    private final TruckManager truckManager;

    public TruckingsBoard(TruckManager truckManager) {
        this.truckManager = truckManager;
        truckings = new LinkedList<Trucking>();
    }

    public synchronized void addTrucking(Trucking trucking) throws Exception {
        if (trucking == null)
            throw new IllegalArgumentException("The trucking s empty");
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

    public void addSourcesToTrucking(int id, List<Site> sources) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.addSources(sources);
    }

    public void addDestinationsToTrucking(int id, List<Site> destinations) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.addDestinations(destinations);
    }

    public void addProductsToTrucking(int id, ProductForTrucking productForTrucking) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.addProducts(productForTrucking);
    }

    public void updateSourcesOnTrucking(int id, List<Site> sources) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.updateSources(sources);
    }

    public void updateDestinationsOnTrucking(int id, List<Site> destinations) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.updateDestinations(destinations);
    }

    public void moveProductsToTrucking(int id, Products productSKU) throws Exception {
        Trucking trucking = findTruckingById(id);
        trucking.moveProducts(productSKU);
    }

    //TODO: add all the methods from Trucking

    private Trucking findTruckingById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Illegal id");
        for (Trucking trucking : truckings) {
            if (trucking.getId() == id)
                return trucking;
        }
        throw new IllegalArgumentException("There is no trucking in the board with that id");
    }

    private static String line = "\n____________________________________\n\n";
}