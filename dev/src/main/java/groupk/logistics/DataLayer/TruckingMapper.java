package groupk.logistics.DataLayer;

import groupk.logistics.business.Trucking;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TruckingMapper extends myDataBase {
    private TruckingsIDMap truckingsIDMap;
    public TruckingMapper() throws Exception {
        super("Truckings");
        truckingsIDMap = TruckingsIDMap.getInstance();
    }

    public boolean addTrucking(int TID, String username, String driverUserName, String RegistrationPlate, LocalDateTime date, long hours, long minutes){
        int n = 0;
        String query = "INSERT INTO Truckings(TID,truck_manager,registration_plate,driver_username,date,hours,minutes) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(finalCurl))
        {
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                String formattedDateTime = date.format(formatter);
                prepStat.setInt(1, TID);
                prepStat.setString(2, username);
                prepStat.setString(3, RegistrationPlate);
                prepStat.setString(4, driverUserName);
                prepStat.setString(5, formattedDateTime);
                prepStat.setLong(6, hours);
                prepStat.setLong(7, minutes);


                n = prepStat.executeUpdate();
            }
            else
                return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return n == 1;
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) {
        return null;
    }

    public void addTrucking(Trucking trucking) {
        truckingsIDMap.truckingMap.put(trucking.getId(),trucking);
    }

    public boolean setWeightForTrucking(int truckingId, int weight) {
        if(truckingsIDMap.truckingMap.containsKey(truckingId))truckingsIDMap.truckingMap.get(truckingId).updateWeight(weight);
        String sql = "UPDATE Trucking SET weight="+weight+" WHERE TID='"+truckingId+"'";
        int n=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return n>0;
    }
}
