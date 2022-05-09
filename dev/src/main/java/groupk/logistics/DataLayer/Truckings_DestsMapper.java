package groupk.logistics.DataLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Truckings_DestsMapper extends myDataBase {
    public Truckings_DestsMapper() throws SQLException {
        super("Truckings_Destinations");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public void addTruckingDestinations(int truckingIdCounter, List<List<String>> destinations) {
    }
}
