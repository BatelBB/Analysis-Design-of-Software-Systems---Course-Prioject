package groupk.workers.data;
import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DalController {
    public static String url ;
    private ShiftRepository shiftRepository;
    private EmployeeRepository employeeRepository;
    public static File file;
    public DalController() {
        file = new File("employeeDB.db");
        url = ("jdbc:sqlite:").concat(file.getAbsolutePath());
        try{
            if(!file.exists()) {
                createTables();
            }
            load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        shiftRepository = new ShiftRepository();
        employeeRepository = new EmployeeRepository();
    }

    public void createTables() {
        LinkedList<String> tables = new LinkedList<>();
        tables.add("CREATE TABLE IF NOT EXISTS \"Employee\" (\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"BankName\"\tTEXT,\n" +
                "\t\"BankBranch\"\tINTEGER,\n" +
                "\t\"BankID\"\tINTEGER,\n" +
                "\t\"EmploymentStart\"\tTEXT,\n" +
                "\t\"SickDaysUsed\"\tINTEGER,\n" +
                "\t\"VacationDaysUsed\"\tINTEGER,\n" +
                "\t\"SalaryPerHour\"\tINTEGER,\n" +
                "\t\"Role\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Role\") REFERENCES \"Role\"(\"Name\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"RequiredStaff\" (\n" +
                "\t\"Count\"\tINTEGER,\n" +
                "\t\"Role\"\tTEXT,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\",\"Role\"),\n" +
                "\tFOREIGN KEY(\"ID\") REFERENCES \"Shift\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Role\") REFERENCES \"Role\"(\"Name\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"Role\" (\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Name\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"Shift\" (\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Date\",\"Type\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"ShiftPreference\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"ShiftType\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"ShiftType\") REFERENCES \"ShiftSlot\"(\"Type\"),\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftType\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employee\"(\"ID\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"ShiftSlot\" (\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Type\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"Workers\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shift\"(\"ID\"),\n" +
                "\tPRIMARY KEY(\"ShiftID\",\"EmployeeID\")\n" +
                ");");
        try (
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()){
            for (String table : tables) {
                statement.addBatch(table);
            }
            statement.executeBatch();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public void load(){
        try{
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            for (Employee.Role role: Employee.Role.values()) {
                String insertRole = "INSERT OR IGNORE INTO Role VALUES ('"+ role.name()+"')";
                statement.executeUpdate(insertRole);
            }
            for (Employee.ShiftDateTime shift: Employee.ShiftDateTime.values()) {
                String insertShift = "INSERT OR IGNORE INTO ShiftSlot VALUES ('" + shift.name() + "')";
                statement.executeUpdate(insertShift);
            }
            connection.close();
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }

    //for test use
    public void deleteDataBase(){
        File file = new File("employeeDB.db");
        if(file.exists())
            file.delete();
    }

    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    public Employee getEmployee(String id) {
        return employeeRepository.getEmployee(id);
    }

    public Employee addEmployee(String name, String id, String bank, int bankID, int bankBranch,
                                Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Employee.Role role, Set<Employee.ShiftDateTime> shiftPreferences) {
        return employeeRepository.addEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role, shiftPreferences);
    }

    public Employee deleteEmployee(String id) {
        return employeeRepository.deleteEmployee(id);
    }

    public LinkedList<Shift> getShifts() {
        return shiftRepository.getShifts();
    }

    public Shift addShift(Shift shift) {
        return shiftRepository.addShift(shift);
    }
}

