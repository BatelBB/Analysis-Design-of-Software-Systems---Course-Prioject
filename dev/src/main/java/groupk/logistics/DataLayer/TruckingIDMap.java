package groupk.logistics.DataLayer;

import groupk.logistics.business.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckingIDMap {

    public Map<Integer, TruckingDTO> truckingsMap;
    private static TruckingIDMap singletonTruckingIDMapInstance = null;
    private TruckingIDMap() {
        truckingsMap = new ConcurrentHashMap<Integer, TruckingDTO>();
    }


    public static TruckingIDMap getInstance() throws Exception {
        if (singletonTruckingIDMapInstance == null)
            singletonTruckingIDMapInstance = new TruckingIDMap();
        return singletonTruckingIDMapInstance;
    }
}
