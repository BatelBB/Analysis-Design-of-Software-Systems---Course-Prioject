package groupk.logistics.DataLayer;

import groupk.logistics.business.TruckManager;
import groupk.logistics.business.Vehicle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckMangersIDMap {
    public Map<String, TruckManager> truckManagerMap;
    private static TruckMangersIDMap singletonTMMapperInstance = null;
    private TruckMangersIDMap() {
        truckManagerMap = new ConcurrentHashMap<String, TruckManager>();
    }


    public static TruckMangersIDMap getInstance() throws Exception {
        if (singletonTMMapperInstance == null)
            singletonTMMapperInstance = new TruckMangersIDMap();
        return singletonTMMapperInstance;
    }

}
