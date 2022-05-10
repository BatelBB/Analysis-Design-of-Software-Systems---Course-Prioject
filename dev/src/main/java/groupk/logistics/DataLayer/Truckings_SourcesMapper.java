package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Site;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Truckings_SourcesMapper extends myDataBase {

    private Truckings_SourcesIDMAP truckings_sourcesIDMAP = Truckings_SourcesIDMAP.getInstance();
    public Truckings_SourcesMapper() throws Exception {
        super("Truckings_Sources");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public void addTruckingSources(int truckingIdCounter, List<Site> sources) {
        for (Site source:sources) {addTruckingSource(truckingIdCounter,source); truckings_sourcesIDMAP.SourcesMap.put(truckingIdCounter,source);}
    }

        public void addTruckingSource(int truckingIdCounter, Site source) {
        int n = 0;
        String query = "INSERT INTO Truckings_Sources(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

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
                n = prepStat.executeUpdate();
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

}
