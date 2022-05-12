package groupk.logistics.DataLayer;

import groupk.logistics.business.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingDTO {
    private final int id;
    private String truckManager;
    private LocalDateTime date;
    private String driverUsername;
    private String vehicleRegistrationPlate;
    private long hours;
    private long minutes;
    private int weight;

    public TruckingDTO(int id, String date,String truckManager, String driverUsername, String vehicleRegistrationPlate, long hours, long minutes, int weight) throws Exception {
        this.id = id;
        this.date = convertDate(date, id);
        this.truckManager = truckManager;
        this.driverUsername = driverUsername;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
        this.hours = hours;
        this.minutes = minutes;
        this.weight = weight;
    }

    public TruckingDTO(int id, LocalDateTime date, String truckManager, String driverUsername, String vehicleRegistrationPlate, long hours, long minutes, int weight) throws Exception {
        this.id = id;
        this.date = date;
        this.truckManager = truckManager;
        this.driverUsername = driverUsername;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
        this.hours = hours;
        this.minutes = minutes;
        this.weight = weight;
    }
    private LocalDateTime convertDate(String date, int TruckingID) throws Exception{
        LocalDateTime toReturn;
        try {
            date = date.substring(0,date.length()-3);
            toReturn = LocalDateTime.parse(date, TruckingMapper.dateFormat);
        }
        catch (Exception e) {
            throw new Exception("The date value of trucking: " + TruckingID + " is illegal. please fix that.");
        }
        return toReturn;
    }

    public int getId() {
        return id;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public String getTruckManager() {
        return truckManager;
    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public LocalDateTime getDate() {
        return date.plusHours(0);
    }

    public int getWeight() {
        return weight;
    }
}