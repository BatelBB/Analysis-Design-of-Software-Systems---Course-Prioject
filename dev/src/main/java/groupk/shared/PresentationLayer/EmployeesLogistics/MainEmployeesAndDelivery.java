package groupk.shared.PresentationLayer.EmployeesLogistics;

import groupk.shared.PresentationLayer.EmployeesLogistics.command.*;

import java.sql.Connection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainEmployeesAndDelivery {
    public static void mainEmployeesAndDelivery(String[] args, Connection conn) {
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
                        new AddOrderToDelivery(),
                        new UpdateDeliveryWeight(),
                        new DeleteOrderFromDelivery(),
                        new CreateVehicle(),
                        new ListVehicles(),
                        new AddDriverLicense(),
                        new GetDeliveryByOrder(),
                        new GetDeliveryRequests(),
                        new ConfirmDeliveryRequest()
                },
                () -> {
                        keepGoing.set(false);
                }, conn);

        Scanner input = new Scanner(System.in);

        runner.introduce();
        while(keepGoing.get()) {
            System.out.print("> ");
            String command = input.nextLine();
            runner.invoke(command);
        }
    }
}
