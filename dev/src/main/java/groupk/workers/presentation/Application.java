package groupk.workers.presentation;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static void main( String[] args )
    {
        AtomicBoolean keepGoing = new AtomicBoolean(true);
        TUI tui = new TUI(() -> {
            keepGoing.set(false);
        });

        tui.startupPrompt();

        Scanner input = new Scanner(System.in);
        while(keepGoing.get()) {
            System.out.print("> ");
            String command = input.nextLine();
            tui.handleCommand(command);
        }
    }
}
