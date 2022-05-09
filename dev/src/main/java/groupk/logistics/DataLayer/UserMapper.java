package groupk.logistics.DataLayer;

import groupk.logistics.business.Driver;
import groupk.logistics.business.TruckManager;
import groupk.logistics.business.User;
import groupk.logistics.business.Vehicle;

import java.sql.*;
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

    public User getUser(String username) {
        if(userIDMap.userMap.containsKey(username)) return  userIDMap.userMap.get(username);
        String query = "SELECT * FROM Users" +
                "WHERE username='"+username;
        List<String> details = new LinkedList<>();
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                User user;
                if(rs.getString(4)== "driver") user = new Driver(rs.getString(3),
                        rs.getString(1), rs.getString(2));
                else user= new TruckManager(rs.getString(3),
                        rs.getString(1), rs.getString(2));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String username, String password){
        if(userIDMap.userMap.containsKey(username))userIDMap.userMap.get(username).updatePassword(password);
        String sql = "UPDATE Users SET password="+password+" WHERE username='"+username+"'";
        int n=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return n>0;
    }

    public boolean hasUsername(String username) {
        String query = "SELECT * FROM Users" +
                "WHERE username='"+username;
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addUser(User user)
    {
        userIDMap.userMap.put(user.getUsername(), user);
    }
}
