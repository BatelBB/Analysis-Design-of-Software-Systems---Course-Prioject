package groupk.workers.data;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.*;

public class Employee {
    public static enum ShiftDateTime {
        SundayMorning,
        SundayEvening,
        MondayMorning,
        MondayEvening,
        TuesdayMorning,
        TuesdayEvening,
        WednesdayMorning,
        WednesdayEvening,
        ThursdayMorning,
        ThursdayEvening,
        FridayMorning,
        FridayEvening,
        SaturdayMorning,
        SaturdayEvening
    }

    public static ShiftDateTime toShiftDateTime(Calendar date, boolean isEvening) {
        ShiftDateTime s = ShiftDateTime.values()[((date.get(Calendar.DAY_OF_WEEK)) - 1) * 2 + (isEvening ? 1 : 0)];
        return s;
    }

    private String name;
    private String id;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private BankAccount account;
    private WorkingConditions conditions;
    private Set<ShiftDateTime> availableShifts;
    public enum Role{
        Logistics,
        HumanResources,
        Stocker,
        Cashier,
        LogisticsManager,
        ShiftManager,
        Driver,
        StoreManager
    };
    private Role role;

    public Employee(String name, String id, String bank, int bankID, int bankBranch,
                    Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Role role, Set<ShiftDateTime> availableShifts){
        this.name = name;
        this.id = id;
        account = new BankAccount(bank, bankID, bankBranch);
        conditions = new WorkingConditions(employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed);
        this.role = role;
        this.availableShifts = availableShifts;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public BankAccount getAccount() { return account; }

    public WorkingConditions getConditions() { return conditions; }

    public Set<ShiftDateTime> getAvailableShifts() { return availableShifts; }

    public Role getRole() { return role;}

    public Employee setAvailableShifts(Set<ShiftDateTime> shiftPreferences) {
        availableShifts = shiftPreferences;
        return this;
    }

    public Employee addEmployeeShiftPreference(ShiftDateTime shift){
        availableShifts.add(shift);
        return this;
    }

    public Employee deleteEmployeeShiftPreference(ShiftDateTime shift){
        availableShifts.remove(shift);
        return this;
    }

    public boolean isShiftpreferred(ShiftDateTime shift){
        return availableShifts.contains(shift);
    }
}
