package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;
import groupk.shared.service.dto.Employee;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class LoadSample implements Command {
    @Override
    public String name() {
        return "load sample";
    }

    @Override
    public String description() {
        return "insert sample data";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("load sample");
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 2) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Usage:");
            System.out.println("> load sample");
            return;
        }

        System.out.println("Hang on! This might take a minute on slower drives.");

        Set<Employee.ShiftDateTime> all = new LinkedHashSet<Employee.ShiftDateTime>();
        all.add(Employee.ShiftDateTime.SundayMorning);
        all.add(Employee.ShiftDateTime.SundayEvening);
        all.add(Employee.ShiftDateTime.MondayMorning);
        all.add(Employee.ShiftDateTime.MondayEvening);
        all.add(Employee.ShiftDateTime.TuesdayMorning);
        all.add(Employee.ShiftDateTime.TuesdayEvening);
        all.add(Employee.ShiftDateTime.WednesdayMorning);
        all.add(Employee.ShiftDateTime.WednesdayEvening);
        all.add(Employee.ShiftDateTime.ThursdayMorning);
        all.add(Employee.ShiftDateTime.ThursdayEvening);
        all.add(Employee.ShiftDateTime.FridayMorning);
        all.add(Employee.ShiftDateTime.FridayEvening);
        all.add(Employee.ShiftDateTime.SaturdayMorning);
        all.add(Employee.ShiftDateTime.SaturdayEvening);

        // HR.
        runner.getService().createEmployee(
                "Avi",
                "111",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Eli",
                "112",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Dana",
                "113",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Noa",
                "114",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);

        // Cashiers.
        runner.getService().createEmployee(
                "Eli",
                "212",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Cashier,
                all);
        runner.getService().createEmployee(
                "Noa",
                "214",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Cashier,
                all);

        // Drivers:
        runner.getService().createEmployee(
                "Avi",
                "311",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Driver,
                all);
        runner.getService().createEmployee(
                "Dana",
                "313",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Driver,
                all);

        // Logistics:
        runner.getService().createEmployee(
                "Eli",
                "412",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Logistics,
                all);
        runner.getService().createEmployee(
                "Noa",
                "414",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Logistics,
                all);

        // Logistics Managers:
        runner.getService().createEmployee(
                "Eli",
                "512",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.LogisticsManager,
                all);
        runner.getService().createEmployee(
                "Noa",
                "514",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.LogisticsManager,
                all);

        // Shift Managers:
        runner.getService().createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                all);
        runner.getService().createEmployee(
                "Noa",
                "614",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                all);

        // Stockers:
        runner.getService().createEmployee(
                "Avi",
                "711",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Stocker,
                all);
        runner.getService().createEmployee(
                "Dana",
                "713",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Stocker,
                all);

        // Store Managers:
        runner.getService().createEmployee(
                "Avi",
                "811",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.StoreManager,
                all);
        runner.getService().createEmployee(
                "Dana",
                "813",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.StoreManager,
                all);

        // Store Managers:
        runner.getService().createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                all);
        runner.getService().createEmployee(
                "Dana",
                "913",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                all);

        // TODO Add more sample data.
    }
}
