package Test;

import BusinessLayer.TruckManagerController;
import BusinessLayer.UserController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class RegisterTests {

    private UserController userController = UserController.getInstance();
    private TruckManagerController truckManagerController = TruckManagerController.getInstance();

    public RegisterTests() throws Exception {
    }

    @Test
    public void tooShortUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "id", "ido1Ido1", "trucking manager", "tm1234tm");
            Assert.assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The minimum username length should be at least 3 characters");
        }
    }

    @Test
    public void tooLongUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "idoShapiraWrong", "idoShapiraWrong", "trucking manager", "tm1234tm");
            Assert.assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The maximum username length should be up to 12 characters");
        }
    }

    @Test
    public void notValidUsernameException() throws Exception {
        try {
            userController.registerUser("ido shapira", "i#d", "idoWrong", "trucking manager", "tm1234tm");
            Assert.assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Username can only contain letters and numbers");
        }
    }

    @Test
    public void notValidRegisterCodeForTMException() throws Exception {
        try {
            userController.registerUser("ido shapira", "iffd", "idoWrong", "trucking manager", "fgt");
            Assert.assertEquals(true, false); //if the register won't throw exception the test will fail.
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Sorry, the code is not valid");
        }
    }

    @Test
    public void notValidRegisterCodeForDriverException() throws Exception {
        try {
            userController.registerUser("ido shapira", "ido1Ido1","idoWron1g","driver", "WRONG");
            Assert.assertEquals(true, false); //if the register won't throw exception the test will fail.
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Sorry, the code is not valid");
        }
    }

    @Test
    public void registerTMSuccess() throws Exception {
        Assert.assertEquals(userController.registerUser("ido shapira", "ido1Ido1", "ido1Ido1","driver", "tm1234tm"), true);
    }

    @Test
    public void registerDriversSuccess() throws Exception {
        userController.login("ido1Ido1", "ido1Ido1");
        String hashCode = truckManagerController.getRegisterCode();
        Assert.assertEquals(userController.logout(), true);
        Assert.assertEquals(userController.registerUser("rami fozis", "ramiF", "rami1Rami1", "driver", hashCode), true);
        Assert.assertEquals(userController.registerUser("tamir avisar", "tam1Tamir1", "tam1Tamir1", "driver", hashCode), true);
    }
}
