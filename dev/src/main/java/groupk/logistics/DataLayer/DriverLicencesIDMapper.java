package groupk.logistics.DataLayer;

import groupk.logistics.business.Site;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicencesIDMapper {
    public Map<String, String> driverLicencesIDMapper ;
    private static DriverLicencesIDMapper singleton = null;
    private DriverLicencesIDMapper() {
        driverLicencesIDMapper  = new ConcurrentHashMap<String, String>();
    }


    public static DriverLicencesIDMapper getInstance() throws Exception {
        if (singleton == null)
            singleton = new DriverLicencesIDMapper();
        return singleton;
    }
}
