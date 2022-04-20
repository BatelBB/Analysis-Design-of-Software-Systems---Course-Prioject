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
