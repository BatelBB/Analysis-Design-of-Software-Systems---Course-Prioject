package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;
import groupk.shared.service.Response;
import groupk.shared.service.dto.Employee;

public class Login implements Command {
    @Override
    public String name() {
        return "login";
    }

    @Override
    public String description() {
        return "log in as an employee";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("login");
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 2) {
            System.out.println("Error: Wrong number of arguments.");
            usage();
            return;
        }

        Response<Employee> subject = runner.getService().readEmployee(command[1], command[1]);
        if (subject.isError()) {
            System.out.println("Error: User does not exist.");
            usage();
            return;
        }

        runner.setSubject(subject.getValue().id);
        System.out.printf("Logged in as %s.\n", subject.getValue().id);
    }

    private void usage() {
        System.out.println("Usage:");
        System.out.println("> login <id>");
    }
}
