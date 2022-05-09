package groupk.logistics.DataLayer;

import groupk.logistics.business.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleMapper extends myDataBase {
    private VehiclesIDMap vehicleIDMapper = VehiclesIDMap.getInstance();
    public VehicleMapper() throws Exception {
        super("Vehicles");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
    public boolean addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight, String username){
        int n = 0;
        String query = "INSERT INTO Vehicles(registration_plate, model,truck_manager,license, weight,max_weight) VALUES(?,?,?,?,?,?)";

        try(Connection conn = getConnection()){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, registrationPlate);
                prepStat.setString(2, model);
                prepStat.setString(3,username);
                prepStat.setString(4, lisence);
                prepStat.setInt(5, weight);
                prepStat.setInt(6, maxWeight);
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

    public void addVehicle(Vehicle newVehicle) {
        vehicleIDMapper.vehicleMap.put(newVehicle.getRegistationPlate(), newVehicle);

    }

    public Vehicle getVehicle(String registrationPlateOfVehicle, String username) {
        String query = "SELECT * FROM Vehicles" +
                "WHERE registration_plate='"+registrationPlateOfVehicle+"' AND truck_manager = '"+username+"'";
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return new Vehicle(rs.getString(4),rs.getString(1), rs.getString(2),
                        rs.getInt(5),rs.getInt(6),username);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
