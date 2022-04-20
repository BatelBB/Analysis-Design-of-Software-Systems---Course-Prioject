package groupk.workers.data;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private HashMap<String, Employee> employees;

    public EmployeeRepository(){
        employees = new HashMap<>();
    }
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    };

    public Employee getEmployee(String id){
        return employees.get(id);
    }

    public Employee addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role){
        Employee created = new Employee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
        employees.put(id, created);
        return created;
    }

    public Employee deleteEmployee(String id) {
        Employee deleted = employees.get(id);
        employees.remove(id);
        return deleted;
    }
}
