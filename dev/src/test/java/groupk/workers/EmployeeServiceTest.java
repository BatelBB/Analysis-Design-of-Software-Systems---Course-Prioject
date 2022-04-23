package groupk.workers;

import static org.junit.jupiter.api.Assertions.*;

import groupk.workers.service.EmployeeService;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeServiceTest {
    @Test
    public void testCreateShiftMorning()
    {
        EmployeeService service = new EmployeeService();
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Shift shift = service.createShift(HR.id, new GregorianCalendar(),Shift.Type.Morning, new LinkedList<>(), new HashMap<>());
        assertEquals(service.listShifts(HR.id).size(), 1);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().size(),8);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.StoreManager),1);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.LogisticsManager),1);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.HumanResources),1);
    }

    @Test
    public void testCreateShiftEvening()
    {
        EmployeeService service = new EmployeeService();
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Shift shift = service.createShift(HR.id, new GregorianCalendar(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        assertEquals(service.listShifts(HR.id).size(), 1);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().size(),8);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.StoreManager),0);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.LogisticsManager),0);
        assertEquals(service.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.HumanResources),0);
    }

    @Test
    public void testCreateShiftNotUnauthorized()
    {
        EmployeeService service = new EmployeeService();
        Employee NotHR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.createShift(NotHR.id, new GregorianCalendar(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        });
    }

    @Test
    public void testCreateEmployee()
    {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        List<Employee> list = service.listEmployees("111111111");
        assertEquals(1, list.size());
        assertEquals("111111111", list.get(0).id);
        assertEquals("Foo", list.get(0).name);
    }

    @Test
    public void testCreateEmployeeSameID() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.createEmployee(new Employee(
                "111111111",
                "Bar",
                Employee.Role.HumanResources,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
            ));
        });
        List<Employee> list = service.listEmployees("111111111");
        assertEquals(1, list.size());
    }

    @Test
    public void testReadEmployeesUnauthorized() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.listEmployees("111111111");
        });
    }

    @Test
    public void testDeleteEmployee() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.HumanResources,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.deleteEmployee("111111111", "111111111");
        List<Employee> list = service.listEmployees("222222222");
        assertEquals(1, list.size());
    }

    @Test
    public void testDeleteEmployeeByHR() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.HumanResources,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.deleteEmployee("222222222", "111111111");
        List<Employee> list = service.listEmployees("222222222");
        assertEquals(1, list.size());
    }

    @Test
    public void testDeleteEmployeeUnauthorized() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.deleteEmployee("111111111", "999999999");
        });
    }

    @Test
    public void testReadEmployee() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee self = service.readEmployee("111111111", "111111111");
        assertEquals("111111111", self.id);
        assertEquals("Foo", self.name);
    }

    @Test
    public void testReadEmployeeByHR() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.HumanResources,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee self = service.readEmployee("222222222", "111111111");
        assertEquals("111111111", self.id);
        assertEquals("Foo", self.name);
    }

    @Test
    public void testReadEmployeeUnauthorized() {
        EmployeeService service = new EmployeeService();
        service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.Cashier,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.readEmployee("222222222", "111111111");
        });
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        created.name = "Changed";
        service.updateEmployee(created.id, created);
        Employee self = service.readEmployee("111111111", "111111111");
        assertEquals("Changed", self.name);
    }

    @Test
    public void testUpdateEmployeeByHR() {
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.HumanResources,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        created.name = "Changed";
        service.updateEmployee("222222222", created);
        Employee foo = service.readEmployee("222222222", "111111111");
        assertEquals("Changed", foo.name);
    }

    @Test
    public void testUpdateEmployeeUnauthorized() {
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.createEmployee(new Employee(
                "222222222",
                "Bar",
                Employee.Role.Cashier,
                "BarBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.updateEmployee("222222222", created);
        });
    }

    @Test
    public void testAddEmployeeShiftPreference(){
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayMorning);
        Employee.ShiftDateTime createdShift = service.readEmployee(created.id, created.id).shiftPreferences.iterator().next();
        assertEquals(Employee.ShiftDateTime.FridayMorning, createdShift);
    }

    @Test
    public void testAddEmployeeShiftPreferenceFromAnotherId(){
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee created2 = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        assertThrows(Exception.class, () -> {
            service.addEmployeeShiftPreference(created2.id, created.id, Employee.ShiftDateTime.FridayMorning);
        });
    }

    @Test
    public void testDeleteEmployeeShiftPreference(){
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayMorning);
        service.deleteEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayMorning);
        assertEquals(0, service.readEmployee(created.id, created.id).shiftPreferences.size());
    }

    @Test
    public void testSetEmployeeShiftsPreference(){
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Set<Employee.ShiftDateTime> set = new HashSet<>();
        set.add(Employee.ShiftDateTime.FridayMorning);
        set.add(Employee.ShiftDateTime.MondayEvening);
        service.setEmployeeShiftsPreference(created.id, created.id, set);
        assertEquals(2, service.readEmployee(created.id, created.id).shiftPreferences.size());
    }

    @Test
    public void testAddEmployeeToShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
    }

    @Test
    public void testAddEmployeeToShiftWithRistrictionsOnRoles(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee created2 = service.createEmployee(new Employee(
                "011111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.setRequiredRoleInShift(HR.id, shift.getDate(), shift.getType(), Employee.Role.Stocker, 2);
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created2.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 2);
    }

    @Test
    public void testsetRequiredStaffInShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Cashier,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee created2 = service.createEmployee(new Employee(
                "111100111",
                "Foo",
                Employee.Role.Cashier,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee created3 = service.createEmployee(new Employee(
                "111111100",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        r.put(Employee.Role.Cashier, 2);
        r.put(Employee.Role.Stocker, 0);
        service.setRequiredStaffInShift(HR.id, shift.getDate(), shift.getType(), r);
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created2.id);;
        assertThrows(Exception.class, () -> {
            service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created3.id);
        });
        int size = (service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().stream().filter(p -> p.role.equals(created3.role)).collect(Collectors.toList())).size();
        assertEquals( size,0);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 2);
    }

    @Test
    public void testAddEmployeeToShiftWithRistrictionsOnRolesWithoutSpace(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee created2 = service.createEmployee(new Employee(
                "111111100",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertThrows(Exception.class, () -> {
            service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created2.id);
        });
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
    }

    @Test
    public void testAddEmployeeToShiftCanNotWork() {
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        assertThrows(Exception.class, () -> {
            service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        });
    }

    @Test
    public void testAddEmployeeToShiftNoEmployee() {
        EmployeeService service = new EmployeeService();
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<Employee.ShiftDateTime>(),
                new GregorianCalendar()
        ));
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        assertThrows(Exception.class, () -> {
            service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, "111111111");
        });
    }

    @Test
    public void testRemoveEmployeeFromShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);

        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                availableShifts,
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),
                Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeToShift(HR.id, shift.getDate(), shift.getType(), created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
        service.removeEmployeeFromShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 0);
    }

    @Test
    public void testRemoveEmployeeFromShiftNoEmployee() {
        EmployeeService service = new EmployeeService();
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        assertThrows(Exception.class, () -> {
            service.removeEmployeeFromShift(HR.id, shift.getDate(), Shift.Type.Evening, "111111111");
        });
    }

    @Test
    public void testWhoCanWork() {
        EmployeeService service = new EmployeeService();
        Employee HR = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee created = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee created2 = service.createEmployee(new Employee(
                "111111100",
                "Foo",
                Employee.Role.Cashier,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayEvening);
        service.addEmployeeShiftPreference(created2.id, created2.id, Employee.ShiftDateTime.FridayEvening);
        assertEquals(2, service.WhoCanWork(HR.id, Employee.ShiftDateTime.FridayEvening).size());
        assertEquals(1, service.WhoCanWorkWithRole(HR.id, Employee.ShiftDateTime.FridayEvening, Employee.Role.Cashier).size());
    }

    @Test
    public void testNumOfShifts(){
        EmployeeService service = new EmployeeService();
        Employee created = service.createEmployee(new Employee(
                "111111111",
                "Foo",
                Employee.Role.Stocker,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        Employee HR = service.createEmployee(new Employee(
                "111111110",
                "Foo",
                Employee.Role.HumanResources,
                "FooBank",
                1, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));

        Shift shift = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        Shift shift2 = service.createShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 20),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.ThursdayEvening);
        service.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.WednesdayEvening);
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        service.addEmployeeToShift(HR.id, shift2.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.numOfShifts(HR.id, created.id), 2);
        assertEquals(service.numOfShifts(HR.id, HR.id), 0);
    }
}
