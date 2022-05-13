package groupk.shared.business;

import groupk.shared.service.Response;
import groupk.shared.service.dto.Employee;
import groupk.shared.service.dto.Shift;

import java.util.*;

public class EmployeesController {
    public Employee createEmployee(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Calendar employmentStart,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Employee.Role role,
            Set<Employee.ShiftDateTime> shiftPreferences) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> createShift(String subjectID, Calendar date, Shift.Type type,
                                       LinkedList<Employee> staff,
                                       HashMap<Employee.Role, Integer> requiredStaff){
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> readEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> readShift(String subjectID, Calendar date , Shift.Type type) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> deleteEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<List<Employee>> listEmployees(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> updateEmployee(String subjectID, Employee changed) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> updateEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift, boolean canWork) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<List<Shift>> listShifts(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> updateRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        throw new UnsupportedOperationException("TODO");
    }
}
