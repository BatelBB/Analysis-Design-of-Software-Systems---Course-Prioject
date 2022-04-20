package groupk.workers.data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Shift {
    private enum Type {morning, evening};
    private Type type;
    private Date date;
    private Set<Employee> staff;

    public Shift(String typeString, Date date){
        type = Type.valueOf(typeString);
        this.date = date;
        staff = new HashSet<>();
    }
    public Type getType() {
        return type;
    }
    public Date getDate() {return date; }
    public Set<Employee> getStaff() {return staff; }
}
