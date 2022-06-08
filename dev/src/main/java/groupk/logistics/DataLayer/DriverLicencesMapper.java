package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicencesMapper {
    private DriverLicencesIDMapper driverLicencesIDMapper;

    public DriverLicencesMapper() {
        driverLicencesIDMapper = DriverLicencesIDMapper.getInstance();
    }

    public void deleteDB() {
        driverLicencesIDMapper.resetData();
    }

    public List<String> getMyLicenses(int username) {
        if (driverLicencesIDMapper.isDriverUpdated(username))
            return driverLicencesIDMapper.getMyLicenses(username);
        List<String> DTOList = new LinkedList<String>();
        String query = "SELECT licence FROM Drivers_Licences Where username = '" + username + "'";
        try {
            Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                DTOList.add(rs.getString(1));
                driverLicencesIDMapper.addDLicense(username, rs.getString(1));
            }
            conn.close();
        } catch (SQLException s) {
            throw new IllegalArgumentException(s.getMessage());
        }
        driverLicencesIDMapper.updateDriver(username);
        return DTOList;
    }

    public boolean addLicence(int username, DLicense license) {
        int n = 0;
        String query = "INSERT INTO Drivers_Licences(username,licence) VALUES(?,?)";
        try {
            Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
            PreparedStatement prepStat = conn.prepareStatement(query);
            prepStat.setInt(1, username);
            prepStat.setString(2, license.name());
            n = prepStat.executeUpdate();
            if (driverLicencesIDMapper.isDriverUpdated(username))
                driverLicencesIDMapper.addDLicense(username, license.name());
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return n == 1;
    }
}

