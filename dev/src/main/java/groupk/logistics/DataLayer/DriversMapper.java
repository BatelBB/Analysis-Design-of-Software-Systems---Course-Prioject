package groupk.logistics.DataLayer;


import groupk.logistics.business.Driver;
import groupk.logistics.business.TruckManager;
import groupk.logistics.business.User;
import groupk.logistics.business.Vehicle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DriversMapper extends myDataBase {
    private DriversIDMap driversIDMap;
    public DriversMapper() throws SQLException {
        super("Drivers");
    }
    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
    public boolean addDriver(String lisence, String registrationPlate, String model, int weight, int maxWeight, String username){
        int n = 0;
        String query = "INSERT INTO Driver(registration_plate, model,truck_manager,license, weight,max_weight) VALUES(?,?,?,?,?,?)";

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

    public void addDriver(Driver driver) {
        driversIDMap.driverMap.put(driver.getUsername(), driver);

    }

    public List<String> selectTMDrivers(String TMusername) {
        List DTOList = new LinkedList();
        String query = "SELECT * FROM Drivers Where truck_manager = " + TMusername;
        try (Connection conn = getConnection();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                DTOList.add(ConvertResultSetToDTO(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return DTOList;
    }



    public Driver getDriver(String driverUsername, TruckManager truckManager, List<String> userDetails) {
        String query = "SELECT * FROM Drivers" +
                "WHERE username='"+driverUsername+"' AND truck_manager = '"+truckManager.getUsername()+"'";
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return new Driver(userDetails.get(1),driverUsername,userDetails.get(0));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
