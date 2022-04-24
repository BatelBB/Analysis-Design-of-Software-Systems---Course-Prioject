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
        if (command.startsWith("create shift")) {
            handleCreateShift(command);
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

        if (command.startsWith("login")) {
            handleLogin(command);
            return;
        }
        if (command.equals("quit")) {
            onStop.run();
            return;
        }

        System.out.println("Error: command must be one of the following:");
        System.out.println("  login");
        System.out.println("  quit");
        System.out.println("  create employee");
        System.out.println("  get employee");
        System.out.println("  delete employee");
        System.out.println("  update employee");
        System.out.println("  add shift preference");
        System.out.println("  delete shift preference");
        System.out.println("  create shift");
        System.out.println("Usage:");
        System.out.println("> <command> [arguments]");
        System.out.println("For command specific help, run the command without arguments.");
        System.out.println("To quit the program, run the quit command. ALL INFORMATION WILL BE LOST ON QUIT.");
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
                System.out.printf("%s, %s, %s", employee.id, employee.name, employee.role);
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
                    + "/" + read.employmentStart.get(GregorianCalendar.MONTH)
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
                    // Why +1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor.
                    Integer.parseInt(splitDate[1]) + 1,
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
