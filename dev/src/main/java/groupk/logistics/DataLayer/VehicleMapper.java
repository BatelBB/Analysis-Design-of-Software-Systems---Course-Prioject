package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleMapper {
    private VehiclesIDMap vehicleIDMapper = VehiclesIDMap.getInstance();
    public VehicleMapper() throws Exception {
    }

    String ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
    public boolean addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        int n = 0;
        String query = "INSERT INTO Vehicles(registration_plate, model,license, weight,max_weight) VALUES(?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(myDataBase.finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, registrationPlate);
                prepStat.setString(2, model);
                prepStat.setString(3, lisence);
                prepStat.setInt(4, weight);
                prepStat.setInt(5, maxWeight);
                n = prepStat.executeUpdate();
            }
            else
                return false;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return n == 1;
    }

    public void addVehicle(VehicleDTO newVehicle) {
        vehicleIDMapper.vehicleMap.put(newVehicle.getRegistationPlate(), newVehicle);
    }

    public String getLicense(String registrationPlateOfVehicle) throws Exception{
        if(vehicleIDMapper.vehicleMap.containsKey(registrationPlateOfVehicle))
            return vehicleIDMapper.vehicleMap.get(registrationPlateOfVehicle).getLisence();
        else {
            String query = "SELECT license FROM Vehicles " +
                    "WHERE registration_plate='" + registrationPlateOfVehicle + "'";
            try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next())
                    return rs.getString(1);
                else
                    throw new Exception("Oops, there is no vehicle with this driver's license");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public List<String> selectAll() throws Exception {
        String query = "SELECT * FROM Vehicles";
        List<String> DTOList = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DTOList.add(ConvertResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return DTOList;
    }

    public VehicleDTO getVehicle(String registrationPlateOfVehicle) throws Exception {
        if(vehicleIDMapper.vehicleMap.containsKey(registrationPlateOfVehicle)) return vehicleIDMapper.vehicleMap.get(registrationPlateOfVehicle);
        else {
            String query = "SELECT * FROM Vehicles " +
                    "WHERE registration_plate='" + registrationPlateOfVehicle + "'";
            try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    VehicleDTO vehicle = new VehicleDTO(rs.getString(3), rs.getString(1), rs.getString(2),
                            rs.getInt(4), rs.getInt(5));
                    vehicleIDMapper.vehicleMap.put(registrationPlateOfVehicle, vehicle);
                    return vehicle;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
