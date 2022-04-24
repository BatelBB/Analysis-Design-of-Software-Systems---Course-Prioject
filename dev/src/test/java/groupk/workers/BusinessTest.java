package groupk.workers;

import static org.junit.jupiter.api.Assertions.*;
import groupk.workers.business.Facade;
import groupk.workers.service.Service;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

public class BusinessTest {
    @Test
    public void testCreateEmployee()
    {
        Facade facade = new Facade();
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        List<Employee> list = facade.listEmployees("111111110");
        assertEquals(1, list.size());
        assertEquals("111111110", list.get(0).id);
        assertEquals("Foo", list.get(0).name);
    }

    @Test
    public void testCreateBasicShift()
    {
        Facade facade = new Facade();
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        Employee LM = facade.addEmployee(
                "Foo",
                "11111410",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.LogisticsManager,
                new HashSet<>()
        );
        Employee SM = facade.addEmployee(
                "Foo",
                "11111210",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.StoreManager,
                new HashSet<>()
        );
        Employee s = facade.addEmployee(
                "Foo",
                "1115210",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                new HashSet<>()
        );
        Employee c = facade.addEmployee(
                "Foo",
                "115210",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Cashier,
                new HashSet<>()
        );
        Employee d = facade.addEmployee(
                "Foo",
                "1915210",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                new HashSet<>()
        );
        Employee l = facade.addEmployee(
                "Foo",
                "1415210",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                new HashSet<>()
        );
        Employee ShM = facade.addEmployee(
                "Foo",
                "11180",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.ShiftManager,
                new HashSet<>()
        );
        LinkedList<Employee> em = new LinkedList<>();
        em.add(SM); em.add(HR); em.add(LM); em.add(s); em.add(d); em.add(ShM); em.add(c); em.add(l);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(),Shift.Type.Morning, em, new HashMap<>());
        assertEquals(facade.listShifts(HR.id).size(), 1);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().size(),8);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.StoreManager),1);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.LogisticsManager),1);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.HumanResources),1);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getStaff().size(),8);
    }

    @Test
    public void testDeleteEmployeeByHR() {
        Facade facade = new Facade();
        facade.addEmployee(
                "Foo",
                "111111111",

                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                new HashSet<>()
        );
        facade.addEmployee(

                "Bar",
                "222222222",
                "BarBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        facade.deleteEmployee("222222222", "111111111");
        List<Employee> list = facade.listEmployees("222222222");
        assertEquals(1, list.size());
    }

    @Test
    public void testAddEmployeeShiftPreference(){
        Facade facade = new Facade();
        Employee created = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                new HashSet<>()
        );
        facade.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayMorning);
        Employee.ShiftDateTime createdShift = facade.readEmployee(created.id, created.id).shiftPreferences.iterator().next();
        assertEquals(Employee.ShiftDateTime.FridayMorning, createdShift);
    }

    @Test
    public void testAddEmployeeToShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Facade facade = new Facade();
        Employee created = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                availableShifts
        );
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), r);
        facade.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getStaff().size(), 1);
    }

    @Test
    public void testAddEmployeeToShiftCanNotWork() {
        Facade facade = new Facade();
        Employee created = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                new HashSet<>()
        );
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, new LinkedList<>(), r);
        assertThrows(Exception.class, () -> {
            facade.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        });
    }

    @Test
    public void testRemoveEmployeeFromShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Facade facade = new Facade();
        Employee created = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                availableShifts
        );
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        r.replace(created.role, 1);
        LinkedList<Employee> employees = new LinkedList<>();
        employees.add(created);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),
                Shift.Type.Evening, employees, r);
        assertThrows(Exception.class, () -> {
            facade.removeEmployeeFromShift(HR.id, shift.getDate(), Shift.Type.Evening, created.id);
        });
    }

    @Test
    public void testWhoCanWork() {
        Facade facade = new Facade();
        Employee HR = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        Employee created = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Stocker,
                new HashSet<>()
        );
        Employee created2 = facade.addEmployee(
                "Foo",
                "111111100",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Cashier,
                new HashSet<>()
        );
        facade.addEmployeeShiftPreference(created.id, created.id, Employee.ShiftDateTime.FridayEvening);
        facade.addEmployeeShiftPreference(created2.id, created2.id, Employee.ShiftDateTime.FridayEvening);
        assertEquals(2, facade.WhoCanWork(HR.id, Employee.ShiftDateTime.FridayEvening).size());
        assertEquals(1, facade.WhoCanWorkWithRole(HR.id, Employee.ShiftDateTime.FridayEvening, Employee.Role.Cashier).size());
    }

    @Test
    public void testAddEmployeeToShiftNoEmployee() {
        Facade facade = new Facade();
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21), Shift.Type.Evening, new LinkedList<>(), r);
        assertThrows(Exception.class, () -> {
            facade.addEmployeeToShift(HR.id, shift.getDate(), Shift.Type.Evening, "111111111");
        });
    }

    @Test
    public void testSetRequiredStaffInShift(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        Facade facade = new Facade();
        Employee HR = facade.addEmployee(
                "Foo",
                "111111110",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.HumanResources,
                new HashSet<>()
        );
        Employee created = facade.addEmployee(
                "Foo",
                "111111111",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Cashier,
                availableShifts
        );
        LinkedList<Employee> em = new LinkedList<>();
        em.add(created);
        HashMap<Employee.Role, Integer> r = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r.put(role, 0);
        r.replace(Employee.Role.Cashier, 1);
        Shift shift = facade.addShift(HR.id, new GregorianCalendar(2022, Calendar.APRIL, 21),Shift.Type.Evening, em, r);
        r.replace(Employee.Role.Cashier, 2);
        assertThrows(Exception.class, () -> {
            facade.setRequiredStaffInShift(HR.id, shift.getDate(), shift.getType(), r);
        });
        r.replace(Employee.Role.Cashier, 0);
        facade.setRequiredStaffInShift(HR.id, shift.getDate(), shift.getType(), r);
        assertEquals(facade.readShift(HR.id, shift.getDate(), shift.getType()).getRequiredStaff().get(Employee.Role.Cashier),0);
    }
}
