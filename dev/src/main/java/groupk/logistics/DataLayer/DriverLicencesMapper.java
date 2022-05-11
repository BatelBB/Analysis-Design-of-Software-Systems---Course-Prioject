package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;
import groupk.logistics.business.Driver;
import groupk.logistics.business.Vehicle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicencesMapper extends myDataBase {
    private DriverLicencesIDMapper driverLicencesIDMapper;
    public DriverLicencesMapper() throws Exception {
        super("Driver_Licences");
        driverLicencesIDMapper = DriverLicencesIDMapper.getInstance();
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(2);
    }

    public List<DLicense> getMyLicenses(String username) throws Exception {
        List<DLicense> DTOList = new LinkedList<DLicense>();
        String query = "SELECT licence FROM Drivers_Licences Where username = '" + username +"'";
        try (Connection conn = DriverManager.getConnection(finalCurl);
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                DTOList.add(Vehicle.castDLicenseFromString(rs.getString(1)));
            }
        } catch (Exception e) {
            throw new Exception("Oops, there was unexpected problem with get the licenses from the driver: \"" + username + "\"\nError description: " + e.getMessage());
        }
        return DTOList;
    }

    public boolean addLicence(String username, DLicense license) throws Exception {
        int n = 0;
        String query = "INSERT INTO Drivers_Licences(username,licence) VALUES(?,?)";

        try(Connection conn = DriverManager.getConnection(finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, username);
                prepStat.setString(2, license.name());
                n = prepStat.executeUpdate();
                driverLicencesIDMapper.driverLicencesIDMapper.put(username,license.name());
            }
            else
                throw new Exception("The connection to the data has lost");
        }
        catch (SQLException e){
            throw new Exception("Oops, there was unexpected problem with add the license '" + license.name() + "' to the driver: '" + username + "'\nError description: " + e.getMessage());
        }

        return n == 1;
    }
}
