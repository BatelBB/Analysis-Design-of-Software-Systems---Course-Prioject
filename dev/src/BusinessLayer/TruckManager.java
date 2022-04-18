package BusinessLayer;

import java.time.LocalDateTime;
import java.util.List;

public class TruckManager extends User {

    private TruckingsBoard truckingsBoard;

    public TruckManager(String id, String name, TruckingsBoard truckingsBoard) {
        this.id = id;
        this.name = name;
        this.truckingsBoard = truckingsBoard;
        account = null;
    }

    public List<Trucking> showTruckings() {
        return  truckingsBoard.getTruckings();
    }

    public List<Truck> showTrucks() {
        return  truckingsBoard.getTrucks();
    }
    public List<Driver> showDrivers() {
        return  truckingsBoard.getDrivers();
    }

    public void addDriver(String id, String name,List<DLicense> driverLicenses)
    {
        if(!validateId(id)) throw new IllegalArgumentException("Illegal id number");
        if(!validateName(name)) throw new IllegalArgumentException("Illegal driver name");
        truckingsBoard.addDriver(new Driver(id,name,driverLicenses));
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

    public void addTruck(DLicense lisence, String registationPlate, String model, int weight, int maxWeight) {
        if(!validateMaxWeight(maxWeight)) throw new IllegalArgumentException("Max weight is positive");
        if(!validateModel(model)) throw new IllegalArgumentException("Invalid model");
        if(!validateRegistationPlate(registationPlate))  throw new IllegalArgumentException("Invalid registration plate");
        if(!validateWeight(weight))throw new IllegalArgumentException("Weight is positive");
        if(!validateWeightSmallerThanMaxWeight(weight,maxWeight))throw new IllegalArgumentException("Max weight is bigger then weight");
        truckingsBoard.addTruck(new Truck( lisence,  registationPlate,  model,  weight,  maxWeight));
    }


    private boolean validateRegistationPlate(String registationPlate)
    {
        if(registationPlate.length()!=8) return  false;
        for(int i = 0 ; i < registationPlate.length(); i++)
        {
            if(! (Character.isDigit(registationPlate.charAt(i)) )) return  false;
        }
        return true;

    }

    private boolean validateWeight(int weight)
    {
        return weight>0;
    }

    private boolean validateWeightSmallerThanMaxWeight(int weight,int maxWeight)
    {
        return maxWeight>weight;
    }

    private boolean validateMaxWeight(int maxWeight)
    {
        return maxWeight>0;
    }
    private boolean validateModel(String model)
    {
        if(model.length()<3 | model.length()>7) return  false;
        for(int i = 0 ; i < model.length(); i++)
        {
            if(!(Character.isLetter(model.charAt(i))| Character.isDigit(model.charAt(i)))) return  false;
        }
        return true;
    }
}



