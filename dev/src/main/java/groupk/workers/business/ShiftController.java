package groupk.workers.business;

import groupk.workers.data.ShiftRepository;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }
}
