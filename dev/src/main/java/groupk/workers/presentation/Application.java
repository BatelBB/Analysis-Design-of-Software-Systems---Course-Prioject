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

        Scanner input = new Scanner(System.in);

        System.out.println( "Hello World!" );

        while(keepGoing.get()) {
            System.out.print("> ");
            String command = input.nextLine();
            tui.handleCommand(command);
        }
    }
}
