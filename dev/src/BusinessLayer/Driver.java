package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class Driver extends User {
    private List<DLicense> licenses;
    private List<Trucking> futureTruckings;


    public Driver(String id, String name,List<DLicense> licenses)
    {
        this.id = id;
        this.name = name;
        account = null;
        isLogin = false;
        this.licenses=licenses;
        futureTruckings = new LinkedList<Trucking>();
    }

    public String getName() {
        return name;
    }

    public List<Trucking> getFutureTruckings() {
        return futureTruckings;
    }

    public void addTrucking(Trucking trucking)
    {
        futureTruckings.add(trucking);
    }

    public void removeTrucking(Trucking trucking)
    {
        futureTruckings.remove(trucking);
    }


    public String getId() {
        return id;
    }

    public void addLicense(DLicense dLicense)
    {
        if (!(licenses.contains(dLicense)))licenses.add(dLicense);
    }

    public void removeLicense(DLicense dLicense)
    {
        if (!(licenses.contains(dLicense)))licenses.remove(dLicense);
    }

    public  List<Trucking> showMyTruckings()
    {
        if(isLogin()) return  futureTruckings;
        else throw new IllegalArgumentException("You need to be logged in");
    }

    public List<DLicense> getLicenses() {
        return licenses;
    }
}
