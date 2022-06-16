package groupk.shared.PresentationLayer.EmployeesLogistics.command;

import groupk.shared.PresentationLayer.EmployeesLogistics.CommandRunner;
import groupk.shared.service.Response;
import groupk.shared.service.dto.Delivery;
import groupk.shared.service.dto.Employee;
import groupk.shared.service.dto.Site;

import java.util.List;

public class ListDeliveries implements Command {
    @Override
    public String name() {
        return "list deliveries";
    }

    @Override
    public String description() {
        return "print a list of deliveries";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("list deliveries");
    }

    @Override
    public boolean isVisible(Employee.Role role) {
        return role == Employee.Role.LogisticsManager || role == Employee.Role.TruckingManger;
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 2) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Usage:");
            System.out.println("> list deliveries");
            return;
        }

        Response<List<Delivery>> response = runner.getService().listDeliveries(runner.getSubject());
        if (response.isError()) {
            System.out.printf("Error: %s\n", response.getErrorMessage());
            return;
        }

        for (Delivery d : response.getValue()) {
            System.out.printf("- id: %s\n  driver: %s\n  date:%s\n  sources:\n", d.id, d.driverID, d.date);
            for (Site s : d.sources) {
                System.out.printf("  - %s, %s, %s, %s, %s, %s, %s\n", s.area, s.city, s.street, s.houseNumber, s.apartment, s.contactName, s.contactPhone);
            }
            System.out.println("  destinations:");
            for (Site s : d.destinations) {
                System.out.printf("  - %s, %s, %s, %s, %s, %s, %s\n", s.area, s.city, s.street, s.houseNumber, s.apartment, s.contactName, s.contactPhone);
            }
            System.out.println("  orders:");
            for (Integer o : d.orders) {
                System.out.printf("  - order id: %s\n", o.intValue());
            }
        }
    }
}


