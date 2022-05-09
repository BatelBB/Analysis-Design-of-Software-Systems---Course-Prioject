package groupk.logistics.DataLayer;

import groupk.logistics.business.Site;
import groupk.logistics.business.TruckManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Truckings_DestsIDMAP {
    public Map<Integer, Site> DestsMap;
    private static Truckings_DestsIDMAP singleton = null;
    private Truckings_DestsIDMAP() {
        DestsMap= new ConcurrentHashMap<Integer, Site>();
    }


    public static Truckings_DestsIDMAP getInstance() throws Exception {
        if (singleton == null)
            singleton = new Truckings_DestsIDMAP();
        return singleton;
    }

}
