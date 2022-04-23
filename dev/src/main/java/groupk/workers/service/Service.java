package groupk.workers.service;

import groupk.workers.business.Facade;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.*;

public class Service {
    private Facade facade;

    public Service(){
        facade = new Facade();
    }

    // Does not require authentication, so no subjectID.
    public Employee createEmployee(Employee toCreate) {
        return facade.addEmployee(
                toCreate.name,
                toCreate.id,
                toCreate.bank,
                toCreate.bankID,
                toCreate.bankBranch,
                toCreate.employmentStart,
                toCreate.salaryPerHour,
                toCreate.sickDaysUsed,
                toCreate.vacationDaysUsed,
                toCreate.role,
                toCreate.shiftPreferences);
    }

    public Shift createShift(String subjectID, Calendar date, Shift.Type type,
                          LinkedList<Employee> staff,
                          HashMap<Employee.Role, Integer> requiredStaff){
        return facade.addShift(subjectID, date, type, staff ,requiredStaff);
    }

    public Employee readEmployee(String subjectID, String employeeID) {
        return facade.readEmployee(subjectID, employeeID);
    }

    public Shift readShift(String subjectID, Calendar date ,Shift.Type type) {
        return facade.readShift(subjectID, date ,type);
    }

    public Employee deleteEmployee(String subjectID, String employeeID) {
        return facade.deleteEmployee(subjectID, employeeID);
    }
    public Employee updateEmployee(String subjectID, Employee changed) {
        return facade.updateEmployee(subjectID, changed);
    }

    public Employee addEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        return facade.addEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Employee setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.ShiftDateTime> shiftPreferences) {
        return facade.setEmployeeShiftsPreference(subjectID, employeeID, shiftPreferences);
    }

    public Employee deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift){
        return facade.deleteEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public List<Employee> listEmployees(String subjectID) {
        return facade.listEmployees(subjectID);
    }

    public Shift addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return facade.addEmployeeToShift(subjectID, date , type , employeeID);
    }
    public Shift removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return facade.removeEmployeeFromShift(subjectID, date , type , employeeID);
    }
    public Shift setRequiredRoleInShift(String subjectID, Calendar date, Shift.Type type, Employee.Role role, int count) {
        return facade.setRequiredRoleInShift(subjectID, date, type, role, count);
    }
    public Shift setRequiredStaffInShift(String subjectID, Calendar date, Shift.Type type, HashMap<Employee.Role, Integer> staff) {
        return facade.setRequiredStaffInShift(subjectID, date, type, staff);
    }
    public List<Shift> listShifts(String subjectID) {
        return facade.listShifts(subjectID);
    }

    public List<Employee> WhoCanWorkWithRole(String subjectId, Employee.ShiftDateTime day, Employee.Role role){
        return facade.WhoCanWorkWithRole(subjectId, day, role);
    }

    public List<Employee> WhoCanWork(String subjectId, Employee.ShiftDateTime day) {
        return facade.WhoCanWork(subjectId, day);
    }

    public int numOfShifts(String subjectId, String employeeID){
        return facade.numOfShifts(subjectId, employeeID);
    }
}
