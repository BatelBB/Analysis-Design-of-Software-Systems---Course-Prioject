package groupk.workers.presentation;

import groupk.workers.service.Service;
import groupk.workers.service.dto.Employee;
import groupk.workers.service.dto.Shift;

import java.util.*;
import java.util.function.Consumer;

public class TUI {
    private final Service service;
    private final Runnable onStop;
    private String subject;

    public TUI(Runnable onStop) {
        this.service = new Service();
        this.onStop = onStop;
    }

    public void handleCommand(String command) {
        if (command.startsWith("create employee")) {
            handleCreateEmployee(command);
            return;
        }
        if (command.startsWith("get employee")) {
            handleGetEmployee(command);
            return;
        }
        if (command.startsWith("delete employee")) {
            handleDeleteEmployee(command);
            return;
        }
        if (command.startsWith("update employee")) {
            handleUpdateEmployee(command);
            return;
        }
        if (command.startsWith("list employees")) {
            handleListEmployees(command);
            return;
        }
        if (command.startsWith("add shift preference")) {
            handleAddShiftPreference(command);
            return;
        }
        if (command.startsWith("delete shift preference")) {
            handleRemoveShiftPreference(command);
            return;
        }
        if (command.startsWith("create shift")) {
            handleCreateShift(command);
            return;
        }
        if (command.startsWith("list shifts")) {
            handleListShifts(command);
            return;
        }
        if (command.startsWith("add shift staff")) {
            handleAddShiftStaff(command);
            return;
        }
        if (command.startsWith("delete shift staff")) {
            handleDeleteShiftStaff(command);
            return;
        }
        if (command.startsWith("update shift required role")) {
            handleUpdateShiftRequiredRole(command);
            return;
        }

        if (command.startsWith("login")) {
            handleLogin(command);
            return;
        }
        if (command.equals("load sample")) {
            handleLoadSample();
            return;
        }
        if (command.equals("quit")) {
            onStop.run();
            return;
        }

        System.out.println("Error: command must be one of the following:");
        System.out.println("  load sample");
        System.out.println("  login");
        System.out.println("  quit");
        System.out.println("  create employee");
        System.out.println("  get employee");
        System.out.println("  delete employee");
        System.out.println("  update employee");
        System.out.println("  list employees");
        System.out.println("  add shift preference");
        System.out.println("  delete shift preference");
        System.out.println("  create shift");
        System.out.println("  list shifts");
        System.out.println("  add shift staff");
        System.out.println("  delete shift staff");
        System.out.println("  update shift required role");
        System.out.println("Usage:");
        System.out.println("> <command> [arguments]");
        System.out.println("For command specific help, run the command without arguments.");
        System.out.println("To quit the program, run the quit command. ALL INFORMATION WILL BE LOST ON QUIT.");
    }

    private void handleLoadSample() {
        service.createEmployee(new Employee(
                "999871163",
                "Noam Levi",
                Employee.Role.HumanResources,
                "Foo",
                12556, 1,
                35,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999871163", "999871163", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999871163", "999871163", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999871163", "999871163", Employee.ShiftDateTime.ThursdayMorning);
        service.createEmployee(new Employee(
                "999698780",
                "David Perez",
                Employee.Role.HumanResources,
                "Foo",
                55336, 1,
                35,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999698780", "999698780", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999698780", "999698780", Employee.ShiftDateTime.WednesdayMorning);
        service.addEmployeeShiftPreference("999698780", "999698780", Employee.ShiftDateTime.ThursdayMorning);
        service.createEmployee(new Employee(
                "999356934",
                "Noa Cohen",
                Employee.Role.Cashier,
                "Foo",
                23226, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999356934", "999356934", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999356934", "999356934", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999356934", "999356934", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999356934", "999356934", Employee.ShiftDateTime.ThursdayMorning);
        service.createEmployee(new Employee(
                "999368814",
                "David Levi",
                Employee.Role.Cashier,
                "Foo",
                15323, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999368814", "999368814", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999368814", "999368814", Employee.ShiftDateTime.SundayEvening);
        service.addEmployeeShiftPreference("999368814", "999368814", Employee.ShiftDateTime.MondayEvening);
        service.addEmployeeShiftPreference("999368814", "999368814", Employee.ShiftDateTime.WednesdayMorning);
        service.addEmployeeShiftPreference("999368814", "999368814", Employee.ShiftDateTime.ThursdayEvening);
        service.createEmployee(new Employee(
                "999849854",
                "Josef Cohen",
                Employee.Role.Stocker,
                "Foo",
                12552, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999849854", "999849854", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999849854", "999849854", Employee.ShiftDateTime.SundayEvening);
        service.addEmployeeShiftPreference("999849854", "999849854", Employee.ShiftDateTime.MondayEvening);
        service.addEmployeeShiftPreference("999849854", "999849854", Employee.ShiftDateTime.WednesdayMorning);
        service.addEmployeeShiftPreference("999849854", "999849854", Employee.ShiftDateTime.ThursdayEvening);
        service.createEmployee(new Employee(
                "999007248",
                "Maya Dehan",
                Employee.Role.Stocker,
                "Foo",
                65552, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999007248", "999007248", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999007248", "999007248", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999007248", "999007248", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999007248", "999007248", Employee.ShiftDateTime.FridayEvening);
        service.createEmployee(new Employee(
                "999481773",
                "Eitan Cohen",
                Employee.Role.Driver,
                "Foo",
                13525, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999481773", "999481773", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999481773", "999481773", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999481773", "999481773", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999481773", "999481773", Employee.ShiftDateTime.FridayEvening);
        service.createEmployee(new Employee(
                "999555931",
                "Omer Levi",
                Employee.Role.Driver,
                "Foo",
                15731, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999555931", "999555931", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999555931", "999555931", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999555931", "999555931", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999555931", "999555931", Employee.ShiftDateTime.FridayEvening);
        service.createEmployee(new Employee(
                "999205214",
                "Tamar Meir",
                Employee.Role.Logistics,
                "Foo",
                63731, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999205214", "999205214", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999205214", "999205214", Employee.ShiftDateTime.SundayEvening);
        service.addEmployeeShiftPreference("999205214", "999205214", Employee.ShiftDateTime.MondayEvening);
        service.addEmployeeShiftPreference("999205214", "999205214", Employee.ShiftDateTime.WednesdayMorning);
        service.addEmployeeShiftPreference("999205214", "999205214", Employee.ShiftDateTime.ThursdayEvening);
        service.createEmployee(new Employee(
                "999838402",
                "Michal Alon",
                Employee.Role.Logistics,
                "Foo",
                63731, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999838402", "999838402", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999838402", "999838402", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999838402", "999838402", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999838402", "999838402", Employee.ShiftDateTime.FridayEvening);
        service.createEmployee(new Employee(
                "999072804",
                "Michal Golan",
                Employee.Role.ShiftManager,
                "Foo",
                63521, 1,
                30,
                0, 0,
                new HashSet<>(),
                new GregorianCalendar()
        ));
        service.addEmployeeShiftPreference("999072804", "999072804", Employee.ShiftDateTime.SundayMorning);
        service.addEmployeeShiftPreference("999072804", "999072804", Employee.ShiftDateTime.MondayMorning);
        service.addEmployeeShiftPreference("999072804", "999072804", Employee.ShiftDateTime.TuesdayEvening);
        service.addEmployeeShiftPreference("999072804", "999072804", Employee.ShiftDateTime.FridayEvening);
    }

    private void handleListShifts(String command) {
        String[] args = command.split(" ");
        if (args.length != 2) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Usage:");
            System.out.println("> list shifts");
            return;
        }
        try {
            List<Shift> shifts = service.listShifts(subject);
            for (Shift shift: shifts) {
                String date = shift.getDate().get(GregorianCalendar.DAY_OF_MONTH)
                        + "/" + (shift.getDate().get(GregorianCalendar.MONTH) + 1)
                        + "/" + shift.getDate().get(GregorianCalendar.YEAR);
                System.out.printf("- date: %s\n  type: %s\n  required staff:\n", date, shift.getType());
                for (Employee.Role key: shift.getRequiredStaff().keySet()) {
                    System.out.printf("  - role: %s\n    count: %s\n", key, shift.getRequiredStaff().get(key));
                }
                System.out.println("  staff:");
                for (Employee staff: shift.getStaff()) {
                    System.out.printf("  - id: %s\n    name: %s\n    role: %s\n", staff.id, staff.name, staff.role);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdateShiftRequiredRole(String command) {
        String[] args = command.split(" ");
        if (args.length != 8) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> update shift required role <date> <type> <role> <required-count>");
            return;
        }

        String[] splitDate = args[4].split("-");
        if (
                splitDate.length != 3
                        && splitDate[0].length() == 4
                        && splitDate[1].length() == 2
                        && splitDate[2].length() == 2
        ) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }
        Calendar date;
        try {
            date = new GregorianCalendar(
                    Integer.parseInt(splitDate[0]),
                    // Why -1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor.
                    Integer.parseInt(splitDate[1]) - 1,
                    Integer.parseInt(splitDate[2]));
        } catch (NumberFormatException e) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }

        Shift.Type type;
        try {
            type = Shift.Type.valueOf(args[5]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: type must be either Morning or Evening.");
            return;
        }

        Employee.Role role;
        try {
            role = Employee.Role.valueOf(args[6]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: role must be one of the following:");
            System.out.println("Logistics, HumanResources, Stocker, Cashier, LogisticsManager, ShiftManager, Driver, StoreManager.");
            return;
        }

        int count;
        try {
            count = Integer.parseInt(args[7]);
            if (count < 0) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: required-count must be an integer.");
            return;
        }

        try {
            service.setRequiredRoleInShift(subject, date, type, role, count);
            System.out.println("updated required staff in shift.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDeleteShiftStaff(String command) {
        String[] args = command.split(" ");
        if (args.length != 6) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> delete shift staff <date> <type> <employee-id>");
            return;
        }

        String[] splitDate = args[3].split("-");
        if (
                splitDate.length != 3
                        && splitDate[0].length() == 4
                        && splitDate[1].length() == 2
                        && splitDate[2].length() == 2
        ) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }
        Calendar date;
        try {
            date = new GregorianCalendar(
                    Integer.parseInt(splitDate[0]),
                    // Why -1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor.
                    Integer.parseInt(splitDate[1]) - 1,
                    Integer.parseInt(splitDate[2]));
        } catch (NumberFormatException e) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }

        Shift.Type type;
        try {
            type = Shift.Type.valueOf(args[4]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: type must be either Morning or Evening.");
            return;
        }

        try {
            service.addEmployeeToShift(subject, date, type, args[5]);
            System.out.println("added employee to shift.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddShiftStaff(String command) {
        String[] args = command.split(" ");
        if (args.length != 6) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> add shift staff <date> <type> <employee-id>");
            return;
        }

        String[] splitDate = args[3].split("-");
        if (
                splitDate.length != 3
                        && splitDate[0].length() == 4
                        && splitDate[1].length() == 2
                        && splitDate[2].length() == 2
        ) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }
        Calendar date;
        try {
            date = new GregorianCalendar(
                    Integer.parseInt(splitDate[0]),
                    // Why -1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor.
                    Integer.parseInt(splitDate[1]) - 1,
                    Integer.parseInt(splitDate[2]));
        } catch (NumberFormatException e) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }

        Shift.Type type;
        try {
            type = Shift.Type.valueOf(args[4]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: type must be either Morning or Evening.");
            return;
        }

        try {
            service.removeEmployeeFromShift(subject, date, type, args[5]);
            System.out.println("removed employee from shift.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleListEmployees(String command) {
        String[] args = command.split(" ");
        if (args.length != 2) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Usage:");
            System.out.println("> list employees");
            return;
        }

        try {
            List<Employee> employees = service.listEmployees(subject);
            System.out.println("id, name, role");
            for (Employee employee: employees) {
                System.out.printf("%s, %s, %s\n", employee.id, employee.name, employee.role);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleRemoveShiftPreference(String command) {
        String[] args = command.split(" ");
        if (args.length != 5) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> delete shift preference <id> <shift>");
            return;
        }

        String id = args[3];
        Employee.ShiftDateTime shift;
        try {
            shift = Employee.ShiftDateTime.valueOf(args[4]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: shift must be one of the following:");
            System.out.println("SundayMorning, SundayEvening, MondayMorning, MondayEvening, TuesdayMorning, TuesdayEvening, WednesdayMorning, WednesdayEvening, ThursdayMorning, ThursdayEvening, FridayMorning, FridayEvening, SaturdayMorning, SaturdayEvening.");
            return;
        }

        try {
            service.deleteEmployeeShiftPreference(subject, id, shift);
            System.out.println("removed shift preference.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddShiftPreference(String command) {
        String[] args = command.split(" ");
        if (args.length != 5) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> add shift preference <id> <shift>");
            return;
        }

        String id = args[3];
        Employee.ShiftDateTime shift;
        try {
            shift = Employee.ShiftDateTime.valueOf(args[4]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: shift must be one of the following:");
            System.out.println("SundayMorning, SundayEvening, MondayMorning, MondayEvening, TuesdayMorning, TuesdayEvening, WednesdayMorning, WednesdayEvening, ThursdayMorning, ThursdayEvening, FridayMorning, FridayEvening, SaturdayMorning, SaturdayEvening.");
            return;
        }

        try {
            service.addEmployeeShiftPreference(subject, id, shift);
            System.out.println("added shift preference.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleUpdateEmployee(String command) {
        String[] args = command.split(" ");
        if (args.length != 9) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> update employee <id> <name> <bank> <bank-id> <bank-branch> <salary-per-hour> <role>");
            return;
        }

        String id = args[2];
        String name = args[3];
        String bank = args[4];
        int bankID;
        try {
            bankID = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.out.println("Error: bank-id must be an integer.");
            return;
        }
        int bankBranch;
        try {
            bankBranch = Integer.parseInt(args[6]);
        } catch (NumberFormatException e) {
            System.out.println("Error: bank-branch must be an integer.");
            return;
        }
        int salaryPerHour;
        try {
            salaryPerHour = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            System.out.println("Error: salary-per-hour must be an integer.");
            return;
        }
        Employee.Role role;
        try {
            role = Employee.Role.valueOf(args[8]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: role must be one of the following:");
            System.out.println("Logistics, HumanResources, Stocker, Cashier, LogisticsManager, ShiftManager, Driver, StoreManager.");
            return;
        }

        try {
            service.updateEmployee(subject, new Employee(
                    id,
                    name,
                    role,
                    bank,
                    bankID,
                    bankBranch,
                    salaryPerHour,
                    0,
                    0,
                    new HashSet<>(),
                    new GregorianCalendar()
            ));
            System.out.printf("Updated employee %s.\n", name);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleLogin(String command) {
        String[] args = command.split(" ");
        if (args.length != 2) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> login <id>");
            return;
        }
        try {
            service.readEmployee(args[1], args[1]);
        } catch (Exception e) {
            System.out.println("Error: id must be valid employee ID.");
        }
        subject = args[1];
        System.out.printf("Logged in as %s.\n", subject);
    }

    private void handleCreateEmployee(String command) {
        String[] args = command.split(" ");
        if (args.length != 9) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> create employee <name> <id> <bank> <bank-id> <bank-branch> <salary-per-hour> <role>");
            return;
        }

        String name = args[2];
        String id = args[3];
        String bank = args[4];
        int bankID;
        try {
            bankID = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.out.println("Error: bank-id must be an integer.");
            return;
        }
        int bankBranch;
        try {
            bankBranch = Integer.parseInt(args[6]);
        } catch (NumberFormatException e) {
            System.out.println("Error: bank-branch must be an integer.");
            return;
        }
        int salaryPerHour;
        try {
            salaryPerHour = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            System.out.println("Error: salary-per-hour must be an integer.");
            return;
        }
        Employee.Role role;
        try {
            role = Employee.Role.valueOf(args[8]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: role must be one of the following:");
            System.out.println("Logistics, HumanResources, Stocker, Cashier, LogisticsManager, ShiftManager, Driver, StoreManager.");
            return;
        }

        try {
            service.createEmployee(new Employee(
                    id,
                    name,
                    role,
                    bank,
                    bankID,
                    bankBranch,
                    salaryPerHour,
                    0,
                    0,
                    new HashSet<>(),
                    new GregorianCalendar()
            ));
            System.out.printf("Created employee %s.\n", name);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleGetEmployee(String command) {
        String[] args = command.split(" ");
        if (args.length != 3) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> get employee <id>");
            return;
        }
        try {
            Employee read = service.readEmployee(subject, args[2]);
            String startDate = read.employmentStart.get(GregorianCalendar.DAY_OF_MONTH)
                    + "/" + (read.employmentStart.get(GregorianCalendar.MONTH) + 1)
                    + "/" + read.employmentStart.get(GregorianCalendar.YEAR);
            System.out.printf("name: %s\nid: %s\nrole: %s\nbank details:\n  bank: %s\n  branch: %s\n  account number: %s\nworking conditions:\n  hourly salary: %s\n  sick days used: %s\n  vacation days used: %s\n  employment start: %s\n", read.name, read.id, read.role, read.bank, read.bankBranch, read.bankID, read.salaryPerHour, read.sickDaysUsed, read.vacationDaysUsed, startDate);
            System.out.println("can work at:");
            for (Employee.ShiftDateTime shift: read.shiftPreferences) {
                System.out.println("  " + shift);
            }
        } catch (Exception e) {
            System.out.println("Error: id must be valid employee ID.");
        }
    }

    private void handleDeleteEmployee(String command) {
        String[] args = command.split(" ");
        if (args.length != 3) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> delete employee <id>");
            return;
        }
        if (args[2].equals(subject)) {
            System.out.println("Error: Can not delete currently logged in user.");
            return;
        }
        try {
            Employee deleted = service.deleteEmployee(subject, args[2]);
            System.out.printf("deleted employee %s.\n", deleted.id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateShift(String command) {
        String[] args = command.split(" ");
        if (args.length < 4) {
            System.out.println("Error: All arguments must be supplied.");
            System.out.println("Usage:");
            System.out.println("> create shift <date> <type> [staff]");
            System.out.println("  date is formatted as yyyy-mm-dd, for example 2022-04-25.");
            System.out.println("  type is either Morning or Evening.");
            return;
        }

        String[] splitDate = args[2].split("-");
        if (
                splitDate.length != 3
                && splitDate[0].length() == 4
                && splitDate[1].length() == 2
                && splitDate[2].length() == 2
        ) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }
        Calendar date;
        try {
            date = new GregorianCalendar(
                    Integer.parseInt(splitDate[0]),
                    // Why -1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor.
                    Integer.parseInt(splitDate[1]) - 1,
                    Integer.parseInt(splitDate[2]));
        } catch (NumberFormatException e) {
            System.out.println("Error: date must follow yyyy-mm-dd format, for example 2022-04-25.");
            return;
        }

        Shift.Type type;
        try {
            type = Shift.Type.valueOf(args[3]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: type must be either Morning or Evening.");
            return;
        }

        LinkedList<Employee> staff = new LinkedList<>();
        for (int i = 4; i < args.length; i++) {
            try {
                Employee current = service.readEmployee(subject, args[i]);
                staff.push(current);
            } catch (NullPointerException e) {
                System.out.printf("Error: Encountered invalid employee %s. staff must be valid employee IDs.\n", args[i]);
                System.out.println("Error: staff must be valid employee IDs.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: you must be authorized to perform this action.");
                return;
            }
        }

        try {
            service.createShift(subject, date, type, staff, new HashMap<>());
            System.out.println("Created shift.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
