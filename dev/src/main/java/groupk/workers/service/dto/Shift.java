package groupk.workers.service.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Shift {
    public enum Type {
        Morning,
        Evening
    }

    HashMap<Employee.Role, Integer> requiredStaff;
    List<Employee> staff;
    Date date;
    Type type;

    public Type getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
