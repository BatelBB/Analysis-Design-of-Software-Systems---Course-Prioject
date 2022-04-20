package groupk.workers.data;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class EmployeeRepository {
    private LinkedList<Employee> employees;

    public EmployeeRepository(){
        employees = new LinkedList<>();
    }
    public LinkedList<Employee> getEmployees() { return  employees;};

    public Employee getEmployee(String id){
        for (Employee e:employees) {
            if(e.getId().equals(id))
                return  e;
        }
        throw new IllegalArgumentException();
    }

    public Employee addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role){
        Employee created = new Employee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
        employees.add(created);
        return created;
    }
}
