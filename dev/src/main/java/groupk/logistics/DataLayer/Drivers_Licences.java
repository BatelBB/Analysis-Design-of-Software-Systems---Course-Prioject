package groupk.logistics.DataLayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Drivers_Licences extends myDataBase {
    public Drivers_Licences() throws SQLException {
        super("Driver_Licences");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }
}
