package groupk.workers.service;

import groupk.workers.business.BusinessController;
import groupk.workers.business.EmployeeController;
import groupk.workers.business.ShiftController;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.Date;
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

    public void addShifts(Shift shift){
        businessController.getShiftsController().addShifts(shift);
    }
}
