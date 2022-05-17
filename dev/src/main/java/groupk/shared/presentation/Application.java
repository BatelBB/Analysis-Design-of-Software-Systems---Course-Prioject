package groupk.shared.presentation;

import groupk.shared.presentation.command.Command;
import groupk.shared.presentation.command.CreateEmployee;
import groupk.shared.presentation.command.Login;
import groupk.shared.presentation.command.Quit;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static void main(String[] args) {
        AtomicBoolean keepGoing = new AtomicBoolean(true);
        CommandRunner runner = new CommandRunner(
                new Command[] {
                        new Quit(),
                        new Login(),
                        new CreateEmployee()
                },
                () -> {
                        keepGoing.set(false);
                });

        Scanner input = new Scanner(System.in);

        runner.introduce();
        while(keepGoing.get()) {
            System.out.print("> ");
            String command = input.nextLine();
            runner.invoke(command);
        }
    }
}
