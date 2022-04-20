package groupk.workers.service;

import groupk.workers.business.BusinessController;
import groupk.workers.business.EmployeeController;
import groupk.workers.business.ShiftController;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class EmployeeService {
    private BusinessController businessController;

    public EmployeeService(){
        businessController = new BusinessController();
    }

    public void addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                            Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role){
        businessController.getEmployeesController().addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role);
    }
    public void addShiftPreferences(String Id, Set<Employee.WeeklyShift> shiftPreferences){
        businessController.getEmployeesController().addShiftPreferences(Id, shiftPreferences);
    }

    public Employee createEmployee(Employee toCreate) {
        throw new UnsupportedOperationException();
    }
    public Employee readEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException();
    }
    public Employee deleteEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException();
    }
    public Employee updateEmployee(String subjectID, Employee changed) {
        throw new UnsupportedOperationException();
    }
    public Employee setEmployeeShiftPreference(String subjectID, String employeeID, Employee.WeeklyShift shift, boolean canWork) {
        throw new UnsupportedOperationException();
    }
    public List<Employee> listEmployees(String subjectID) {
        throw new UnsupportedOperationException();
    }

    public Shift addEmployeeToShift(String subjectID, Date date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException();
    }
    public Shift removeEmployeeFromShift(String subjectID, Date date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException();
    }
    public Shift setRequiredRoleInShift(String subjectID, Date date, Shift.Type type, Employee.Role role, int count) {
        throw new UnsupportedOperationException();
    }
    public List<Shift> listShifts(String subjectID) {
        throw new UnsupportedOperationException();
    }
}
