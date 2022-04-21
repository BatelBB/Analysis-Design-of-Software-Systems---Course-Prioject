package BusinessLayer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Vehicle {
    private DLicense lisence;
    private String registrationPlate;
    private String model;
    private int weight;
    private int maxWeight;


    public Vehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) {
        this.lisence = lisence;
        this.registrationPlate = registrationPlate;
        this.maxWeight = maxWeight;
        this.weight = weight;
        this.model = model;
        checkVehicle();
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
        return registrationPlate;
    }

    private boolean validateRegistationPlate(String registationPlate) {
        if(registationPlate.length()!=8) return  false;
        for(int i = 0 ; i < registationPlate.length(); i++) {
            if(! (Character.isDigit(registationPlate.charAt(i)) )) return  false;
        }
        return true;
    }

    private boolean checkVehicle() {
        if (!validateWeight(weight))
            throw new IllegalArgumentException("Weight is positive");
        if (!validateRegistationPlate(registrationPlate))
            throw new IllegalArgumentException("Invalid registration plate");
        if (!validateWeightSmallerThanMaxWeight(weight,maxWeight))
            throw new IllegalArgumentException("Max wight is bigger then weight");
        if (!validateModel(model))
            throw new IllegalArgumentException("Invalid model");
        return true;
    }

    private boolean validateWeight(int weight) {
        return weight > 0;
    }

    private boolean validateWeightSmallerThanMaxWeight(int weight,int maxWeight) {
        return maxWeight > weight;
    }

    private boolean validateModel(String model) {
        if (model.length() < 3 | model.length() > 7)
            return  false;
        for (int i = 0; i < model.length(); i++)  {
            if(!(Character.isLetter(model.charAt(i)) | Character.isDigit(model.charAt(i))))
                return  false;
        }
        return true;
    }

}