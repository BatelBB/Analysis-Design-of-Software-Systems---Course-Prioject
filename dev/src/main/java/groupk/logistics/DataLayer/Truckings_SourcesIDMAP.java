package groupk.logistics.DataLayer;

import groupk.logistics.business.Site;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Truckings_SourcesIDMAP {
    public Map<Integer, Site> SourcesMap;
    private static Truckings_SourcesIDMAP singleton = null;
    private Truckings_SourcesIDMAP() {
        SourcesMap= new ConcurrentHashMap<Integer, Site>();
    }


    public static Truckings_SourcesIDMAP getInstance() throws Exception {
        if (singleton == null)
            singleton = new Truckings_SourcesIDMAP();
        return singleton;
    }
}
