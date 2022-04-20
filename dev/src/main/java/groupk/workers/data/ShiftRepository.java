package groupk.workers.data;

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

    public void addShift(Shift shift){
        shifts.add(new Shift(shift.getType() ,shift.getDate(), shift.getStaff(), shift.getRequiredStaff()));
    }
}
