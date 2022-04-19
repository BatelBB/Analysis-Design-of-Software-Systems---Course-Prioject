package groupk.workers.data;

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
    }


    private String name;
    private String id;
    private BankAccount account;
    private WorkingConditions conditions;
    //private Unknown[] availableShifts;
    private enum Role{Logisitcs, HumanResources, Stocker,
                Cashier, LogisticsManager, Driver};
}
