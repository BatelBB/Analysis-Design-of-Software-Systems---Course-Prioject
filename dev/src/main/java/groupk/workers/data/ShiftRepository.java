package groupk.workers.data;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ShiftRepository {
    private LinkedList<Shift> shifts;

    public ShiftRepository(){
        shifts = new LinkedList<>();
    }

    public LinkedList<Shift> getShifts() {
        return shifts;
    }

    public Shift addShift(Shift shift){
        shifts.add(shift);
        return  shift;
    }

    public Shift addShift(ResultSet shift, ResultSet requiredStaff, LinkedList<Employee> workers){
        Shift loadShift = new Shift(shift, requiredStaff, workers);
        shifts.add(loadShift);
        return loadShift;
    }
}
