package groupk.workers.business;

import groupk.workers.data.Employee;
import groupk.workers.data.EmployeeRepository;

import java.util.Date;

public class EmployeeController {
    private EmployeeRepository repo;

    public EmployeeController(){
        repo = new EmployeeRepository();
    }

    public void AddEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, String role){
        repo.addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
    }
}
