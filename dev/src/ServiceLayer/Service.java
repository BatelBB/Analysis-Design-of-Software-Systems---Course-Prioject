package ServiceLayer;

import BusinessLayer.DLicense;

import javax.management.relation.Role;
import java.util.List;

public class Service {

    TruckManagerService truckManagerService;
    DriverService driverService;
    UserService userService;

    public Service()
    {
        driverService = new DriverService();
        truckManagerService = new TruckManagerService();
        userService = new UserService();
    }


    public Response<String> Logout()
    {
        return userService.Logout();
    }

    public Response<String> Login(String userEmail,String password)
    {
        return userService.Login(userEmail,password);
    }

    public Response<String> registerUser(String name, String username, String password, Role role, String code)
    {
        return userService.registerUser(name,username,password,role,code);
    }

    public Response showDriverHisFutureTruckings(String name, String username, String password, Role role, String code)
    {
        return driverService.showDriverHisFutureTruckings();
    }

    public Response addLicenseToDriver(DLicense dLicense)
    {
        return driverService.addLicense(dLicense);
    }

    public Response addLicensesToDriver(List<DLicense> dLicenses)
    {
        return driverService.addLicenses(dLicenses);
    }

    public Response removeLicenseFromDriver(DLicense dLicense)
    {
        return driverService.removeLicense(dLicense);
    }


}


}
