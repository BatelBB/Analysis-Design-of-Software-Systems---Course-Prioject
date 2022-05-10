package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverLicensesMap {
    public Map<String, List<DLicense>> driverLicencesIDMapper ; //key: username, value: list of licenses
    private static DriverLicensesMap singleton = null;
    private DriverLicensesMap() {
        driverLicencesIDMapper  = new ConcurrentHashMap<String, List<DLicense>>();
    }


    public static DriverLicensesMap getInstance() throws Exception {
        if (singleton == null)
            singleton = new DriverLicensesMap();
        return singleton;
    }
}
