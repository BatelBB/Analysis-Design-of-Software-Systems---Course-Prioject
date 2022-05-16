package groupk.shared.presentation;

import groupk.shared.presentation.command.Command;

public class CommandRunner {
    private final Command[] commands;
    private final Runnable onStop;
    private String subject;

    public CommandRunner(Command[] commands, Runnable onStop) {
        this.commands = commands;
        this.onStop = onStop;
    }

    public void invoke(String line) {
        for (Command command: commands) {
            if (command.isMatching(line)) {
                command.execute(line, this);
                return;
            }
        }
        help();
    }

    public void introduce() {
        System.out.println("introduction");
    }

    public void stop() {
        
    }

    private void help() {
        System.out.println("help");
    }
}
