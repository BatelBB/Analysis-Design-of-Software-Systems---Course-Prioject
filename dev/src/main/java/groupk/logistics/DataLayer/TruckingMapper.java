package groupk.logistics.DataLayer;

import groupk.logistics.business.Trucking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TruckingMapper extends myDataBase {
    private TruckingsIDMap truckingsIDMap;
    public TruckingMapper() throws Exception {
        super("Truckings");
        truckingsIDMap = TruckingsIDMap.getInstance();
    }

    public boolean addTrucking(int id, String username, String driverUserName, String RegistrationPlate, LocalDateTime date, long hours, long minutes){
        int n = 0;
        String query = "INSERT INTO Truckings(id,registration plate, driver_username,hours, minutes) VALUES(?,?,?,?,?)";

        try(Connection conn = getConnection()){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setInt(1, id);
                prepStat.setString(2, RegistrationPlate);
                prepStat.setString(3, driverUserName);
                prepStat.setLong(4, hours);
                prepStat.setLong(5, minutes);

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
}
