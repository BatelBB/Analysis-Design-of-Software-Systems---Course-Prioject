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
        checkVehicle();
    }

    private boolean checkVehicle()
    {
        if (!validateWeight(weight)) throw new IllegalArgumentException("Weight is positive");
        if (!validateRegistationPlate(registationPlate)) throw new IllegalArgumentException("Invalid registration plate");
        if (!validateWeightSmallerThanMaxWeight(weight,maxWeight)) throw new IllegalArgumentException("Max wight is bigger then weight");
        if (!validateModel(model)) throw new IllegalArgumentException("Invalid model");
        return true;
    }

    public List<Trucking> getFutureTruckings() {
        return futureTruckings;
    }

    public void addTrucking(Trucking trucking)
    {
        if(futureTruckings.size()==0) futureTruckings.add(trucking);
        else
        {
            for(int index = 0 ; index < futureTruckings.size(); index++)
            {
                if(!(trucking.getDate().isAfter(futureTruckings.get(index).getDate()))) futureTruckings.add(index,trucking);
                return;
            }
        }
        futureTruckings.add(trucking);
    }

    public void removeTrucking(int truckingId)
    {
        for(Trucking trucking : futureTruckings)
        {
            if(truckingId == trucking.getId()) {futureTruckings.remove(trucking); return;}
        }
        throw new IllegalArgumentException("Truck ID not exists");
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

    private boolean validateRegistationPlate(String registationPlate)
    {
        if(registationPlate.length()!=8) return  false;
        for(int i = 0 ; i < registationPlate.length(); i++)
        {
            if(! (Character.isDigit(registationPlate.charAt(i)) )) return  false;
        }
        return true;

    }

    private boolean validateWeight(int weight) { return weight>0; }

    private boolean validateWeightSmallerThanMaxWeight(int weight,int maxWeight) { return maxWeight>weight; }


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