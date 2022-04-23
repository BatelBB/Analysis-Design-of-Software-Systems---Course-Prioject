package Test;

import BusinessLayer.DriverController;
import BusinessLayer.Role;
import BusinessLayer.TruckManagerController;
import BusinessLayer.UserController;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTests {

    private UserController userController = UserController.getInstance();
    private TruckManagerController truckManagerController = TruckManagerController.getInstance();

    public RegisterTests() throws Exception {
    }

    @Test
    public void tooShortUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "id", "ido1Ido1", Role.truckingManager, "tm1234tm");
            assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The minimum username length should be at least 3 characters");
        }
    }

    @Test
    public void tooLongUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "idoShapiraWrong", "idoShapiraWrong", Role.truckingManager, "tm1234tm");
            assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            assertEquals(e.getMessage(), "The maximum username length should be up to 12 characters");
        }
    }

    @Test
    public void notValidUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "i#d", "idoWrong", Role.truckingManager, "tm1234tm");
            assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Username can only contain letters and numbers");
        }
    }

    @Test
    public void notValidRegisterCodeForTMException() throws Exception {
        try {
            userController.registerUser("ido shapira", "iffd", "idoWrong", Role.truckingManager, "fgt");
            assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Sorry, the code is not valid");
        }
    }

    @Test
    public void notValidRegisterCodeForDriverException() throws Exception {
        try {
            userController.registerUser("ido shapira", "ido1Ido1","idoWron1g",Role.driver, "WRONG");
            assertEquals(true, false); //if the register won't throw exception the test will fail.
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"Sorry, the code is not valid");
        }
    }

    @Test
    public void registerTMSuccess() throws Exception {
        assertEquals(userController.registerUser("ido shapira", "ido1Ido1", "ido1Ido1", Role.truckingManager, "tm1234tm"), true);
    }

    @Test
    public void registerDriversSuccess() throws Exception {
        userController.login("ido1Ido1", "ido1Ido1");
        String hashCode = truckManagerController.getRegisterCode();
        assertEquals(userController.logout(), true);
        assertEquals(userController.registerUser("rami fozis", "ramiF", "rami1Rami1", Role.driver, hashCode), true);
        assertEquals(userController.registerUser("tamir avisar", "tam1Tamir1", "tam1Tamir1", Role.driver, hashCode), true);
    }
}
