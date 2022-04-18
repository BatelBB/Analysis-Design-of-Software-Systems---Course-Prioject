package BusinessLayer;

import java.util.List;

public class TruckingsBoard {

    private List<Driver> drivers ;
    private List<Trucking> truckings;
    private List<Truck> trucks;

    public List<Trucking> getTruckings() {
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

    public void addTrucking(Trucking trucking) {
        truckings.add(trucking);
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
    }
}
