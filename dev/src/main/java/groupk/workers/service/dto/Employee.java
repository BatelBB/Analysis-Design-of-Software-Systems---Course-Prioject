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
        public WeeklyShift(Day day, Shift.Type type){
            this.day = day;
            this.type = type;
        }
    }

    public String id;
    public String name;
    public Role role;

    public Employee(
            String id,
            String name,
            Role role,
            String bank,
            int bankID,
            int bankBranch,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Set<WeeklyShift> shiftPreferences,
            Date employmentStart
    ) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.bank = bank;
        this.bankID = bankID;
        this.bankBranch = bankBranch;
        this.salaryPerHour = salaryPerHour;
        this.sickDaysUsed = sickDaysUsed;
        this.vacationDaysUsed = vacationDaysUsed;
        this.shiftPreferences = shiftPreferences;
        this.employmentStart = employmentStart;
    }

    public String bank;
    public int bankID;
    public int bankBranch;
    public int salaryPerHour;
    public int sickDaysUsed;
    public int vacationDaysUsed;
    public Set<WeeklyShift> shiftPreferences;
    public Date employmentStart;
}
