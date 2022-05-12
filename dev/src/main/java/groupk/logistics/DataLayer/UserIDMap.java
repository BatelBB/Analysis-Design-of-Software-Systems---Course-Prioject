package groupk.logistics.DataLayer;

import groupk.logistics.business.Driver;
import groupk.logistics.business.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserIDMap {
    public Map<String, User> userMap;
    private static UserIDMap singletonUserMapInstance = null;
    private UserIDMap() {
        userMap = new ConcurrentHashMap<String, User>();
    }


    public static UserIDMap getInstance() throws Exception {
        if (singletonUserMapInstance == null)
            singletonUserMapInstance = new UserIDMap();
        return singletonUserMapInstance;
    }
}
