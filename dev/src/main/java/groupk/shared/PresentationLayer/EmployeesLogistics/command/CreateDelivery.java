package groupk.shared.PresentationLayer.EmployeesLogistics.command;

import groupk.shared.PresentationLayer.EmployeesLogistics.CommandRunner;
import groupk.shared.service.Response;
import groupk.shared.service.dto.Delivery;
import groupk.shared.service.dto.Employee;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class CreateDelivery implements Command {
    @Override
    public String name() {
        return "create delivery";
    }

    @Override
    public String description() {
        return "create a new delivery";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("create delivery");
    }

    @Override
    public boolean isVisible(Employee.Role role) {
        return role == Employee.Role.LogisticsManager || role == Employee.Role.TruckingManger;
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 7) {
            System.out.println("Error: Wrong number of arguments.");
            System.out.println("Usage:");
            System.out.println("> create delivery <registration> <date> <time> <driver-id> <duration-in-minutes>");
            return;
        }

        LocalDateTime datetime;
        try {
            datetime = CommandRunner.parseLocalDateTime(command[3], command[4]);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: date or time %s\n", e.getMessage());
            return;
        }

        int minuteDuration;
        try {
            minuteDuration = CommandRunner.parseInt(command[6]);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: date or time %s\n", e.getMessage());
            return;
        }

        Response<List<String>[]> response = runner.getService().createDelivery(
                runner.getSubject(),
                command[2],
                datetime,
                command[5],
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                minuteDuration / 60,
                minuteDuration % 60);
        if (response.isError()) {
            System.out.printf("Error: %s\n", response.getErrorMessage());
            return;
        }
        if (response.getValue() == null | response.getValue().length != 3 | response.getValue()[0] == null | response.getValue()[1] == null | response.getValue()[2] == null)
            System.out.print("Error: something got wrong :(\n");
        else if (response.getValue()[0].size() == 0 & response.getValue()[1].size() == 0 & response.getValue()[2].size() == 0)
            System.out.println("Delivery created.");
        else {
            System.out.println("The delivery created with some erros: ");
            for (int i = 0; i < 3; i++) {
                List<String> Errors = response.getValue()[i];
                for (String error : Errors) {
                    System.out.println("   " + error);
                }
            }
        }
    }
}
