package groupk.workers.service;

import groupk.workers.business.ServiceAdapter;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.*;

public class EmployeeService {
    private ServiceAdapter businessController;

    public EmployeeService(){
        businessController = new ServiceAdapter();
    }

    // Does not require authentication, so no subjectID.
    public Employee createEmployee(Employee toCreate) {
        return businessController.addEmployee(
                toCreate.name,
                toCreate.id,
                toCreate.bank,
                toCreate.bankID,
                toCreate.bankBranch,
                toCreate.employmentStart,
                toCreate.salaryPerHour,
                toCreate.sickDaysUsed,
                toCreate.vacationDaysUsed,
                toCreate.role);
    }

    public Shift createShift(String subjectID, Calendar date, Shift.Type type,
                          LinkedList<Employee> staff,
                          HashMap<Employee.Role, Integer> requiredStaff){
        return businessController.addShift(subjectID, date, type, staff ,requiredStaff);
    }

    public Employee readEmployee(String subjectID, String employeeID) {
        return businessController.readEmployee(subjectID, employeeID);
    }

    public Shift readShift(String subjectID, Calendar date ,Shift.Type type) {
        return businessController.readShift(subjectID, date ,type);
    }

    public Employee deleteEmployee(String subjectID, String employeeID) {
        return businessController.deleteEmployee(subjectID, employeeID);
    }
    public Employee updateEmployee(String subjectID, Employee changed) {
        return businessController.updateEmployee(subjectID, changed);
    }

    public Employee addEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        return businessController.addEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Employee setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.ShiftDateTime> shiftPreferences) {
        return businessController.setEmployeeShiftsPreference(subjectID, employeeID, shiftPreferences);
    }

    public Employee deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift){
        return businessController.deleteEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public List<Employee> listEmployees(String subjectID) {
        return businessController.listEmployees(subjectID);
    }

    public Shift addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return businessController.addEmployeeToShift(subjectID, date , type , employeeID);
    }
    public Shift removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return businessController.removeEmployeeFromShift(subjectID, date , type , employeeID);
    }
    public Shift setRequiredRoleInShift(String subjectID, Calendar date, Shift.Type type, Employee.Role role, int count) {
        throw new UnsupportedOperationException();
    }
    public List<Shift> listShifts(String subjectID) {
        return businessController.listShifts(subjectID);
    }
}
