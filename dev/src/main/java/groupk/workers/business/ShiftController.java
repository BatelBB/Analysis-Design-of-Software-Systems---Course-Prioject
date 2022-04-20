package groupk.workers.business;

import groupk.workers.data.ShiftRepository;
import groupk.workers.service.dto.Shift;

import java.util.Date;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }

    public void addShifts(Shift shift){
        repo.addShift(shift);
    }

}
