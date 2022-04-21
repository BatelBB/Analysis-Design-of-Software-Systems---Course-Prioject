package groupk.workers.business;

import groupk.workers.data.Shift;
import groupk.workers.data.ShiftRepository;

import java.util.Date;
import java.util.LinkedList;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }

    public void addShifts(groupk.workers.data.Shift shift){
        repo.addShift(shift);
    }

    public groupk.workers.data.Shift getShift(Date date, Shift.Type type){
        LinkedList<groupk.workers.data.Shift> shifts = repo.getShifts();
        for (groupk.workers.data.Shift s: shifts) {
            if(s.getDate().equals(date) && s.getType().equals(type))
                return s;
        }
        throw new IllegalArgumentException("Shift does not exists");
    }



}
