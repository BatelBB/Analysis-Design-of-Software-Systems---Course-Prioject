package BusinessLayer;

import java.time.LocalDateTime;
import java.util.List;

public class TruckManager extends User {

    private TruckingsBoard truckingsBoard;

    public TruckManager(String name, String username, String password) throws Exception {
        super(name, username, password);
        this.role = Role.truckingManager;
        this.truckingsBoard = new TruckingsBoard(this);
    }

    public String printTruckings() {
        return truckingsBoard.printBoard();
    }

    public String printFutureTruckings() {
        return truckingsBoard.printFutureTruckings();
    }

    public String printDoneTruckings() {
        return truckingsBoard.printDoneTruckings();
    }

    public List<Vehicle> showVehicle() {
        return null; //TODO: not implemented yet
    }
    public List<Driver> showDrivers() {
        return null; //TODO: not implemented yet
    }

    public void addDriver(String id, String name,List<DLicense> driverLicenses)
    {
        if(!validateId(id)) throw new IllegalArgumentException("Illegal id number");
        if(!validateName(name)) throw new IllegalArgumentException("Illegal driver name");
        //truckingsBoard.addDriver(new Driver(id,name,driverLicenses, licenses)); //TODO: not implemented yet
    }

    private boolean validateId(String id)
    {
        if(id.length()!=9) return  false;
        for(int i = 0 ; i < id.length(); i++)
        {
            if(!(Character.isDigit(id.charAt(i)))) return  false;
        }
        return true;
    }


    private boolean validateName(String name)
    {
        if(name.length()<3 | name.length()>15) return  false;
        for(int i = 0 ; i < name.length(); i++)
        {
            if(!(Character.isLetter(name.charAt(i)))) return  false;
        }
        return true;
    }

    /*
    public void addTrucking(Truck truck, int truckingId, LocalDateTime startTime, LocalDateTime endTime, Driver driver, List<Site> sources, List<Site> destinations) {
        if(!truckingsBoard.getTruckings().contains(truckingId)) throw new IllegalArgumentException("Already have this truckingID number");
        if(validateDriver(driver))throw new IllegalArgumentException("This driver is not exist");
        if(validateTruck(truck))throw new IllegalArgumentException("This truck is not exist");
        if(validateTime(driver,truck,startTime,endTime) != "") throw new IllegalArgumentException(validateTime(driver, truck, startTime,endTime));
        if(!(driver.getLicenses().contains(truck.getLisence()))) throw new IllegalArgumentException("The driver dont have the license");
        if(!validateDestinations(destinations)) throw new IllegalArgumentException("Destinations have different areas");
        if(!validateSources(sources)) throw new IllegalArgumentException("Sources have different areas");
        Trucking trucking = new Trucking(truck,truckingId,startTime,endTime,driver, sources, destinations);
        truckingsBoard.addTrucking(trucking);
        truckingsBoard.addTruckingToDriverList(driver.getId(),trucking);
        truckingsBoard.addTruckingToTruckList(truck.getRegistationPlate(),trucking);

    }
    */

    /*
    private String validateTime(Driver driver, Truck truck, LocalDateTime startTime, LocalDateTime endTime) {
        List<Trucking> truckTruckings = truck.getFutureTruckings();
        List<Trucking> driverTrucking = driver.getFutureTruckings();
        for(Trucking trucking : driverTrucking) {
            LocalDateTime currStartTime = trucking.getStartTime();
            LocalDateTime currEndTime = trucking.getEndTime();
            if(!(currStartTime.isBefore(startTime) & currEndTime.isBefore(startTime))|!(currStartTime.isAfter(endTime) & currStartTime.isAfter(endTime)))
                return "Driver is busy";
        }
        for(Trucking trucking : truckTruckings) {
            LocalDateTime currStartTime = trucking.getStartTime();
            LocalDateTime currEndTime = trucking.getEndTime();
            if(!(currStartTime.isBefore(startTime) & currEndTime.isBefore(startTime))|!(currStartTime.isAfter(endTime) & currStartTime.isAfter(endTime)))
                return "The truck is being used";
        }

        return "";
    }



    private boolean validateDriver(Driver driver)
    {
        for(Driver driverCurr : truckingsBoard.getDrivers()) {
            if(driver.equals(driverCurr)) return  true;
        }
        return false;
    }


    private boolean validateTruck(Truck truck)
    {
        for(Truck truckCurr : truckingsBoard.getTrucks()) {
            if(truckCurr.equals(truck)) return  true;
        }
        return false;    }

     */
    private boolean validateSources (List<Site> sources)
    {
        for (int i = 0 ; i<sources.size()-1;i++) {
            if (sources.get(i).getArea() != sources.get(i + 1).getArea()) return false;
        }
        return true;

    }

    private boolean validateDestinations (List<Site> destinations) {
        for (int i = 0; i < destinations.size() - 1; i++) {
            if (destinations.get(i).getArea() != destinations.get(i + 1).getArea()) return false;
        }
        return true;
    }


}