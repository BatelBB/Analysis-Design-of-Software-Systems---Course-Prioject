package groupk.logistics.DataLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleMapper {
    private VehiclesIDMap vehicleIDMapper = VehiclesIDMap.getInstance();

    public VehicleMapper() {
    }

    public void deleteDB() {
        vehicleIDMapper.resetData();
    }

    private String ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }

    public boolean addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) {
        int n = 0;
        String query = "INSERT INTO Vehicles(registration_plate, model,license, weight,max_weight) VALUES(?,?,?,?,?)";

        try {
            Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
            PreparedStatement prepStat = conn.prepareStatement(query);
            prepStat.setString(1, registrationPlate);
            prepStat.setString(2, model);
            prepStat.setString(3, lisence);
            prepStat.setInt(4, weight);
            prepStat.setInt(5, maxWeight);
            n = prepStat.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return n == 1;
    }

    public void addVehicle(VehicleDTO newVehicle) {
        vehicleIDMapper.vehicleMap.put(newVehicle.getRegistationPlate(), newVehicle);
    }

    public String getLicense(String registrationPlateOfVehicle) {
        if (vehicleIDMapper.vehicleMap.containsKey(registrationPlateOfVehicle))
            return vehicleIDMapper.vehicleMap.get(registrationPlateOfVehicle).getLisence();
        else {
            String query = "SELECT license FROM Vehicles " +
                    "WHERE registration_plate='" + registrationPlateOfVehicle + "'";
            try {
                Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    conn.close();
                    return rs.getString(1);
                } else {
                    conn.close();
                    throw new IllegalArgumentException("Oops, there is no vehicle with this registration plate");
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public List<String> getAllRegistrationPlates() {
        String query = "SELECT * FROM Vehicles";
        List<String> DTOList = new ArrayList<String>();
        try {
            Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DTOList.add(ConvertResultSetToDTO(rs));
            }
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return DTOList;
    }

    public VehicleDTO getVehicle(String registrationPlateOfVehicle) {
        if (vehicleIDMapper.vehicleMap.containsKey(registrationPlateOfVehicle))
            return vehicleIDMapper.vehicleMap.get(registrationPlateOfVehicle);
        else {
            String query = "SELECT * FROM Vehicles " +
                    "WHERE registration_plate='" + registrationPlateOfVehicle + "'";
            try {
                Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    VehicleDTO vehicle = new VehicleDTO(rs.getString(3), rs.getString(1), rs.getString(2),
                            rs.getInt(4), rs.getInt(5));
                    vehicleIDMapper.vehicleMap.put(registrationPlateOfVehicle, vehicle);
                    conn.close();
                    return vehicle;
                }
                conn.close();
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return null;
    }
}

