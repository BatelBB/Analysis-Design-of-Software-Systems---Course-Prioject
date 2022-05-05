package ServiceLayer;

import BusinessLayer.*;


public class UserService {

    private UserController userController;

    public UserService() throws Exception {
        userController = UserController.getInstance();
    }

    public Response registerUser(String name, String username, String password, String role, String code)
    {
        try
        {
            boolean success = userController.registerUser( name,  username,  password,  role,  code);
            return new Response(true);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response Login(String userEmail, String password)
    {
        try
        {
           boolean driver = userController.login(userEmail,password);
           return new Response(driver);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response Logout()
    {
        try
        {
            userController.logout();
            return new Response(true);

        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response<String> updatePassword(String newPassword) {
        try
        {
            boolean success = userController.updatePassword(newPassword);
            return new Response(success);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
}
