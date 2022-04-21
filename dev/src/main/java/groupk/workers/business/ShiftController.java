package groupk.workers.business;

import groupk.workers.data.Shift;
import groupk.workers.data.ShiftRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }

    public Shift addShifts(groupk.workers.data.Shift shift){
        if(!ifShiftExist(shift))
            return repo.addShift(shift);
        else
            throw new IllegalArgumentException("Shift already exists.");
    }

    public Shift getShift(Date date, Shift.Type type){
        LinkedList<Shift> shifts = repo.getShifts();
        for (Shift s: shifts) {
            if(s.getDate().equals(date) && s.getType().equals(type))
                return s;
        }
        throw new IllegalArgumentException("Shift does not exists.");
    }

    public boolean ifShiftExist(Shift shift){
        LinkedList<Shift> shifts = repo.getShifts();
        for (Shift s: shifts) {
            if(s.getDate().equals(shift.getDate()) && s.getType().equals(shift.getType()))
                return true;
        }
        return false;
    }

    public List<Shift> listShifts(){
        return new ArrayList<>(repo.getShifts());
    }



}
