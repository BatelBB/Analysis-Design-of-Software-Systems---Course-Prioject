package groupk.workers.business;

import groupk.workers.data.ShiftRepository;

import java.util.Date;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }

    public void addShifts(String typeString, Date date){
        repo.addShift(typeString, date);
    }
}
