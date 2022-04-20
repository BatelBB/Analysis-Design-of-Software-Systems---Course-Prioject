package groupk.workers.data;

import java.util.HashSet;
import java.util.Set;

public class ShiftRepository {
    private Set<Shift> shifts;

    public ShiftRepository(){
        shifts = new HashSet<>();
    }
    public Set<Shift> getShifts() {
        return shifts;
    }

}
