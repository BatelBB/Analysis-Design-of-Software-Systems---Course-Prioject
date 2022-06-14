package groupk.shared.PresentationLayer.EmployeesLogistics.command;

import groupk.shared.PresentationLayer.EmployeesLogistics.CommandRunner;
import groupk.shared.service.Response;
import groupk.shared.service.dto.Product;

public class DeleteDeliveryProduct implements Command {
    @Override
    public String name() {
        return "delete delivery product";
    }

    @Override
    public String description() {
        return "remove product from delivery";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("delete delivery product");
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 6) {
            System.out.println("Error: Wrong number of arguments.");
            System.out.println("Usage:");
            System.out.println("> delete delivery product <id> <product-id> <count>");
            return;
        }

        int id;
        try {
            id = CommandRunner.parseInt(command[3]);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: id %s\n", e.getMessage());
            return;
        }

        int count;
        try {
            count = CommandRunner.parseInt(command[5]);
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: count %s\n", e.getMessage());
            return;
        }

        Response<Boolean> response = runner.getService().moveProducts(runner.getSubject(), id, new Product(command[4], count));
        if (response.isError()) {
            System.out.printf("Error: %s\n", response.getErrorMessage());
            return;
        }
        if (!response.getValue()) {
            System.out.println("Error: Something went wrong.");
            return;
        }
        System.out.println("Added product to delivery.");
    }
}
