package groupk.shared.presentation;

import groupk.shared.presentation.command.Command;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        CommandRunner runner = new CommandRunner(new Command[] {});

        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;

    }
}
