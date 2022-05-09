package groupk.logistics.DataLayer;

    import groupk.logistics.business.Driver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriversIDMap {
    public Map<String, Driver> driverMap;
    private static DriversIDMap singletonDriversMapperInstance = null;
    private DriversIDMap() {
        driverMap = new ConcurrentHashMap<String, Driver>();
    }


    public static DriversIDMap getInstance() throws Exception {
        if (singletonDriversMapperInstance == null)
            singletonDriversMapperInstance = new DriversIDMap();
        return singletonDriversMapperInstance;
    }

}
