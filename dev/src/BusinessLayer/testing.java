package BusinessLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ServiceLayer.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;


public class testing {
    private UserController userController =  UserController.getInstance();
    private TruckManagerController truckManagerController = TruckManagerController.getInstance();
    private DriverController driverController = DriverController.getInstance();


    @Before // setup()
    public void before() throws Exception {
        System.out.println("Setting it up!");
        userController.registerUser("ido shapira","ido1Ido1","ido1Ido1",Role.truckingManager,"tm1234tm");
    }

    @Test
        public void registerUsersTests() throws Exception {
            assertEquals(userController.registerUser("ido shapira","ido1Ido1","ido1Ido1",Role.truckingManager,"tm1234tm"),true);
            assertEquals(userController.login("ido1Ido1","ido1Ido1"),true);
            assertEquals(userController.logout(),true);
            try {
                assertEquals(userController.login("idoWrong","ido1Ido1"),true);
            }
            catch (Exception e) {
                assertEquals(e.getMessage(),"Sorry but there's no user with that username");
            }
       //  truckManagerController.addVehicle(DLicense.C,"45345642","volvo",12,14);
        assertEquals(userController.login("ido1Ido1","ido1Ido1"),true);
        assertEquals(truckManagerController.getVehiclesRegistrationPlates().contains("45345642"),true);
    }

    @Test
    public void addVeichle() throws Exception {
            // truckManagerController.addVehicle(DLicense.C,"45345642","volvo",12,14);

    }


}
