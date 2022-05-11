package groupk.logistics.business;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TruckingsBoard {

    private LinkedList<Trucking> truckings;

    public TruckingsBoard() {
        truckings = new LinkedList<Trucking>();
    }

    public synchronized void addTrucking(Trucking trucking) {
        if (trucking == null)
            throw new IllegalArgumentException("The trucking is empty");
        if (truckings.size() == 0) {
            truckings.add(trucking);
            return;
        }
        else {
            LocalDateTime startTruck = trucking.getDate();
            LocalDateTime endTruck = trucking.finalDate();
            ListIterator<Trucking> truckingListIterator = truckings.listIterator();
            while (truckingListIterator.hasNext()) {
                Trucking currentTrucking = truckingListIterator.next();
                LocalDateTime startCurr = currentTrucking.getDate();
                LocalDateTime endCurr = currentTrucking.finalDate();
                if (startTruck.isAfter(endCurr)) {
                    insertTrucking(trucking);
                    return;
                }
                if (endTruck.isAfter(startCurr)) {
                    checkAvailibility(currentTrucking.getVehicleRegistrationPlate(),trucking.getVehicleRegistrationPlate(),
                            currentTrucking.getDriverUsername(),trucking.getDriverUsername());
                }
            }
            insertTrucking(trucking);
        }
    }

    private void insertTrucking(Trucking trucking) {
        if (truckings.size() == 0) {
            truckings.add(trucking);
            return;
        }
        ListIterator<Trucking> truckingListIterator = truckings.listIterator();
        while (truckingListIterator.hasNext()) {
            if(trucking.getDate().compareTo(truckingListIterator.next().getDate()) >= 0) {
                truckingListIterator.previous();
                truckingListIterator.add(trucking);
                return;
            }
        }
        truckings.add(trucking);
    }

    public boolean checkAvailibility(String registrationPlate1, String registrationPlate2,String driverUserName1,String driverUserName2) {
        if (registrationPlate1 == registrationPlate2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same vehicle");
        if (driverUserName1 == driverUserName2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same driver");
        return true;
    }

    public synchronized void removeTrucking(int truckingId) {
        if (truckingId < 0)
            throw new IllegalArgumentException("Illegal id");
        boolean found = false;
        for(Trucking trucking : truckings)
        {
            if(trucking.getId() == truckingId) {
                truckings.remove();
                found = true;
            }
        }
        if(!found)throw new IllegalArgumentException("There is no trucking in the board with that id");
    }

    public synchronized String printBoard() {
        String toReturn = "TRUCKINGS BOARD\n\n";
        for (Trucking trucking : truckings) {
            toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printTruckingsHistory() {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printFutureTruckings() {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        boolean found = false; //saves time of checking everytime if is it in future, after first future trucking appear.
        for (Trucking trucking : truckings) {
            if (found | trucking.getDate().compareTo(LocalDateTime.now()) > 0) {
                found = true;
                toReturn += trucking.printTrucking() + line;
            }
        }
        return toReturn;
    }

    public synchronized String printBoardOfDriver(String username) {
        String toReturn = "TRUCKINGS BOARD\n\n";
        for (Trucking trucking : truckings) {
            if(trucking.getDriverUsername() == username)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printTruckingsHistoryOfDriver(String username) {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            else if(trucking.getDriverUsername() == username)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printFutureTruckingsOfDriver(String username) {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        boolean found = false; //saves time of checking everytime if is it in future, after first future trucking appear.
        for (Trucking trucking : truckings) {
            if (found | trucking.getDate().compareTo(LocalDateTime.now()) > 0) {
                found = true;
                if(trucking.getDriverUsername() == username)
                    toReturn += trucking.printTrucking() + line;
            }
        }
        return toReturn;
    }

    public synchronized String printBoardOfVehicle(String registrationPlate) {
        String toReturn = "             TRUCKINGS BOARD\n\n";
        for (Trucking trucking : truckings) {
            if(trucking.getVehicleRegistrationPlate() == registrationPlate)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printTruckingsHistoryOfVehicle(String registrationPlate) {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            else if(trucking.getVehicleRegistrationPlate() == registrationPlate)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printFutureTruckingsOfVehicle(String registrationPlate) {
        /*
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        boolean found = false; //saves time of checking everytime if is it in future, after first future trucking appear.
        for (Trucking trucking : truckings) {
            if (found | trucking.getDate().compareTo(LocalDateTime.now()) > 0) {
                found = true;
                if(trucking. == registrationPlate)
                    toReturn += trucking.printTrucking() + line;
            }
        }
        return toReturn;

         */
        return null;
    }

    public void addSourcesToTrucking(int truckingId, List<List<String>> sources) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addSources(sources);
    }

    public void addDestinationsToTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addDestinations(destinations);
    }

    public void addProductsToTrucking(int truckingId,String pruductName,int quantity) {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addProducts(pruductName,quantity);
    }

    public void updateSourcesOnTrucking(int truckingId, List<List<String>> sources) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateSources(sources);
    }

    public void updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateDestinations(destinations);
    }

    public void moveProductsToTrucking(int truckingId, String productSKU) {
        Trucking trucking = findTruckingById(truckingId);
        trucking.moveProducts(productSKU);
    }

    public synchronized void updateVehicleOnTrucking(int truckingId, Vehicle vehicle) {
    }

    public synchronized void updateDriverOnTrucking(int truckingId, Driver driver) {
    }

    public synchronized void updateDateOnTrucking(int truckingId, LocalDateTime date) {
        Trucking trucking = findTruckingById(truckingId);
        int truckingIndex = truckings.indexOf(trucking);
        trucking.updateDate(date);
        truckings.remove(truckingIndex);
        addTrucking(trucking);
    }

    public synchronized void updateTotalWeightOfTrucking(int truckingId, int newWeight, String driverUsername){

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
