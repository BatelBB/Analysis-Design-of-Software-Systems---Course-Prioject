package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;

public interface Command {
    String name();
    String description();
    boolean isMatching(String line);
    void execute(String line, CommandRunner runner);
}
