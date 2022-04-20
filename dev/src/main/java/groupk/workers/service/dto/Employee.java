package groupk.workers.service.dto;

import java.util.Date;
import java.util.Set;

public class Employee {
    public enum Role {
        Logistics,
        HumanResources,
        Stocker,
        Cashier,
        LogisticsManager,
        ShiftManager,
        Driver
    }

    public static class WeeklyShift {
        public enum Day {
            Sunday,
            Monday,
            Tuesday,
            Wednesday,
            Thursday,
            Friday,
            Saturday
        }

        public Day day;
        public Shift.Type type;
    }

    public String id;
    public String name;
    public Role role;
    public String bank;
    public int bankID;
    public int bankBranch;
    public int salaryPerHour;
    public int sickDaysUsed;
    public int vacationDaysUsed;
    public Set<WeeklyShift> shiftPreferences;
    public Date employmentStart;
}
