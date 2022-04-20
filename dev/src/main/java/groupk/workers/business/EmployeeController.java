package groupk.workers.business;

import groupk.workers.data.EmployeeRepository;
import groupk.workers.data.Employee;

import java.util.Date;
import java.util.List;

public class EmployeeController {
    private final EmployeeRepository repo;

    public EmployeeController(){
        repo = new EmployeeRepository();
    }

    public Employee create(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role){
        return repo.addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
    }

    public Employee read(String employeeID) {
        return repo.getEmployee(employeeID);
    }

    public Employee update(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Date employmentStart,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Employee.Role role
    ) {
        Employee changed = read(id);
        changed.setName(name);
        changed.getAccount().setBank(bank);
        changed.getAccount().setBankID(bankID);
        changed.getAccount().setBankBranch(bankBranch);
        changed.getConditions().setEmploymentStart(employmentStart);
        changed.getConditions().setSalaryPerHour(salaryPerHour);
        changed.getConditions().setSickDaysUsed(sickDaysUsed);
        changed.getConditions().setVacationDaysUsed(vacationDaysUsed);
        changed.setRole(role);
        return changed;
    }

    public Employee delete(String employeeID) {
        return repo.deleteEmployee(employeeID);
    }

    public List<Employee> list() {
        return  repo.getEmployees();
    }

    public boolean isFromHumanResources(String employeeID) {
        return repo.getEmployee(employeeID).getRole() == Employee.Role.HumanResources;
    }
}
