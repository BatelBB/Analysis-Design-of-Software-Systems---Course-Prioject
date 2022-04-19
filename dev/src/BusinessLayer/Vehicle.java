package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class Vehicle {
    private DLicense lisence;
    private String registationPlate;
    private String model;
    private int weight;
    private int maxWeight;
    private List<Trucking> futureTruckings;


    public Vehicle(DLicense lisence, String registationPlate, String model, int weight, int maxWeight) {
        this.lisence = lisence;
        this.registationPlate = registationPlate;
        this.maxWeight = maxWeight;
        this.weight = weight;
        this.model = model;
        futureTruckings = new LinkedList<Trucking>();
    }

    public List<Trucking> getFutureTruckings() {
        return futureTruckings;
    }

    public void addTrucking(Trucking trucking)
    {
        futureTruckings.add(trucking);
    }


    public DLicense getLisence() {
        return lisence;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public String getModel() {
        return model;
    }

    public int getWeight() {
        return weight;
    }

    public String getRegistationPlate() {
        return registationPlate;
    }

}
