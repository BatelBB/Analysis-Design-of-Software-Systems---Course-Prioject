package groupk.workers.business;

import java.util.HashMap;

public class BusinessController {
    private EmployeeController employeesController;
    private ShiftController shiftsController;

    public BusinessController(){
        employeesController = new EmployeeController();
        shiftsController = new ShiftController();
    }

    public EmployeeController getEmployeesController() {
        return employeesController;
    }

    public ShiftController getShiftsController() {
        return shiftsController;
    }
}
