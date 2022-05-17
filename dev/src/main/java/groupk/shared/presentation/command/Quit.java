package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;

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
    public void execute(String[] command, CommandRunner runner) {
        runner.stop();
    }
}
