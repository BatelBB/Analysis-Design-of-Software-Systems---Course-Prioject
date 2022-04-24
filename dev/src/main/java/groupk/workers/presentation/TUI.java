package groupk.workers.presentation;

import groupk.workers.service.Service;
import groupk.workers.service.dto.Employee;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.function.Consumer;

public class TUI {
    private final Service service;
    private final Runnable onStop;

    public TUI(Runnable onStop) {
        this.service = new Service();
        this.onStop = onStop;
    }

    public void handleCommand(String command) {
        if (command.startsWith("create employee")) {
            handleCreateEmployee(command);
            return;
        }

        if (command.equals("quit")) {
            onStop.run();
            return;
        }

        System.out.println("Error: command must be one of the following:");
        System.out.println("  create employee");
        System.out.println("  quit");
        System.out.println("Usage:");
        System.out.println("> <command> [arguments]");
        System.out.println("For command specific help, run the command without arguments.");
        System.out.println("To quit the program, run the quit command. ALL INFORMATION WILL BE LOST ON QUIT.");
    }

    private void handleCreateEmployee(String command) {
        if (command.equals("create employee")) {
            System.out.println("Usage:");
            System.out.println("> create employee <name> <id> <bank> <bank-id> <bank-branch> <salary-per-hour> <role>");
            return;
        }

        String[] args = command.split(" ");
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
}
