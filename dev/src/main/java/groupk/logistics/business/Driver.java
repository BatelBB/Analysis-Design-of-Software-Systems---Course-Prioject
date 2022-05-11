package groupk.logistics.business;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Driver extends User {
    private List<DLicense> licenses;


    public Driver(String name, String username, String password) {
        super(name, username, password);
        this.role = Role.driver;
        licenses = new LinkedList<DLicense>();
    }

    public synchronized void checkTrucking(Trucking trucking) {
        if (trucking.getDate().compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("The date must be in the future");
        if (trucking.getDriverUsername() != this.username)
            throw new IllegalArgumentException("The driver does not match the driver specified in the form");
        if (licenses.size() == 0)
            throw new IllegalArgumentException("Oops, the driver hasn't entered his driver's license yet");
        if(false)
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license appropriate for the vehicle type");
    }





    private DLicense castFromString(String dLicense)
    {
        if(dLicense.equals("B")) return DLicense.B;
        else if (dLicense.equals("C")) return DLicense.C;
        else if (dLicense.equals("C1")) return DLicense.C1;
        else if (dLicense.equals("C+E")) return DLicense.C_E;
        else throw new IllegalArgumentException("wrong license");

    }



    public String getName() {
        return name;
    }

}