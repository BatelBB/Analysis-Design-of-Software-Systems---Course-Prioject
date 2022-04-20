package groupk.workers.data;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Shift {
    private enum Type {morning, evening};
    private Type type;
    private Date date;
    private LinkedList<Employee> staff;

    public Shift(int typeindex, Date date){
        type = Type.values()[typeindex];
        this.date = date;
        staff = new LinkedList<>();
    }
    public Type getType() {
        return type;
    }
    public Date getDate() {return date; }
    public LinkedList<Employee> getStaff() {return staff; }
}
