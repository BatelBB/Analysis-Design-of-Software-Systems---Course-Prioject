package groupk.workers.business;

import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import javax.security.auth.Subject;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceAdapter {
    private EmployeeController employees;
    private ShiftController shifts;

    public ServiceAdapter() {
        employees = new EmployeeController();
        shifts = new ShiftController();
    }

    public Employee addEmployee(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Calendar employmentStart,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Employee.Role role) {
        groupk.workers.data.Employee created = employees.create(
                name, id, bank,
                bankID, bankBranch, employmentStart,
                salaryPerHour, sickDaysUsed, vacationDaysUsed,
                serviceRoleToData(role));
        return dataEmployeeToService(created);
    }

    public Shift addShift(String subjectID, Calendar date, Shift.Type type,
                          LinkedList<Employee> staff,
                          HashMap<Employee.Role, Integer> requiredStaff){
        if (employees.isFromHumanResources(subjectID)) {
            HashMap<groupk.workers.data.Employee.Role, Integer> requiredStaffData = new HashMap<>();
            requiredStaff.forEach((k, v) -> requiredStaffData.put(serviceRoleToData(k), v));
            LinkedList<groupk.workers.data.Employee> staffData = new LinkedList<>();
            for(Employee e: staff){
                staffData.add(serviceEmployeeToData(e));
            }
            groupk.workers.data.Shift created = new groupk.workers.data.Shift(date, ServiceTypeToData(type), staffData , requiredStaffData);
            return dataShiftToService(shifts.addShifts(created));
        } else {
            throw new IllegalArgumentException("Subject must be authorized to add shift.");
        }
    }

    public Employee readEmployee(String subjectID, String employeeID) {
        if (subjectID.equals(employeeID) || employees.isFromHumanResources(subjectID)) {
            return dataEmployeeToService(employees.read(employeeID));
        } else {
            throw new IllegalArgumentException("Subject must be authorized to read employees.");
        }
    }

    public Shift readShift(String subjectID, Calendar date ,Shift.Type type) {
        employees.getEmployee(subjectID); //checks if employee exist
        return dataShiftToService(shifts.getShift(date, ServiceTypeToData(type)));
    }

    public Employee deleteEmployee(String subjectID, String employeeID) {
        if (subjectID.equals(employeeID) || employees.isFromHumanResources(subjectID)) {
            return dataEmployeeToService(employees.delete(employeeID));
        } else {
            throw new IllegalArgumentException("Subject must be authorized to delete employees.");
        }
    }

    public List<Employee> listEmployees(String subjectID) {
        if (employees.isFromHumanResources(subjectID)) {
            return employees.list().stream()
                    .map(ServiceAdapter::dataEmployeeToService)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Subject must be authorized to read employees.");
        }
    }

    public Shift addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        if (employees.isFromHumanResources(subjectID)) {
            groupk.workers.data.Shift shift = shifts.getShift(date, ServiceTypeToData(type));
            if (shift.isEmployeeWorking(employeeID))
                throw new IllegalArgumentException("Employee already working in this shift.");
            else
                return dataShiftToService(shift.addEmployee(employees.getEmployee(employeeID)));
        } else {
            throw new IllegalArgumentException("Subject must be authorized to add employees to shifts.");
        }
    }

    public Shift removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        if (employees.isFromHumanResources(subjectID)) {
            groupk.workers.data.Shift shift = shifts.getShift(date, ServiceTypeToData(type));
            if (!shift.isEmployeeWorking(employeeID))
                throw new IllegalArgumentException("Employee is not working in this shift.");
            else
                return dataShiftToService(shift.removeEmployee(employees.getEmployee(employeeID)));
        } else {
            throw new IllegalArgumentException("Subject must be authorized to remove employees from shifts.");
        }
    }

    public Employee updateEmployee(String subjectID, Employee changed) {
        if (subjectID.equals(changed.id) || employees.isFromHumanResources(subjectID)) {
            return dataEmployeeToService(
                    employees.update(
                            changed.name,
                            changed.id,
                            changed.bank,
                            changed.bankID,
                            changed.bankBranch,
                            changed.employmentStart,
                            changed.salaryPerHour,
                            changed.sickDaysUsed,
                            changed.vacationDaysUsed,
                            serviceRoleToData(changed.role)
                    )
            );
        } else {
            throw new IllegalArgumentException("Subject must be authorized to update employees.");
        }
    }

    public Employee addEmployeeShiftPreference(String subjectID, String employeeID, Employee.WeeklyShift shift) {
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.addEmployeeShiftPreference(employeeID, ServiceWeeklyShiftToData(shift)));
        else
            throw new IllegalArgumentException("Employee can add only to himself shifts preferences.");
    }

    public Employee setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.WeeklyShift> shiftPreferences) {
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.setEmployeeShiftsPreference(employeeID, ServicePreferredShiftsToData(shiftPreferences)));
        else
            throw new IllegalArgumentException("Employee can add only to himself shifts preferences.");
    }

    public Employee deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.WeeklyShift shift){
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.deleteEmployeeShiftPreference(employeeID, ServiceWeeklyShiftToData(shift)));
        else
            throw new IllegalArgumentException("Employee can delete only to himself shifts preferences.");
    }

    public List<Shift> listShifts(String subjectID) {
        if(employees.isFromHumanResources(subjectID)) {
            List<groupk.workers.data.Shift> dataShifts = shifts.listShifts();
            List<Shift> dtoShifts = new LinkedList<>();
            for (groupk.workers.data.Shift s : dataShifts)
                dtoShifts.add(dataShiftToService(s));
            return dtoShifts;
        }
        throw new IllegalArgumentException("Subject must be authorized to get history of shifts.");
    }


    //private helper function




    private static groupk.workers.data.Employee.Role serviceRoleToData(Employee.Role serviceRole) {
        return groupk.workers.data.Employee.Role.values()[serviceRole.ordinal()];
    }

    private static Employee.Role dataRoleToService(groupk.workers.data.Employee.Role dataRole) {
        return Employee.Role.values()[dataRole.ordinal()];
    }

    private static Set<Employee.WeeklyShift> dataPreferredShiftsToService
            (Set<groupk.workers.data.Employee.WeeklyShift> dataShifts) {
        Set<Employee.WeeklyShift> preferredShifts = new HashSet<>();
        for (groupk.workers.data.Employee.WeeklyShift shift : dataShifts) {
            Employee.WeeklyShift serviceShift = new Employee.WeeklyShift(Employee.WeeklyShift.Day.values()[shift.day.ordinal()], Shift.Type.values()[shift.type.ordinal()]);
            preferredShifts.add(serviceShift);
        }
        return preferredShifts;
    }

    private static Set<groupk.workers.data.Employee.WeeklyShift> ServicePreferredShiftsToData
            (Set<Employee.WeeklyShift> dtoShifts) {
        Set<groupk.workers.data.Employee.WeeklyShift> preferredShifts = new HashSet<>();
        for (Employee.WeeklyShift shift : dtoShifts) {
            groupk.workers.data.Employee.WeeklyShift dataShift =
                    new groupk.workers.data.Employee.WeeklyShift(shift.day.ordinal(), shift.type.ordinal());
            preferredShifts.add(dataShift);
        }
        return preferredShifts;
    }

    private static groupk.workers.data.Employee.WeeklyShift ServiceWeeklyShiftToData(Employee.WeeklyShift dtoShift) {
        return new groupk.workers.data.Employee.WeeklyShift(dtoShift.day.ordinal(), dtoShift.type.ordinal());
    }

    private static Employee dataEmployeeToService(groupk.workers.data.Employee dataEmployee) {
        return new Employee(
                dataEmployee.getId(),
                dataEmployee.getName(),
                dataRoleToService(dataEmployee.getRole()),
                dataEmployee.getAccount().getBank(),
                dataEmployee.getAccount().getBankID(),
                dataEmployee.getAccount().getBankBranch(),
                dataEmployee.getConditions().getSalaryPerHour(),
                dataEmployee.getConditions().getSickDaysUsed(),
                dataEmployee.getConditions().getVacationDaysUsed(),
                dataPreferredShiftsToService(dataEmployee.getAvailableShifts()),
                dataEmployee.getConditions().getEmploymentStart()
        );
    }

    private groupk.workers.data.Employee serviceEmployeeToData(Employee serviceEmployee) {
        return employees.read(serviceEmployee.id);
    }

    private static Shift.Type dataTypeToService(groupk.workers.data.Shift.Type dataType) {
        return Shift.Type.values()[dataType.ordinal()];
    }

    private static groupk.workers.data.Shift.Type ServiceTypeToData(Shift.Type dataType) {
        return groupk.workers.data.Shift.Type.values()[dataType.ordinal()];
    }


    private static Shift dataShiftToService(groupk.workers.data.Shift dataShift) {
        HashMap<Employee.Role, Integer> staffDto = new HashMap<>();
        dataShift.getRequiredStaff().forEach((k, v) -> staffDto.put(dataRoleToService(k), v));
        return new Shift(
                dataShift.getDate(),
                dataTypeToService(dataShift.getType()),
                dataShift.getStaff().stream().map(ServiceAdapter::dataEmployeeToService).collect(Collectors.toList()),
                staffDto);
    }
}


