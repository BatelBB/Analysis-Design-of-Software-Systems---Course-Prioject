package groupk.logistics.DataLayer;

import groupk.logistics.business.User;
import groupk.logistics.business.UserController;
import groupk.logistics.business.Vehicle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehiclesIDMap {
    public Map<String, Vehicle> vehicleMap;
    private static VehiclesIDMap singletonVehiclesMapperInstance = null;
    private VehiclesIDMap() {
        vehicleMap = new ConcurrentHashMap<String, Vehicle>();
    }


    public static VehiclesIDMap getInstance() throws Exception {
        if (singletonVehiclesMapperInstance == null)
            singletonVehiclesMapperInstance = new VehiclesIDMap();
        return singletonVehiclesMapperInstance;
    }


}
