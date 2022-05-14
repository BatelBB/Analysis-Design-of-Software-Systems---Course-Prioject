package groupk.workers.business;


import groupk.workers.data.DalController;
import groupk.workers.data.Employee;
import groupk.workers.data.EmployeeRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EmployeeController {
    private DalController dalColntroller;

    public EmployeeController(DalController dalColntroller){
        this.dalColntroller = dalColntroller;
    }

    public Employee create(String name, String id, String bank, int bankID, int bankBranch,
                           Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role, Set<Employee.ShiftDateTime> shiftPreferences){
        return dalColntroller.addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role, shiftPreferences);
    }

    public Employee read(String employeeID) {
        return dalColntroller.getEmployee(employeeID);
    }

    public Employee update(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Calendar employmentStart,
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
        return dalColntroller.deleteEmployee(employeeID);
    }

    public List<Employee> list() {
        return  dalColntroller.getEmployees();
    }

    public boolean isFromHumanResources(String employeeID) {
        return getEmployee(employeeID).getRole() == Employee.Role.HumanResources;
    }

    public Employee getEmployee(String id){
        List<Employee> employees = list();
        for(Employee e: employees){
            if(e.getId().equals(id)){
                return e;
            }
        }
        throw new IllegalArgumentException("Employee does not exists.");
    }


    public Employee setEmployeeShiftsPreference(String id, Set<Employee.ShiftDateTime> shiftPreferences) {
        Employee e = getEmployee(id);
        if(e.getAvailableShifts().size() == 0){
            e.setAvailableShifts(shiftPreferences);
        }
        else {
            for (Employee.ShiftDateTime shift : shiftPreferences) {
                if (!e.isShiftpreferred(shift))
                    return getEmployee(id).addEmployeeShiftPreference(shift);
                throw new IllegalArgumentException("This shift already exist in employee preference.");
            }
        }
        return e;
    }

    public Employee addEmployeeShiftPreference(String id, Employee.ShiftDateTime shift){
        Employee e = getEmployee(id);
        if(!e.isShiftpreferred(shift))
            return getEmployee(id).addEmployeeShiftPreference(shift);
        throw new IllegalArgumentException("This shift already exist in employee preference.");
    }

    public Employee deleteEmployeeShiftPreference(String id, Employee.ShiftDateTime shift){
        Employee e = getEmployee(id);
        if(e.isShiftpreferred(shift))
            return getEmployee(id).deleteEmployeeShiftPreference(shift);
        throw new IllegalArgumentException("This shift does not exist in employee preference.");

    }
}
