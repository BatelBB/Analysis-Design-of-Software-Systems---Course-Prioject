package groupk.logistics.DataLayer;

import groupk.logistics.business.Driver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicencesMapper {
    public Map<String, String> driverLicences;
    private static DriverLicencesMapper singleton = null;
    private DriverLicencesMapper() {
        driverLicences = new ConcurrentHashMap<String, String>();
    }


    public static DriverLicencesMapper getInstance() throws Exception {
        if (singleton == null)
            singleton = new DriverLicencesMapper();
        return singleton;
    }

}
