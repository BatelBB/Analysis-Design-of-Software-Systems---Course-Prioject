package groupk.employee_logistics;
import groupk.logistics.business.DLicense;
import groupk.shared.PresentationLayer.App;
import groupk.shared.service.dto.*;
import groupk.shared.service.*;

import groupk.workers.business.Facade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeLogisticsSharedTests {
    protected Connection connection;
    protected Service service;

    private void resetService() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                App app = new App("database.db", true);
                connection = app.conn;
                service = app.service;
            }
        }catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @BeforeEach
    public void setService() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            App app = new App("database.db", true);
            connection = app.conn;
            service = app.service;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    public void afterService() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void optionsForDelivery(){
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Calendar date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,1);
        Shift shift1 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,2);
        Shift shift2 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,3);
        Shift shift3 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,4);
        Shift shift4 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,5);
        Shift shift5 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue(); date.add(Calendar.DAY_OF_MONTH,1);
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,6);
        Shift shift6 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH,7);
        Shift shift7 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        assertEquals(service.listShifts(HR.id).getValue().size(), 7);
        assertEquals(service.optionsForDeleveryWithLogisitcsAndDriversInShift(HR.id).getValue().size(),7);
    }
    @Test
    public void deleteDriverWhoHasFutureDelivery(){
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        assertEquals(service.listDeliveries(TM1.id).getValue().size(),1);
        assertTrue(service.deleteEmployee(HR.id,d.id).isError());
        service.deleteDelivery(TM1.id, 1);
        Calendar date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH, -4);
        Shift shift2 = service.createShift(
                HR.id,
                date,
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Shift shift3 = service.createShift(
                HR.id,
                date,
                Shift.Type.Morning,
                staff,
                r1).getValue();
        service.createDelivery(
                TM1.id,
                "12315678",
                (LocalDateTime.now()).minusDays(4),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        service.removeEmployeeFromShift(HR.id, shift1.getDate(), shift1.getType(), d.id).getValue();
        assertFalse(service.deleteEmployee(HR.id,d.id).isError());
    }

    @Test
    public void deleteLogisticsDB(){
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        service.deleteLogisticsDB();
        resetService();
        Employee TM2 = service.createEmployee(
                "Avi",
                "921",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        assertEquals(service.listDeliveries(TM2.id).getValue().size(),0);
    }

    @Test
    public void deleteEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        resetService();
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
        resetService();
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
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        resetService();
        service.loadEmployeeDB();
        assertEquals(service.listDeliveries(TM1.id).getValue().size(),1);
    }

    @Test
    public void loadEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        resetService();
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
        resetService();
        service.loadEmployeeDB();
        assertEquals(service.listShifts("111111110").getValue().size(), 1);
        assertEquals(service.listShifts("111111110").getValue().get(0).getDate(), shift.getDate());
        assertEquals(service.listEmployees("111111110").getValue().size(), 3);
    }

    @Test
    public void updateLogisticsDB(){
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        Employee d2 = service.createEmployee(
                "Foo",
                "213245121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        service.addLicenseForDriver(
                d2.id,
                DLicense.B.name());
        service.addEmployeeToShift(HR.id, shift1.getDate(), shift1.getType(), d2.id);
        service.updateDriverOnTrucking(TM1.id, 1 ,d2.id);
        assertEquals(service.listDeliveries(TM1.id).getValue().get(0).driverID, Integer.parseInt(d2.id));
        assertTrue(service.updateDateOnTrucking(TM1.id, 1, LocalDateTime.of(2023, Month.APRIL, 20, 18, 0)).isError());
    }

    @Test
    public void UpdateEmployeeDB(){
        Set<Employee.ShiftDateTime> availableShifts = new HashSet<Employee.ShiftDateTime>();
        availableShifts.add(Employee.ShiftDateTime.ThursdayEvening);
        resetService();
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
        resetService();
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
        service.deleteEmployee(HR.id, "11411110");
    }
    @Test
    public void testCreateDelivery() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0);
        assertEquals(service.listDeliveries(TM1.id).getValue().size(),1);
    }
    @Test
    public void testCreateDeliveryWithoutLogisticsEmployee() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        assertTrue(service.createDelivery(
                TM1.id,
                "12345678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0).isError());
    }

    @Test
    public void testAddDriverNotWorking() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "211005121",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.B.name());
        assertTrue(service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0).isError());
    }

    @Test
    public void testAddDriverWithWrongLisence() {
        Set<Employee.ShiftDateTime> shiftPreferences = new HashSet<>();
        for(Employee.ShiftDateTime shiftDateTime : Employee.ShiftDateTime.values())
            shiftPreferences.add(shiftDateTime);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for (Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
        r1.replace(Employee.Role.ShiftManager, 1);
        resetService();
        service.deleteEmployeeDB();
        service.deleteLogisticsDB();
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
        Employee d = service.createEmployee(
                "Foo",
                "123",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Driver,
                shiftPreferences).getValue();
        Employee l = service.createEmployee(
                "Foo",
                "21221",
                "FooBank",
                1, 1,
                new GregorianCalendar(),
                30,
                0, 0,
                Employee.Role.Logistics,
                shiftPreferences).getValue();
        Employee SM1 = service.createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                shiftPreferences).getValue();
        Employee TM1 = service.createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                shiftPreferences).getValue();
        LinkedList<Employee> staff = new LinkedList<>();
        staff.add(SM1); staff.add(l); staff.add(d);
        Shift shift1 = service.createShift(
                HR.id,
                new GregorianCalendar(2023, Calendar.APRIL, 21),
                Shift.Type.Evening,
                staff,
                r1).getValue();
        Site source = new Site("tamirHouse", "054-3397995", "center", "batYam", "tamirStr", 13, 2, 3);
        Site destination = new Site("idoHouse", "052-4321231", "center", "herzliya", "idoStr", 100, 1, 6);
        List<Site> sources = new LinkedList<>();
        List<Site> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Integer> orders = new LinkedList<>();
        orders.add(1);
        service.createVehicle(
                TM1.id,
                "B",
                "12315678",
                "mercedes",
                4,
                32);
        service.addLicenseForDriver(
                d.id,
                DLicense.C.name());
        assertTrue(service.createDelivery(
                TM1.id,
                "12315678",
                LocalDateTime.of(2023, Month.APRIL, 21, 18, 0),
                d.id,
                sources,
                destinations,
                orders,
                2,
                0).isError());
    }
}
