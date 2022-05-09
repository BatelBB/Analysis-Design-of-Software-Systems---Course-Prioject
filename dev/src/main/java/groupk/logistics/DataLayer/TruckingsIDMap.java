package groupk.logistics.DataLayer;

import groupk.logistics.business.Trucking;
import groupk.logistics.business.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingsIDMap {

    public Map<Integer, Trucking> truckingMap;
    private static TruckingsIDMap singletonTruckingsMapInstance = null;
    private TruckingsIDMap() {
        truckingMap = new ConcurrentHashMap<Integer, Trucking>();
    }


    public static TruckingsIDMap getInstance() throws Exception {
        if (singletonTruckingsMapInstance == null)
            singletonTruckingsMapInstance = new TruckingsIDMap();
        return singletonTruckingsMapInstance;
    }
}
