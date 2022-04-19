package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingsBoard {

    private ConcurrentHashMap<Integer, Trucking> truckings;
    private final TruckManager truckManager;
    private int idCounter;

    public TruckingsBoard(TruckManager truckManager) {
        this.truckManager = truckManager;
        this.idCounter = 1;
    }

    public synchronized void addTrucking(Trucking trucking) throws Exception {
        synchronized (trucking.getDriver()) {
            truckings.put(new Integer(idCounter), trucking);
            idCounter++;
            //give a id-truck
            //add to driver's future trucking
        }
    }

    public String printBoard() {
        return "not implemented yet";
    }

    public String printDoneTruckings() {
        return "not implemented yet";
    }

    public String printFutureTruckings() {
        return "not implemented yet";
    }

}