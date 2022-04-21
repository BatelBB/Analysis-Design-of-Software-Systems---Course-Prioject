package ServiceLayer;

import BusinessLayer.*;


public class UserService {

    private UserController userController;

    public UserService()
    {
        userController = UserController.getInstance();
    }

    public Response registerUser(String name, String username, String password, Role role, String code)
    {
        try
        {
            boolean success = userController.registerUser( name,  username,  password,  role,  code);
            return new Response("Register success");
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
            boolean success = userController.login(userEmail,password);
            if(success) return new Response("Login success");
            else return new Response("Wrong username or wrong password");
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
            boolean success = userController.logout();
            if(success) return new Response("Logout success");
            else return new Response("Logut failed");
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
