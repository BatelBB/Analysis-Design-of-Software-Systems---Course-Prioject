package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;

public class DeleteDelivery implements Command {
    @Override
    public String name() {
        return "delete delivery";
    }

    @Override
    public String description() {
        return "delete a delivery";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("delete delivery");
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 3) {
            System.out.println("Error: Wrong number of arguments.");
            System.out.println("Usage:");
            System.out.println("> delete delivery <id>");
            return;
        }
    }
}
