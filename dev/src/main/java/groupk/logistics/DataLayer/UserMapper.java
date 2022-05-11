package groupk.logistics.DataLayer;

import groupk.logistics.business.*;
import groupk.logistics.business.Driver;
import groupk.logistics.service.Response;

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
        String query = "SELECT * FROM Users " +
                "WHERE username='"+username+"'";
        List<String> details = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                User user;
                if(rs.getString(4).equals("driver")) user = new Driver(rs.getString(3),
                        rs.getString(1), rs.getString(2));
                else user= new TruckManager(rs.getString(3),
                        rs.getString(1), rs.getString(2));
                userIDMap.userMap.put(username,user);
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String username, String password){
        if(userIDMap.userMap.containsKey(username)) userIDMap.userMap.get(username).updatePassword(password);
        String query = "UPDATE Users SET password=? WHERE username=?";
            int n = 0;
            try (Connection conn = DriverManager.getConnection(finalCurl);
                 PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, password);
                statement.setString(2, username);
                 n = statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return n > 0;
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addUser(User user, String password)
    {
        int n = 0;
        String query = "INSERT INTO Users(username,password,name,role) VALUES(?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                String role;
                prepStat.setString(1, user.getUsername());
                prepStat.setString(2, password);
                prepStat.setString(3,user.getName());
                if(user.getRole()== Role.driver)role = "driver";
                else role = "truck manager";
                prepStat.setString(4, role);
                n = prepStat.executeUpdate();
                userIDMap.userMap.put(user.getUsername(), user);

            }
            else
                return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return n == 1;
    }

    public List<String> getDriversUsernames() {
        List<String> driversUsername = new LinkedList<>();
        String query = "SELECT * FROM Users " +
                "WHERE role='"+"driver"+"'";
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                driversUsername.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return driversUsername;
    }
}
