package groupk.shared.presentation;

import groupk.shared.presentation.command.Command;
import groupk.shared.service.Service;
import groupk.shared.service.dto.Employee;
import groupk.workers.data.Shift;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CommandRunner {
    private final Command[] commands;
    private final Runnable onStop;
    private String subject;
    private Service service;

    public CommandRunner(Command[] commands, Runnable onStop) {
        this.commands = commands;
        this.onStop = onStop;
        this.service = new Service();
    }

    public void invoke(String line) {
        for (Command command: commands) {
            if (command.isMatching(line)) {
                command.execute(line.split(" "), this);
                return;
            }
        }
        help();
    }

    public void introduce() {
        System.out.println("For usage information enter 'help'.");
    }

    public void stop() {
        onStop.run();
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public Service getService() {
        return service;
    }

    private void help() {
        System.out.println("Supported commands:");
        int indent = Arrays.stream(commands)
                .map(command -> command.name().length())
                .max(Integer::compareTo)
                .orElse(0) + 2;
        for (Command command: commands) {
            System.out.println(
                    "  "
                    + command.name()
                    + " ".repeat(indent - command.name().length())
                    + command.description());
        }
        System.out.println("For command usage, enter the command without arguments.");
    }

    // Parses dates in YYYY-MM-DD format, for example 2022-05-17.
    public static Calendar parseDate(String date) {
        String[] split = date.split("-");
        if (split.length != 3 || split[0].length() != 4 || split[1].length() != 2 || split[2].length() != 2) {
            throw new IllegalArgumentException("must follow yyyy-mm-dd format, for example 2022-04-25.");
        }
        try {
            return new GregorianCalendar(
                    Integer.parseInt(split[0]),
                    // Why -1 you ask? Because the Java library can not even be consistent within
                    // the arguments of a single constructor. Months are indexed from 0.
                    Integer.parseInt(split[1]) - 1,
                    Integer.parseInt(split[2])
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("must follow yyyy-mm-dd format, for example 2022-04-25.");
        }
    }

    public static Employee.Role parseRole(String role) {
        try {
            return Employee.Role.valueOf(role);
        } catch (IllegalArgumentException  e) {
            String roles = Arrays.stream(Employee.Role.values())
                    .map(Enum::toString)
                    .reduce((String acc, String cur) -> acc + ", " + cur)
                    .get();
            throw new IllegalArgumentException("must be one of: \n" + roles + ".");
        }
    }

    public static Shift.Type parseShiftType(String type) {
        try {
            return Shift.Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            String types = Arrays.stream(Shift.Type.values())
                    .map(Enum::toString)
                    .reduce((String acc, String cur) -> acc + ", " + cur)
                    .get();
            throw new IllegalArgumentException("must be one of: \n" + types + ".");
        }
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("must be an integer.");
        }
    }
}
