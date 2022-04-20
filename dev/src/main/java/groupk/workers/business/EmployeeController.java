package groupk.workers.business;

import groupk.workers.data.EmployeeRepository;
import groupk.workers.data.Employee;

import java.util.Date;
import java.util.Set;

public class EmployeeController {
    private EmployeeRepository repo;

    public EmployeeController(){
        repo = new EmployeeRepository();
    }

    public Employee addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role){
        return repo.addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
    }
}
