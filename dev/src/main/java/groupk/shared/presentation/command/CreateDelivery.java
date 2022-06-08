package groupk.shared.presentation.command;

import groupk.logistics.DataLayer.TruckingDTO;
import groupk.shared.presentation.CommandRunner;
import groupk.shared.service.Response;
import groupk.shared.service.dto.Delivery;

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

        Response<Delivery> response = runner.getService().createDelivery(
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
        System.out.println("Delivery created.");
    }
}
