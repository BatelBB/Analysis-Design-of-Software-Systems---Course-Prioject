package groupk.workers.data;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.Date;
import java.util.Set;

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

        public Day getDay() {
            return day;
        }

        public Type getType() {
            return type;
        }
    }


    private String name;
    private String id;
    private BankAccount account;
    private WorkingConditions conditions;
    private Set<WeeklyShift> availableShifts;
    private enum Role{Logisitcs, HumanResources, Stocker,
                Cashier, LogisticsManager, Driver};
    private Role role;

    public Employee(String name, String id, String bank, int bankID, int bankBranch,
                    Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, String roleString){
        this.name = name;
        this.id = id;
        account = new BankAccount(bank, bankID, bankBranch);
        conditions = new WorkingConditions(employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed);
        this.role = Role.valueOf(roleString);
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public BankAccount getAccount() { return account; }

    public WorkingConditions getConditions() { return conditions; }

    public Set<WeeklyShift> getAvailableShifts() { return availableShifts; }

    public Role getRole() { return role;}

    public void setAvailableShifts(Set<WeeklyShift> availableShifts) {
        this.availableShifts = availableShifts;
    }
}
