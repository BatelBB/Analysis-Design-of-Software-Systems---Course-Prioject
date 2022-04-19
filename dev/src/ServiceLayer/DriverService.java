package ServiceLayer;

import BusinessLayer.*;

import java.util.List;

public class DriverService {

    private DriverController driverController;


    public Response Register(String id, String name, String username, String password, Role role, String code, List<DLicense> licenses) {
        try {
            driverController.registerDriver(id,name,username,password,role,code,licenses);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response Login(String username, String password) {
        try {
            driverController.LoginDriver(username,password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response showMyTruckings(String userName) {
        try {
            driverController.showMyTruckings(userName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response addLicense(DLicense license,String username) {
        try {
            driverController.addLicense(license,username);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeLicense(DLicense license,String username) {
        try {
            driverController.removeLicense(license,username);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
