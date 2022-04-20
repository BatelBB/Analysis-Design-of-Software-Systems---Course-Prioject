package groupk.workers.business;

import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BusinessController {
    private EmployeeController employees;
    private ShiftController shifts;

    public BusinessController(){
        employees = new EmployeeController();
        shifts = new ShiftController();
    }

    public Employee addEmployee(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Date employmentStart,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Employee.Role role) {
        groupk.workers.data.Employee created = employees.addEmployee(
                name, id, bank,
                bankID, bankBranch, employmentStart,
                salaryPerHour, sickDaysUsed, vacationDaysUsed,
                serviceRoleToData(role));
        return dataEmployeeToService(created);
    }

    private static groupk.workers.data.Employee.Role serviceRoleToData(Employee.Role serviceRole) {
        return groupk.workers.data.Employee.Role.values()[serviceRole.ordinal()];
    }

    private static Employee.Role dataRoleToService(groupk.workers.data.Employee.Role dataRole) {
        return Employee.Role.values()[dataRole.ordinal()];
    }

    private static Set<Employee.WeeklyShift> dataPreferredShiftsToService(Set<groupk.workers.data.Employee.WeeklyShift> dataShifts) {
        Set<Employee.WeeklyShift> preferredShifts = new HashSet<>();
        for (groupk.workers.data.Employee.WeeklyShift shift: dataShifts) {
            Employee.WeeklyShift serviceShift = new Employee.WeeklyShift();
            serviceShift.day = Employee.WeeklyShift.Day.values()[shift.day.ordinal()];
            serviceShift.type = Shift.Type.values()[shift.type.ordinal()];
            preferredShifts.add(serviceShift);
        }
        return preferredShifts;
    }

    private static Employee dataEmployeeToService(groupk.workers.data.Employee dataEmployee) {
        Employee serviceEmployee = new Employee();
        serviceEmployee.id = dataEmployee.getId();
        serviceEmployee.name = dataEmployee.getName();
        serviceEmployee.role = dataRoleToService(dataEmployee.getRole());
        serviceEmployee.bank = dataEmployee.getAccount().bank;
        serviceEmployee.bankBranch = dataEmployee.getAccount().bankBranch;
        serviceEmployee.bankID = dataEmployee.getAccount().bankID;
        serviceEmployee.salaryPerHour = dataEmployee.getConditions().getSalaryPerHour();
        serviceEmployee.sickDaysUsed = dataEmployee.getConditions().getSickDaysUsed();
        serviceEmployee.vacationDaysUsed = dataEmployee.getConditions().getVacationDaysUsed();
        serviceEmployee.employmentStart = dataEmployee.getConditions().getEmploymentStart();
        serviceEmployee.shiftPreferences = dataPreferredShiftsToService(dataEmployee.getAvailableShifts());
        return serviceEmployee;
    }
}
