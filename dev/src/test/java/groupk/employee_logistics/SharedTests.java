package groupk.employee_logistics;
import groupk.shared.service.dto.*;
import groupk.shared.service.*;

import groupk.workers.business.Facade;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SharedTests {

    @Test
    public void deleteLogisticsDB(){

    }

    @Test
    public void deleteEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee created = service.createEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                availableShifts
        ).getValue();
        Employee HR = service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        ).getValue();
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime s : Employee.ShiftDateTime.values())
            shiftPreferences.add(s);
        Employee SM = service.createEmployee(
                "SM",
                "11411110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.ShiftManager,
                shiftPreferences
        ).getValue();
        LinkedList<Employee> em = new LinkedList<>();
        em.add(SM);
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        r.replace(Employee.Role.ShiftManager, 1);
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, em, r).getValue();
        service.deleteEmployeeDB();
        service = new Service();
        service.loadEmployeeDB();
        service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        ).getValue();
        assertEquals(service.listShifts("111111110").getValue().size(), 0);
        assertEquals(service.listEmployees("111111110").getValue().size(), 1);
    }

    @Test
    public void loadLogisticsDB(){

    }

    @Test
    public void loadEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee created = service.createEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                availableShifts
        ).getValue();
        Employee HR = service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        ).getValue();
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime s : Employee.ShiftDateTime.values())
            shiftPreferences.add(s);
        Employee SM = service.createEmployee(
                "SM",
                "11411110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.ShiftManager,
                shiftPreferences
        ).getValue();
        LinkedList<Employee> em = new LinkedList<>();
        em.add(SM);
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        r.replace(Employee.Role.ShiftManager, 1);
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, em, r).getValue();
        service = new Service();
        service.loadEmployeeDB();
        assertEquals(service.listShifts("111111110").getValue().size(), 1);
        assertEquals(service.listShifts("111111110").getValue().get(0).getDate(), shift.getDate());
        assertEquals(service.listEmployees("111111110").getValue().size(), 3);
    }

    @Test
    public void updateLogisticsDB(){

    }

    @Test
    public void UpdateEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee created = service.createEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                availableShifts
        ).getValue();
        Employee HR = service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        ).getValue();
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime s : Employee.ShiftDateTime.values())
            shiftPreferences.add(s);
        Employee SM = service.createEmployee(
                "SM",
                "11411110",
                "FooBank",
                1, 1,
                new GregorianCalendar(2022, Calendar.JANUARY, 5),
                30,
                0, 0,
                Employee.Role.ShiftManager,
                shiftPreferences
        ).getValue();
        LinkedList<Employee> em = new LinkedList<>();
        em.add(SM); em.add(created);
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        r.replace(Employee.Role.ShiftManager, 1);
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, em, r).getValue();
        Employee createdUpdate = new Employee(
                "111111111",
                "Foo2",
                Employee.Role.TruckingManger,
                "FooBank2",
                12, 12,
                302,
                2, 2,
                availableShifts,
                new GregorianCalendar(2022, Calendar.APRIL, 5)
        );
        service.updateEmployee(HR.id, createdUpdate);
        service = new Service();
        service.loadEmployeeDB();
        assertEquals(service.readEmployee("111111110", "111111111").getValue().name, createdUpdate.name);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().role, createdUpdate.role);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().bankBranch, createdUpdate.bankBranch);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().bank, createdUpdate.bank);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().bankID, createdUpdate.bankID);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().salaryPerHour, createdUpdate.salaryPerHour);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().vacationDaysUsed, createdUpdate.vacationDaysUsed);
        assertEquals(service.readEmployee("111111110", "111111111").getValue().sickDaysUsed, createdUpdate.sickDaysUsed);
        assertEquals(service.listShifts(HR.id).getValue().size(), 1);
        assertEquals(service.listShifts(HR.id).getValue().get(0).getStaff().size(), 2);
        //service.deleteEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.ThursdayEvening);
        service.deleteEmployee(HR.id, "11411110");
    }
    @Test
    public void testCreateDeliveryWithLogisticsEmployee() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee HR = (service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                shiftPreferences)).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        //assertEquals();
    }
    @Test
    public void testCreateDeliveryWithoutLogisticsEmployee() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee HR = (service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                shiftPreferences)).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        //assertTrue(); must be an error
    }

    @Test
    public void testAddDriverNotWorking() {
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee HR = (service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>())).getValue();
        Employee driver = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                new HashSet<>()).getValue();

        //assertTrue(); must be an error
    }

    @Test
    public void testAddDriverInShift() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        Service service = new Service();
        service.deleteEmployeeDB();
        Employee HR = (service.createEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                shiftPreferences)).getValue();
        Employee SM = (service.createEmployee(
                "Foo",
                "111121110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.ShiftManager,
                shiftPreferences)).getValue();
        Employee driver = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        HashMap<Employee.Role, Integer> requiredStaff = new HashMap<Employee.Role, Integer>();
        for(Employee.Role role : Employee.Role.values())
            requiredStaff.put(role, 0);
        requiredStaff.replace(Employee.Role.ShiftManager, 1);
        LinkedList<Employee> employees = new LinkedList<>();
        employees.add(SM); employees.add(driver);
        Shift shift = service.createShift(HR.id, new GregorianCalendar(), Shift.Type.Evening,employees, requiredStaff).getValue();
        //assertEquals();
    }








}
