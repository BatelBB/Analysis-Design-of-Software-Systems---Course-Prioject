package groupk.shared.PresentationLayer.EmployeesLogistics.command;

import groupk.shared.PresentationLayer.EmployeesLogistics.CommandRunner;
import groupk.shared.service.dto.Employee;

public class Quit implements Command {
    @Override
    public String name() {
        return "quit";
    }

    @Override
    public String description() {
        return "exit the program";
    }

    @Override
    public boolean isMatching(String line) {
        return line.equals("quit");
    }

    @Override
    public boolean isVisible(Employee.Role role) {
        return true;
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        runner.stop();
    }
}
