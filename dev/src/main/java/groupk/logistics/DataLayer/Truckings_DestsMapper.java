package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Site;

import java.sql.*;
import java.util.List;

public class Truckings_DestsMapper extends myDataBase {
    private Truckings_DestsIDMAP truckings_destsIDMAP = Truckings_DestsIDMAP.getInstance();
    public Truckings_DestsMapper() throws Exception {
        super("Truckings_Destinations");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public void addTruckingDestinations(int truckingIdCounter, List<Site> destinations) {
        for (Site destination:destinations) {addTruckingDestination(truckingIdCounter,destination);truckings_destsIDMAP.DestsMap.put(truckingIdCounter,destination);}
    }

    public void addTruckingDestination(int truckingIdCounter, Site destination) {
        int n = 0;
        String query = "INSERT INTO Truckings_Destinations(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setInt(1, truckingIdCounter);
                prepStat.setString(2, destination.getContactGuy());
                prepStat.setString(3, destination.getCity());
                prepStat.setString(4, destination.getPhoneNumber());
                prepStat.setString(5, destination.getStreet());
                prepStat.setString(6, destination.getStringArea());
                prepStat.setInt(7, destination.getHouseNumber());
                prepStat.setInt(8, destination.getFloor());
                prepStat.setInt(9, destination.getApartment());
                n = prepStat.executeUpdate();
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }


}
