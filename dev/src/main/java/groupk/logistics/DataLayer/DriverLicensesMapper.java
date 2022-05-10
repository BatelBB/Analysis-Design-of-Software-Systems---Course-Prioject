package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverLicensesMapper extends myDataBase {
    private DriverLicensesMap driverLicensesMap;
    public DriverLicensesMapper() throws Exception {
        super("Driver_Licences");
        driverLicensesMap = DriverLicensesMap.getInstance();
    }



    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(2);
    }

    public List<String> getMyLicenses(String username) {
        List DTOList = new LinkedList();
        String query = "SELECT * FROM Drivers_Licences Where username = " + username;
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

    public boolean addLicence(String username, String license) {

        int n = 0;
        String query = "INSERT INTO Drivers_Licences(username,licence) VALUES(?,?)";

        try(Connection conn = getConnection()){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, username);
                prepStat.setString(2, license);
                n = prepStat.executeUpdate();
                driverLicensesMap.driverLicencesIDMapper.get(username).add(castFromString(license));
            }
            else
                return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return n == 1;
    }

    private DLicense castFromString(String dLicense)
    {
        if(dLicense.equals("B")) return DLicense.B;
        else if (dLicense.equals("C")) return DLicense.C;
        else if (dLicense.equals("C1")) return DLicense.C1;
        else if (dLicense.equals("C+E")) return DLicense.C_E;
        else throw new IllegalArgumentException("wrong license");

    }
}
