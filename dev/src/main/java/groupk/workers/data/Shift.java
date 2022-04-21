package groupk.workers.data;
import java.util.*;

public class Shift {
    public enum Type {morning, evening};
    private Type type;
    private Calendar date;
    private LinkedList<Employee> staff;
    private HashMap<Employee.Role, Integer> requiredStaff;

    public Shift(Type type, Calendar date, HashMap<Employee.Role, Integer> requiredStaff){
        this.type = type;
        this.date = date;
        staff = new LinkedList<>();
        this.requiredStaff = requiredStaff;
    }

    public Shift(Calendar date, Type type, LinkedList<Employee> staff, HashMap<Employee.Role, Integer> requiredStaff){
        this.type = type;
        this.date = date;
        this.staff = staff;
        this.requiredStaff = requiredStaff;
    }

    public Type getType() {
        return type;
    }
    public Calendar getDate() {return date; }
    public LinkedList<Employee> getStaff() {return staff; }

    public HashMap<Employee.Role, Integer> getRequiredStaff() {
        return requiredStaff;
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
