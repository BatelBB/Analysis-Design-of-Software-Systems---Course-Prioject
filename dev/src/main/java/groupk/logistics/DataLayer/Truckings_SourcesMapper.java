package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;
import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Site;
import groupk.logistics.business.Vehicle;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Truckings_SourcesMapper extends myDataBase {

    public Truckings_SourcesMapper() throws Exception {
        super("Truckings_Sources");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public List<String> addTruckingSources(int truckingIdCounter, List<Site> sources) {
        //return the exceptions of every destination that got error
        List<String> Exceptions = new LinkedList<String>();
        String query = "INSERT INTO Truckings_Sources(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

        for (Site source: sources) {
            try (Connection conn = DriverManager.getConnection(finalCurl)){
                if(conn != null) {
                    PreparedStatement prepStat = conn.prepareStatement(query);
                    prepStat.setInt(1, truckingIdCounter);
                    prepStat.setString(2, source.getContactGuy());
                    prepStat.setString(3, source.getCity());
                    prepStat.setString(4, source.getPhoneNumber());
                    prepStat.setString(5, source.getStreet());
                    prepStat.setString(6, source.getStringArea());
                    prepStat.setInt(7, source.getHouseNumber());
                    prepStat.setInt(8, source.getFloor());
                    prepStat.setInt(9, source.getApartment());
                    if (prepStat.executeUpdate() < 1)
                        throw new Exception("The destination is already exist");
                }
                else
                    throw new Exception("The connection with the data lost");
            }
            catch (Exception e){
                Exceptions.add("There was a problem with the destination with the contact guy: " + source.getContactGuy() + "\nthe error description: " + e.getMessage());
            }
        }
        return Exceptions;
    }

    public List<Site> getSourcesByTruckingId(int TruckingID) throws Exception {
        List<Site> sites = new LinkedList<Site>();
        String query = "SELECT * FROM Truckings_Sources Where TID = " + TruckingID;
        try (Connection conn = getConnection();
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new Site(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(6)));
            }
        } catch (Exception e) {
            throw new Exception("Oops, there was unexpected problem with get the sources of trucking: \"" + TruckingID + "\"\nError description: " + e.getMessage());
        }
        return sites;
    }

}
