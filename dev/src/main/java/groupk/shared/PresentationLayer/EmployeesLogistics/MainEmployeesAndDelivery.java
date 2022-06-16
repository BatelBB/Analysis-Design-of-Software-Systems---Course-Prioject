package groupk.shared.PresentationLayer.EmployeesLogistics;

import groupk.shared.PresentationLayer.EmployeesLogistics.command.*;
import groupk.shared.service.Service;
import groupk.shared.service.dto.Employee;

import java.sql.Connection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainEmployeesAndDelivery {
    public static void mainEmployeesAndDelivery(String[] args, Service service, Employee subject) {
        AtomicBoolean keepGoing = new AtomicBoolean(true);
        CommandRunner runner = new CommandRunner(
                new Command[] {
                        new Quit(),
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
                        //new DeleteDeliveryProduct(),
                        new CreateVehicle(),
                        new ListVehicles(),
                        new AddDriverLicense()
                },
                () -> {
                        keepGoing.set(false);
                }, service);

        Scanner input = new Scanner(System.in);

        runner.setSubject(subject);

        runner.introduce();
        while(keepGoing.get()) {
            System.out.print("> ");
            String command = input.nextLine();
            runner.invoke(command);
        }
    }
}
