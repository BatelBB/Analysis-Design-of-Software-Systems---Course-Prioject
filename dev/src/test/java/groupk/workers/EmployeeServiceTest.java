package groupk.workers;

import static org.junit.jupiter.api.Assertions.*;

import groupk.workers.service.EmployeeService;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;
import org.junit.jupiter.api.Test;

import java.util.*;

public class EmployeeServiceTest {
    @Test
    public void testCreateShift()
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
                new Date()
        ));
        Shift shift = service.createShift(HR.id, new Date(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        assertEquals(service.listShifts(HR.id).size(), 1);
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
                new Date()
        ));
        assertThrows(Exception.class, () -> {
            service.createShift(NotHR.id, new Date(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
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
                new Date()
        ));
        Employee.WeeklyShift shift = new Employee.WeeklyShift(Employee.WeeklyShift.Day.Friday, Shift.Type.Morning);
        service.addEmployeeShiftPreference(created.id, created.id, shift);
        Employee.WeeklyShift createdShift = service.readEmployee(created.id, created.id).shiftPreferences.iterator().next();
        assertEquals(Employee.WeeklyShift.Day.Friday, createdShift.day);
        assertEquals(Shift.Type.Morning, createdShift.type);
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
                new Date()
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
                new Date()
        ));
        Employee.WeeklyShift shift = new Employee.WeeklyShift(Employee.WeeklyShift.Day.Friday, Shift.Type.Morning);
        assertThrows(Exception.class, () -> {
            service.addEmployeeShiftPreference(created2.id, created.id, shift);
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
                new Date()
        ));
        Employee.WeeklyShift shift = new Employee.WeeklyShift(Employee.WeeklyShift.Day.Friday, Shift.Type.Morning);
        service.addEmployeeShiftPreference(created.id, created.id, shift);
        service.deleteEmployeeShiftPreference(created.id, created.id, shift);
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
                new Date()
        ));
        Set<Employee.WeeklyShift> set = new HashSet<Employee.WeeklyShift>();
        Employee.WeeklyShift shift = new Employee.WeeklyShift(Employee.WeeklyShift.Day.Friday, Shift.Type.Morning);
        Employee.WeeklyShift shift2 = new Employee.WeeklyShift(Employee.WeeklyShift.Day.Monday, Shift.Type.Evening);
        set.add(shift);
        set.add(shift2);
        service.setEmployeeShiftsPreference(created.id, created.id, set);
        assertEquals(2, service.readEmployee(created.id, created.id).shiftPreferences.size());
    }

    @Test
    public void testAddEmployeeToShift(){
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
                new Date()
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
                new Date()
        ));

        Shift shift = service.createShift(HR.id, new Date(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
    }

    @Test
    public void testRemoveEmployeeToShift(){
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
                new Date()
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
                new Date()
        ));

        Shift shift = service.createShift(HR.id, new Date(),Shift.Type.Evening, new LinkedList<>(), new HashMap<>());
        service.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
        service.removeEmployeeFromShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(service.readShift(created.id, shift.getDate(), shift.getType()).getStaff().size(), 0);
    }
}
