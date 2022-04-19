package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class TruckingsBoard {

    private List<Trucking> truckings;
    private final TruckingManager truckingManager;
    private int idCounter;

    public TruckingsBoard(TruckingManager truckingManager) {
        this.truckingManager = truckingManager;
        this.idCounter = 1;
    }

    public synchronized void addTrucking(Trucking trucking) throws Exception {
        synchronized (trucking.getDriver()) {
            checkDriver(trucking);
            //add to the trucking list
            //give a id-truck
            //add to driver's future trucking
        }
    }

    private boolean checkDriver(Trucking trucking) throws Exception {
        List<Trucking> futureTruckingsDriver = trucking.getDriver().getFutureTruckings();
        boolean found = false;
        Iterator<Trucking> truckingIterator = futureTruckingsDriver.iterator();
        while (!found && truckingIterator.hasNext()) {
            int compareDates = truckingIterator.next().getDate().compareTo(trucking.getDate());
            if (compareDates == 0)
                throw new Exception("Oops, the driver " + trucking.getDriver().getName() + " has another trucking in the same moment: " + trucking.getDate());
            if (compareDates > 0)
                found = true;
        }
        return true;
    }

    private String printBoard() throws Exception {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    private String printDoneTruckings() throws Exception {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }

    private String printFutureTruckings() throws Exception {
        throw new ExecutionControl.NotImplementedException("Not implemented yet");
    }






    /*public List<Trucking> getTruckings() {
        return truckings;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void  addTruck(Truck truck)
    {
        trucks.add(truck);
    }

    public void  addDriver(Driver driver)
    {
        drivers.add(driver);
    }

    public void addTruckingToDriverList(String driverId,Trucking trucking)
    {
        for(Driver driver : drivers)
        {
            if(driverId == driver.getId() ) driver.addTrucking(trucking);
        }
    }

    public void addTruckingToTruckList(String plateNumber,Trucking trucking)
    {
        for(Truck truck : trucks)
        {
            if(plateNumber == truck.getRegistationPlate() ) truck.addTrucking(trucking);
        }
    }*/
}
