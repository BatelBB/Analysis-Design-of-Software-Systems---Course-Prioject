package groupk.workers.data;
import java.util.*;

public class Shift {
    public enum Type {morning, evening};
    private Type type;
    private Calendar date;
    private LinkedList<Employee> staff;
    private HashMap<Employee.Role, Integer> requiredStaff;

    public Shift(Calendar date, Type type, LinkedList<Employee> staff, HashMap<Employee.Role, Integer> requiredStaff){
        this.type = type;
        this.date = date;
        this.staff = staff;
        this.requiredStaff = new HashMap<>();
        for (Employee.Role r : Employee.Role.values()) {
            if(requiredStaff.containsKey(r))
                this.requiredStaff.put(r, requiredStaff.get(r));
            else {
                if(type.equals(Type.morning))
                    this.requiredStaff.put(r, 1);
                else{
                    if(!(r.equals(Employee.Role.HumanResources)|r.equals(Employee.Role.LogisticsManager)|r.equals(Employee.Role.StoreManager)))
                        this.requiredStaff.put(r, 1);
                    else
                        this.requiredStaff.put(r, 0);
                }
            }
        }
    }

    public Type getType() {
        return type;
    }
    public Calendar getDate() {return date; }
    public LinkedList<Employee> getStaff() {return staff; }

    public HashMap<Employee.Role, Integer> getRequiredStaff() {
        return requiredStaff;
    }

    public Shift setRequiredStaff(HashMap<Employee.Role, Integer> requiredStaff){
        for (Employee.Role r : requiredStaff.keySet())
            this.requiredStaff.put(r, requiredStaff.get(r));
        return this;
    }

    public Shift setRequiredRoleInShift(Employee.Role role, int number){
        requiredStaff.replace(role, number);
        return this;
    }

    public boolean isEmployeeWorking(String id){
        for (Employee e : staff){
            if(e.getId().equals(id))
                return true;
        }
        return false;
    }

    public Shift addEmployee(Employee e){
        staff.add(e);
        return this;
    }

    public Shift removeEmployee(Employee e){
        staff.remove(e);
        return this;
    }
}
