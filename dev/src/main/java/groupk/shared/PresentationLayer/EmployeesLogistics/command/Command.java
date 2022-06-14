package groupk.shared.PresentationLayer.EmployeesLogistics.command;

import groupk.shared.PresentationLayer.EmployeesLogistics.CommandRunner;

public interface Command {
    String name();
    String description();
    boolean isMatching(String line);
    void execute(String[] command, CommandRunner runner);
}
