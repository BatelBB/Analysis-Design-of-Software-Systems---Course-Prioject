package groupk.logistics.business;

import java.util.LinkedList;
import java.util.List;

public class Vehicle {
    private DLicense lisence;
    private String registationPlate;
    private String model;
    private int weight;
    private int maxWeight;

    public Vehicle(String lisence, String registationPlate, String model, int weight, int maxWeight) throws Exception {
        this.lisence = castDLicenseFromString(lisence);
        this.registationPlate = registationPlate;
        this.maxWeight = maxWeight;
        this.weight = weight;
        this.model = model;
        checkVehicle();

    }

    public static DLicense castDLicenseFromString(String dLicense) throws Exception {
        if(dLicense.equals("B")) return DLicense.B;
        else if (dLicense.equals("C")) return DLicense.C;
        else if (dLicense.equals("C1")) return DLicense.C1;
        else if (dLicense.equals("C_E")) return DLicense.C_E;
        else throw new IllegalArgumentException("The driver's license \"" + dLicense + "\" doesn't exist");
    }


    public boolean checkVehicle() {
        if (weight <= 0)
            throw new IllegalArgumentException("Weight is positive");
        if (!validateRegistationPlate(registationPlate))
            throw new IllegalArgumentException("Invalid registration plate");
        if (maxWeight <= weight)
            throw new IllegalArgumentException("Max wight is bigger then weight");
        if (!validateModel(model))
            throw new IllegalArgumentException("Invalid model");
        return true;
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

    private boolean validateRegistationPlate(String registationPlate) {
        if (registationPlate == null)
            throw new IllegalArgumentException("Oops, registration plate is empty");
        if (registationPlate.length() != 8 & registationPlate.length() != 7)
            return  false;
        for(int i = 0 ; i < registationPlate.length(); i++) {
            if (!(Character.isDigit(registationPlate.charAt(i))))
                throw new IllegalArgumentException("Registration plate must has only digits");
        }
        return true;
    }

    private boolean validateModel(String model) {
        if (model == null)
            throw new IllegalArgumentException("model is empty");
        if (model.length() <3 | model.length() > 15)
            return  false;
        for (int i = 0; i < model.length(); i++) {
            if (!(Character.isLetter(model.charAt(i)) | Character.isDigit(model.charAt(i)) | model.charAt(i) == ' '))
                return  false;
        }
        return true;
    }

}