package groupk.workers.data;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.*;

public class Employee {
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
        public enum Type {
            Morning,
            Evening
        }
        public Day day;
        public Type type;

        public WeeklyShift(int dayInt, int typeInt){
            day = Day.values()[dayInt];
            type = Type.values()[typeInt];
        }
        public Day getDay() {
            return day;
        }

        public Type getType() {
            return type;
        }
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
    private Set<WeeklyShift> availableShifts;
    public enum Role{
        Logisitcs,
        HumanResources,
        Stocker,
        Cashier,
        LogisticsManager,
        Driver
    };
    private Role role;

    public Employee(String name, String id, String bank, int bankID, int bankBranch,
                    Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Role role){
        this.name = name;
        this.id = id;
        account = new BankAccount(bank, bankID, bankBranch);
        conditions = new WorkingConditions(employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed);
        this.role = role;
        availableShifts = new HashSet<>();
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public BankAccount getAccount() { return account; }

    public WorkingConditions getConditions() { return conditions; }

    public Set<WeeklyShift> getAvailableShifts() { return availableShifts; }

    public Role getRole() { return role;}

    public Employee setAvailableShifts(Set<WeeklyShift> shiftPreferences) {
        for (WeeklyShift shift:shiftPreferences) {
            availableShifts.add(shift);
        }
        return this;
    }

    public Employee addEmployeeShiftPreference(WeeklyShift shift){
        availableShifts.add(shift);
        return this;
    }

    public Employee deleteEmployeeShiftPreference(WeeklyShift shift){
        for(WeeklyShift s: availableShifts){
            if(s.type.equals(shift.type) && s.day.equals(shift.day))
                availableShifts.remove(s);
        }
        return this;
    }

    public boolean isShiftpreferred(WeeklyShift shift){
        for(WeeklyShift s: availableShifts){
            if(s.getDay().equals(shift.getDay()) && s.getType().equals(shift.getType()))
                return true;
        }
        return false;
    }





}
