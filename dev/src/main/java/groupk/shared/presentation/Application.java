package groupk.shared.presentation;

import groupk.shared.presentation.command.*;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static void main(String[] args) {
        AtomicBoolean keepGoing = new AtomicBoolean(true);
        CommandRunner runner = new CommandRunner(
                new Command[] {
                        new Quit(),
                        new LoadSample(),
                        new Login(),
                        new CreateEmployee(),
                        new GetEmployee(),
                        new DeleteEmployee(),
                        new ListEmployees(),
                        new AddShiftPreference(),
                        new DeleteShiftPreference(),
                        new CreateShift(),
                        new ListShifts(),
                        new AddShiftStaff(),
                        new DeleteShiftStaff(),
                        new UpdateShiftRequiredRole(),
                        new CanWork(),
                        new CreateDelivery(),
                        new DeleteDelivery(),
                        new ListDeliveries(),
                        new AddDeliverySource(),
                        new AddDeliveryDestination(),
                        new AddDeliveryProduct(),
                        new UpdateDeliveryWeight(),
                        new DeleteDeliveryProduct(),
                        new CreateVehicle(),
                        new ListVehicles(),
                        new AddDriverLicense()
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
