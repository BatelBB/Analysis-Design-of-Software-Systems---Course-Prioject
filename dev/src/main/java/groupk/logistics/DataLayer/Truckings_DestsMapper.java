package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Site;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Truckings_DestsMapper extends myDataBase {
    public Truckings_DestsMapper() throws Exception {
        super("Truckings_Destinations");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public List<String> addTruckingDestinations(int truckingIdCounter, List<Site> destinations) {
        //return the exceptions of every destination that got error
        List<String> Exceptions = new LinkedList<String>();
        String query = "INSERT INTO Truckings_Destinations(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

        for (Site destination : destinations) {
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
                    if (prepStat.executeUpdate() < 1)
                        throw new Exception("The destination is already exist");
                }
                else
                    throw new Exception("The connection with the data lost");
            }
            catch (Exception e){
                Exceptions.add("There was a problem with the destination with the contact guy: " + destination.getContactGuy() + "\nthe error description: " + e.getMessage());
            }
        }
        return Exceptions;
    }

    public boolean removeTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings_Destinations WHERE TID = " + truckingID;
        int n = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }

    public List<Site> getDestinationsByTruckingId(int TruckingID) throws Exception {
        List<Site> sites = new LinkedList<Site>();
        String query = "SELECT * FROM Truckings_Destinations Where TID = " + TruckingID;
        try (Connection conn = getConnection();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new Site(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(6)));
            }
        } catch (Exception e) {
            throw new Exception("Oops, there was unexpected problem with get the destinations of trucking: \"" + TruckingID + "\"\nError description: " + e.getMessage());
        }
        return sites;
    }
}
