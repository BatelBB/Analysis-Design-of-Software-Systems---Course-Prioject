package groupk.logistics.DataLayer;

import groupk.logistics.business.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Truckings_DestsMapper extends myDataBase {
    public Truckings_DestsMapper() throws SQLException {
        super("Truckings_Destinations");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public List<String> addTruckingDestinations(int truckingIdCounter, List<Site> destinations) throws Exception {
        //return the exceptions of every destination that got error
        List<String> Exceptions = new LinkedList<String>();
        int n = 0;
        String query = "INSERT INTO Truckings(TID, contact_guy, city, phone_number, street, area, house_number, floor, apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";
        for (Site destination : destinations) {
            try(Connection conn = getConnection()){
                if(conn != null) {
                    PreparedStatement prepStat = conn.prepareStatement(query);
                    prepStat.setInt(1, truckingIdCounter);
                    prepStat.setString(2, destination.getContactGuy());
                    prepStat.setString(3, destination.getCity());
                    prepStat.setString(4, destination.getPhoneNumber());
                    prepStat.setString(5, destination.getStreet());
                    prepStat.setString(6, destination.getArea().name());

                    n += prepStat.executeUpdate();
                }
                else
                    throw new Exception("The connection with the data lost :(  try again later");
            }
            catch (Exception e){
                Exceptions.add("There was a problem with the destination with the contact guy: " + destination.getContactGuy());
            }
        }
        return Exceptions;
    }
}
