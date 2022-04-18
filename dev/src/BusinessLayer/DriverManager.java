package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class DriverManager {

    private List<Driver> drivers;


    public DriverManager()
    {
        drivers = new LinkedList<Driver>();
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void addDriver(String id, String name,List<DLicense> driverLicenses)
    {
        if(!validateId(id)) throw new IllegalArgumentException("Illegal id number");
        if(!validateName(name)) throw new IllegalArgumentException("Illegal driver name");
        drivers.add(new Driver(id,name,driverLicenses));

    }



    private boolean validateId(String id)
    {
        if(id.length()!=9) return  false;
        for(int i = 0 ; i < id.length(); i++)
        {
            if(!(Character.isDigit(id.charAt(i)))) return  false;
        }
        return true;
    }


    private boolean validateName(String name)
    {
        if(name.length()<3 | name.length()>15) return  false;
        for(int i = 0 ; i < name.length(); i++)
        {
            if(!(Character.isLetter(name.charAt(i)))) return  false;
        }
        return true;
    }
}
