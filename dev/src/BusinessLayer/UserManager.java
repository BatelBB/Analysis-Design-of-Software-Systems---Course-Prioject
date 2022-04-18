package BusinessLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private Map<String,String> truckingManagers;
    private Map<String,String> drivers;
    private Map<String,String> workers;
    private List<String> logedIn;

    public UserManager()
    {
        truckingManagers = new ConcurrentHashMap<String, String>();
        drivers = new ConcurrentHashMap<String, String>();
        workers = new ConcurrentHashMap<String, String>();
        logedIn = new LinkedList<String>();
    }




    public void registerDriver(Driver driver,String userName, String password, Role role, String code)
    {
        String validUserName = validateUserNameToRegister(userName);
        String validPassowrd= validatePassword(password);
        if(validUserName!="Legal") throw new IllegalArgumentException(validUserName);
        if(validPassowrd!="Legal") throw new IllegalArgumentException(validPassowrd);
        if(!(role == Role.driver && code == "DR12DR")) throw new IllegalArgumentException("Illegal code");
        drivers.put(userName,password);
        workers.put(userName,password);
        driver.setAccount(role,password,userName);
    }

    public void registerTM(TruckManager truckManager,String userName, String password, Role role, String code)
    {
        String validUserName = validateUserNameToRegister(userName);
        String validPassowrd= validatePassword(password);
        if(validUserName!="Legal") throw new IllegalArgumentException(validUserName);
        if(validPassowrd!="Legal") throw new IllegalArgumentException(validPassowrd);
        if(!(role == Role.truckingManager&&code == "TM12TM"))
            throw new IllegalArgumentException("Illegal code");
        truckingManagers.put(userName,password);
        workers.put(userName,password);
        truckManager.setAccount(role,password,userName);

    }
    public void LoginDriver(Driver driver, String userName, String password)
    {
        String validUserName = validateUserNameToLogin(userName);
        String validPassowrd= validatePassword(password);
        if(validUserName!="Legal") throw new IllegalArgumentException(validUserName);
        if(validPassowrd!="Legal") throw new IllegalArgumentException(validPassowrd);
        if(!(drivers.get(userName)==password)) throw new IllegalArgumentException("Bad password");
        driver.setLogin();
        logedIn.add(userName);
    }

    public void LoginTruckManager(TruckManager truckManager, String userName, String password)
    {
        String validUserName = validateUserNameToLogin(userName);
        String validPassowrd= validatePassword(password);
        if(validUserName!="Legal") throw new IllegalArgumentException(validUserName);
        if(validPassowrd!="Legal") throw new IllegalArgumentException(validPassowrd);
        if(!(drivers.get(userName)==password)) throw new IllegalArgumentException("Bad password");
        truckManager.setLogin();
        logedIn.add(userName);
    }



    private String validateUserNameToRegister(String userName)
    {
        if(userName.length()<3 | userName.length()>12) return  "Illegal Name";
        for(int i = 0 ; i < userName.length(); i++)
        {
            if(!(Character.isLetter(userName.charAt(i))| Character.isDigit(userName.charAt(i)))) return  "Illegal Name";
        }

        if(workers.containsKey(userName)) return "username exist";

        return "Legal";
    }

    private String validateUserNameToLogin(String userName)
    {
        if(userName.length()<3 | userName.length()>12) return  "Illegal Name";
        for(int i = 0 ; i < userName.length(); i++)
        {
            if(!(Character.isLetter(userName.charAt(i))| Character.isDigit(userName.charAt(i)))) return  "Illegal Name";
        }

        if(!workers.containsKey(userName)) return "username doesnt exist";

        return "Legal";
    }

    private String validatePassword(String password)
    {
        boolean numberFlag = false;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        if(password.length()<6 | password.length()>12) return  "Illegal Password";
        for(int i = 0 ; i < password.length(); i++)
        {
            char curr = password.charAt(i);
            if( Character.isDigit(curr)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(curr)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(curr)) {
                lowerCaseFlag = true;
            }        }

        if(!lowerCaseFlag|!numberFlag|!capitalFlag) return  "Illegal Password";
        return "Legal";
    }






}
