package groupk.workers.data;
import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DalController {
    private String url ;
    private ShiftRepository shiftRepository;
    private EmployeeRepository employeeRepository;

    public DalController() {
        File file = new File("employeeDB.db");
        url = ("jdbc:sqlite:").concat(file.getAbsolutePath());
        try{
            if(!file.exists()) {
                createTables();
                load();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        shiftRepository = new ShiftRepository();
        employeeRepository = new EmployeeRepository();
    }

    public void createTables() {
        LinkedList<String> tables = new LinkedList<>();
        tables.add("CREATE TABLE IF NOT EXISTS \"Employee\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
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
                "\t\"Role\"\tINTEGER,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\",\"Role\"),\n" +
                "\tFOREIGN KEY(\"Role\") REFERENCES \"Role\"(\"Name\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"Role\" (\n" +
                "\t\"Name\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Name\")\n" +
                ");");
        tables.add("CREATE TABLE IF NOT EXISTS \"Shift\" (\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\t\"RequiredStaffID\"\tINTEGER,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"RequiredStaffID\") REFERENCES \"RequiredStaff\"(\"ID\"),\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
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
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(Logistics)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(HumanResources)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(Cashier)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(LogisticsManager)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(ShiftManager)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(Driver)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(StoreManager)");
            statement.executeUpdate("INSERT INTO Role(Name) VALUES(TruckingManger)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(SundayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(SundayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(MondayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(MondayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(TuesdayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(TuesdayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(WednesdayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(WednesdayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(ThursdayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(ThursdayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(FridayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(FridayEvening)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(SaturdayMorning)");
            statement.executeUpdate("INSERT INTO ShiftSlot(Type) VALUES(SaturdayEvening)");
            connection.close();
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
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

