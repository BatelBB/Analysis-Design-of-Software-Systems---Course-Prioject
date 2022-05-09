package groupk.logistics.DataLayer;

import groupk.logistics.business.TruckManager;
import groupk.logistics.business.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TruckManagersMapper extends myDataBase {
    private TruckMangersIDMap truckMangersIDMap = TruckMangersIDMap.getInstance();
    public TruckManagersMapper() throws Exception {
        super("Truck Managers");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
    public boolean addTM(String username, String code){
        int n = 0;
        String query = "INSERT INTO truck_managers(username, code) VALUES(?,?)";

        try(Connection conn = getConnection()){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, username);
                prepStat.setString(2, code);
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

    public void addTM(TruckManager truckManager) {
        truckMangersIDMap.truckManagerMap.put(truckManager.getUsername(),truckManager);

    }
}
