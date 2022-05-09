package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Truckings_SourcesMapper extends myDataBase {

    public Truckings_SourcesMapper() throws SQLException {
        super("Truckings_Sources");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }

    public void addTruckingSources(int truckingIdCounter, List<List<String>> sources) {
    }

    public void addTruckingProducts(int truckingIdCounter, List<ProductForTrucking> productForTruckings) {
    }
}
