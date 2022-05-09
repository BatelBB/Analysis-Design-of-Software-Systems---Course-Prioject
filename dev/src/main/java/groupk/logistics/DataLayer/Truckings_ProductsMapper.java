package groupk.logistics.DataLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Truckings_ProductsMapper extends  myDataBase {
    public Truckings_ProductsMapper() throws SQLException {
        super("Truckings_Products");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }
}
