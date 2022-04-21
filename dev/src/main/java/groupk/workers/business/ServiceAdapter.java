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
            Employee.Role role,
            Set<Employee.ShiftDateTime> shiftPreferences) {
        groupk.workers.data.Employee created = employees.create(
                name, id, bank,
                bankID, bankBranch, employmentStart,
                salaryPerHour, sickDaysUsed, vacationDaysUsed,
                serviceRoleToData(role),
                servicePreferredShiftsToData(shiftPreferences)
                );
        return dataEmployeeToService(created);
    }

    public Shift addShift(String subjectID, Calendar date, Shift.Type type,
                          LinkedList<Employee> staff,
                          HashMap<Employee.Role, Integer> requiredStaff){
        if (employees.isFromHumanResources(subjectID)) {
            LinkedList<groupk.workers.data.Employee> staffData = new LinkedList<>();
            for(Employee e: staff){
                staffData.add(serviceEmployeeToData(e));
            }
            groupk.workers.data.Shift created = new groupk.workers.data.Shift(date, serviceTypeToData(type), staffData ,
                    serviceRequiredRoleInShiftToData(requiredStaff));
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
        return dataShiftToService(shifts.getShift(date, serviceTypeToData(type)));
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
            groupk.workers.data.Shift shift = shifts.getShift(date, serviceTypeToData(type));
            if (shift.isEmployeeWorking(employeeID)) {
                throw new IllegalArgumentException("Employee already working in this shift.");
            }
            groupk.workers.data.Employee added = employees.read(employeeID);
            if (!added.getAvailableShifts().contains(groupk.workers.data.Employee.toShiftDateTime(date, type == Shift.Type.Evening))) {
                throw new IllegalArgumentException("Employee must be able to work at day of week and time.");
            }
            if(shift.getRequiredStaff().get(added.getRole()) >
                    (shift.getStaff().stream().filter(p -> p.getRole().equals(added.getRole())).collect(Collectors.toList())).size())
                return dataShiftToService(shift.addEmployee(employees.getEmployee(employeeID)));
            else
                throw new IllegalArgumentException("There is enough employees with this role.");
        } else {
            throw new IllegalArgumentException("Subject must be authorized to add employees to shifts.");
        }
    }

    public Shift removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        if (employees.isFromHumanResources(subjectID)) {
            groupk.workers.data.Shift shift = shifts.getShift(date, serviceTypeToData(type));
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

    public Employee addEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.addEmployeeShiftPreference(employeeID, serviceWeeklyShiftToData(shift)));
        else
            throw new IllegalArgumentException("Employee can add only to himself shifts preferences.");
    }

    public Employee setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.ShiftDateTime> shiftPreferences) {
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.setEmployeeShiftsPreference(employeeID, servicePreferredShiftsToData(shiftPreferences)));
        else
            throw new IllegalArgumentException("Employee can add only to himself shifts preferences.");
    }

    public Employee deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift){
        if(subjectID.equals(employeeID))
            return dataEmployeeToService(employees.deleteEmployeeShiftPreference(employeeID, serviceWeeklyShiftToData(shift)));
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

    public Shift setRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        if(employees.isFromHumanResources(subjectId))
            return dataShiftToService(shifts.setRequiredRoleInShift(date, serviceTypeToData(type) ,serviceRoleToData(role), count));
        else
            throw new IllegalArgumentException("Subject must be authorized to set required roles in shifts.");
    }

    public Shift setRequiredStaffInShift(String subjectId, Calendar date, Shift.Type type, HashMap<Employee.Role, Integer> requiredStaff) {
        if(employees.isFromHumanResources(subjectId))
            return dataShiftToService(shifts.setRequiredStaffInShift(date, serviceTypeToData(type) ,serviceRequiredRoleInShiftToData(requiredStaff)));
        else
            throw new IllegalArgumentException("Subject must be authorized to set required roles in shifts.");
    }

    //private helper function


    private static groupk.workers.data.Employee.Role serviceRoleToData(Employee.Role serviceRole) {
        return groupk.workers.data.Employee.Role.values()[serviceRole.ordinal()];
    }

    private static Employee.Role dataRoleToService(groupk.workers.data.Employee.Role dataRole) {
        return Employee.Role.values()[dataRole.ordinal()];
    }

    private static Set<Employee.ShiftDateTime> dataPreferredShiftsToService
            (Set<groupk.workers.data.Employee.ShiftDateTime> dataShifts) {
        Set<Employee.ShiftDateTime> preferredShifts = new HashSet<>();
        for (groupk.workers.data.Employee.ShiftDateTime shift : dataShifts) {
            preferredShifts.add(Employee.ShiftDateTime.values()[shift.ordinal()]);
        }
        return preferredShifts;
    }

    private static Set<groupk.workers.data.Employee.ShiftDateTime> servicePreferredShiftsToData
            (Set<Employee.ShiftDateTime> dtoShifts) {
        Set<groupk.workers.data.Employee.ShiftDateTime> preferredShifts = new HashSet<>();
        for (Employee.ShiftDateTime shift : dtoShifts) {
            preferredShifts.add(groupk.workers.data.Employee.ShiftDateTime.values()[shift.ordinal()]);
        }
        return preferredShifts;
    }

    private static groupk.workers.data.Employee.ShiftDateTime serviceWeeklyShiftToData(Employee.ShiftDateTime dtoShift) {
        return groupk.workers.data.Employee.ShiftDateTime.values()[dtoShift.ordinal()];
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

    private static groupk.workers.data.Shift.Type serviceTypeToData(Shift.Type dataType) {
        return groupk.workers.data.Shift.Type.values()[dataType.ordinal()];
    }


    private static Shift dataShiftToService(groupk.workers.data.Shift dataShift) {
        return new Shift(
                dataShift.getDate(),
                dataTypeToService(dataShift.getType()),
                dataShift.getStaff().stream().map(ServiceAdapter::dataEmployeeToService).collect(Collectors.toList()),
                dataRequiredRoleInShiftToService(dataShift.getRequiredStaff()));
    }

    private static HashMap<Employee.Role, Integer> dataRequiredRoleInShiftToService(HashMap<groupk.workers.data.Employee.Role, Integer> staffData) {
        HashMap<Employee.Role, Integer> staffDto = new HashMap<>();
        staffData.forEach((k, v) -> staffDto.put(dataRoleToService(k), v));
        return staffDto;
    }

    private static HashMap<groupk.workers.data.Employee.Role, Integer> serviceRequiredRoleInShiftToData(HashMap<Employee.Role, Integer> staffDto) {
        HashMap<groupk.workers.data.Employee.Role, Integer> staffData = new HashMap<>();
        staffDto.forEach((k, v) -> staffData.put(serviceRoleToData(k), v));
        return staffData;
    }
}


