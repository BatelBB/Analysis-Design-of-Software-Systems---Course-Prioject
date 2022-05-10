package groupk.logistics.DataLayer;

import groupk.logistics.business.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingDTO {
    public final int id;
    public LocalDateTime date;
    public String driverUsername;
    public String vehicleRegistrationPlate;
    public long hours;
    public long minutes;

    public TruckingDTO(int id, String date, String driverUsername, String vehicleRegistrationPlate, int hours, int minutes) throws Exception {
        this.id = id;
        this.date = convertDate(date, id);
        this.driverUsername = driverUsername;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
        this.hours = hours;
        this.minutes = minutes;
    }

    private LocalDateTime convertDate(String date, int TruckingID) throws Exception{
        LocalDateTime toReturn;
        try {
            toReturn = LocalDateTime.parse(date, TruckingMapper.dateFormat);
        }
        catch (Exception e) {
            throw new Exception("The date value of trucking: " + TruckingID + " is illegal. please fix that.");
        }
        return toReturn;
    }
}
