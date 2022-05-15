package groupk.logistics.DataLayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicencesIDMapper {
    public Map<Integer, String> driverLicencesIDMapper ;
    private static DriverLicencesIDMapper singleton = null;
    private DriverLicencesIDMapper() {
        driverLicencesIDMapper  = new ConcurrentHashMap<Integer, String>();
    }


    public static DriverLicencesIDMapper getInstance() throws Exception {
        if (singleton == null)
            singleton = new DriverLicencesIDMapper();
        return singleton;
    }
}
