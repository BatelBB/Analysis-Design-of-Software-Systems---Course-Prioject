package groupk.workers.data;

import javax.management.relation.RoleInfoNotFoundException;

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
    private WeeklyShift[] availableShifts;
    private enum Role{Logisitcs, HumanResources, Stocker,
                Cashier, LogisticsManager, Driver};
    private Role role;

    public String getId() { return id; }

    public String getName() { return name; }

    public BankAccount getAccount() { return account; }

    public WorkingConditions getConditions() { return conditions; }

    public WeeklyShift[] getAvailableShifts() { return availableShifts; }

    public Role getRole() { return role;}
}
