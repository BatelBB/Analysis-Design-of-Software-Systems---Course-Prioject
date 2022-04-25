package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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
            int indexToAdd = 0 ;
            for(int i = 0 ; i < truckings.size(); i = i+1)
            {
                Trucking currentTrucking = truckings.get(i);
                LocalDateTime startCurr = currentTrucking.getDate();
                LocalDateTime endCurr = currentTrucking.finalDate();
                LocalDateTime startTruck = trucking.getDate();
                LocalDateTime endTruck = trucking.finalDate();
                boolean before = endTruck.compareTo(startCurr)<0;
                boolean bewteenButStartBefore = startTruck.compareTo(startCurr)<0 & endTruck.compareTo(startCurr)>0;
                boolean bewteenButStartAfter = startTruck.compareTo(startCurr)>0 & startTruck.compareTo(endCurr)<0;
                if(before) {truckings.add(indexToAdd,trucking); return;}
                else if (bewteenButStartAfter)
                {
                    boolean checkAvailibity = checkAvailibility(currentTrucking.getVehicle().getRegistationPlate(),trucking.getVehicle().getRegistationPlate(),
                            currentTrucking.getDriver().getUsername(),trucking.getDriver().getUsername());
                }
                else if(bewteenButStartBefore)
                {
                    boolean checkAvailibity = checkAvailibility(currentTrucking.getVehicle().getRegistationPlate(),trucking.getVehicle().getRegistationPlate(),
                            currentTrucking.getDriver().getUsername(),trucking.getDriver().getUsername());
                    indexToAdd=indexToAdd+1;
                }
            }
            truckings.add(indexToAdd,trucking);
        }
    }

    public boolean checkAvailibility(String registrationPlate1, String registrationPlate2,String driverUserName1,String driverUserName2)
    {
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
            if(trucking.getDriver().getUsername() == username)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printTruckingsHistoryOfDriver(String username) {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            else if(trucking.getDriver().getUsername() == username)
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
                if(trucking.getDriver().getUsername() == username)
                    toReturn += trucking.printTrucking() + line;
            }
        }
        return toReturn;
    }

    public synchronized String printBoardOfVehicle(String registrationPlate) {
        String toReturn = "             TRUCKINGS BOARD\n\n";
        for (Trucking trucking : truckings) {
            if(trucking.getVehicle().getRegistationPlate() == registrationPlate)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printTruckingsHistoryOfVehicle(String registrationPlate) {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        for (Trucking trucking : truckings) {
            if (trucking.getDate().compareTo(LocalDateTime.now()) > 0)
                return toReturn;
            else if(trucking.getVehicle().getRegistationPlate() == registrationPlate)
                toReturn += trucking.printTrucking() + line;
        }
        return toReturn;
    }

    public synchronized String printFutureTruckingsOfVehicle(String registrationPlate) {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        boolean found = false; //saves time of checking everytime if is it in future, after first future trucking appear.
        for (Trucking trucking : truckings) {
            if (found | trucking.getDate().compareTo(LocalDateTime.now()) > 0) {
                found = true;
                if(trucking.getVehicle().getRegistationPlate() == registrationPlate)
                    toReturn += trucking.printTrucking() + line;
            }
        }
        return toReturn;
    }

    public void addSourcesToTrucking(int truckingId, List<List<String>> sources) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addSources(sources);
    }

    public void addDestinationsToTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        Trucking trucking = findTruckingById(truckingId);
        trucking.addDestinations(destinations);
    }

    public void addProductsToTrucking(int truckingId,String pruductName,int quantity) throws Exception {
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

    public void moveProductsToTrucking(int truckingId, String productSKU) throws Exception {
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

    public synchronized void updateTotalWeightOfTrucking(int truckingId, int newWeight, String driverUsername) throws Exception{
        if(driverUsername == null)
            throw new IllegalArgumentException("Driver's username is empty");
        Trucking trucking = findTruckingById(truckingId);
        trucking.updateWeight(newWeight);
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
