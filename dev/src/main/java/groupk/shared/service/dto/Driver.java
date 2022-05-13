package groupk.shared.service.dto;

import java.util.Set;

public class Driver {
    public Driver(String employeeID, Set<License> licenses) {
        this.employeeID = employeeID;
        this.licenses = licenses;
    }

    public static enum License {
        B,
        C1,
        C,
        CE
    }

    public String employeeID;
    public Set<License> licenses;
}
