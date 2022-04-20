package groupk.workers.data;
import java.util.Date;

public class Shift {
    private enum Type {morning, evening};
    private Type type;
    private Date date;
    private Employee[] staff;

    public Type getType() {
        return type;
    }
    public Date getDate() {return date; }
    public Employee[] getStaff() {return staff; }
}
