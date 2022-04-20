package groupk.workers.business;

public class BusinessController {
    private EmployeeController employees;
    private ShiftController shifts;

    public BusinessController(){
        employees = new EmployeeController();
        shifts = new ShiftController();
    }
}
