package groupk.logistics.DataLayer;

import groupk.logistics.business.Driver;
import groupk.logistics.business.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserMapper extends myDataBase{

    private UserIDMap userIDMap;
    public UserMapper() throws Exception {
        super("Users");
        userIDMap=UserIDMap.getInstance();
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public List<String> getUser(String username) {
        String query = "SELECT * FROM Users" +
                "WHERE username='"+username;
        List<String> details = new LinkedList<>();
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                details.add(rs.getString(2));
                details.add(rs.getString(3));
                return details;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
