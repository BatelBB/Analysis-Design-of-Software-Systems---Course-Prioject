package groupk.workers.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EmployeeRepository {
    private Set<Employee> employees;

    public EmployeeRepository(){
        employees = new HashSet<>();
    }
    public Set<Employee> getEmployees() { return  employees;};
    public void addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, String role){
        employees.add(new Employee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role));
    }
}
