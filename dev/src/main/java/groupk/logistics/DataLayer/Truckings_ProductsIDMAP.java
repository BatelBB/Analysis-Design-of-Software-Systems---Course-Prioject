package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Site;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Truckings_ProductsIDMAP {
    public Map<Integer, ProductForTrucking> productForTruckingMap;
    private static Truckings_ProductsIDMAP singleton = null;
    private Truckings_ProductsIDMAP() {
        productForTruckingMap= new ConcurrentHashMap<Integer, ProductForTrucking>();
    }


    public static Truckings_ProductsIDMAP getInstance() throws Exception {
        if (singleton == null)
            singleton = new Truckings_ProductsIDMAP();
        return singleton;
    }
}
