package groupk.workers.data;

import javax.management.relation.RoleInfoNotFoundException;
import java.sql.*;
import java.util.*;
import groupk.workers.data.DalController;

public class Employee {
    public static enum ShiftDateTime {
        SundayMorning,
        SundayEvening,
        MondayMorning,
        MondayEvening,
        TuesdayMorning,
        TuesdayEvening,
        WednesdayMorning,
        WednesdayEvening,
        ThursdayMorning,
        ThursdayEvening,
        FridayMorning,
        FridayEvening,
        SaturdayMorning,
        SaturdayEvening
    }

    public static ShiftDateTime toShiftDateTime(Calendar date, boolean isEvening) {
        ShiftDateTime s = ShiftDateTime.values()[((date.get(Calendar.DAY_OF_WEEK)) - 1) * 2 + (isEvening ? 1 : 0)];
        return s;
    }

    private String name;
    private String id;

    public void setName(String name) {
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set Name = ? where ID = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.name = name;
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }

    public void setId(String id) {
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set ID = ? where ID = ?");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, this.id);
            preparedStatement.executeUpdate();
            connection.close();
            this.id = id;
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }

    public void setRole(Role role) {
        try {
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set Role = ? where ID = ?");
            preparedStatement.setString(1, role.name());
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.role = role;
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    private BankAccount account;
    private WorkingConditions conditions;
    private Set<ShiftDateTime> availableShifts;
    public static enum Role{
        Logistics,
        HumanResources,
        Stocker,
        Cashier,
        LogisticsManager,
        ShiftManager,
        Driver,
        StoreManager,
        TruckingManger
    };
    private Role role;

    public Employee(String name, String id, String bank, int bankID, int bankBranch,
                    Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed, Role role, Set<ShiftDateTime> availableShifts){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3 , bank);
            preparedStatement.setInt(4, bankBranch);
            preparedStatement.setInt(5, bankID);
            preparedStatement.setString(6, employmentStart.get(Calendar.DATE) + "/" + (employmentStart.get(Calendar.MONTH)+1)  + "/" + employmentStart.get(Calendar.YEAR));
            preparedStatement.setInt(7, sickDaysUsed);
            preparedStatement.setInt(8, vacationDaysUsed);
            preparedStatement.setInt(9, salaryPerHour);
            preparedStatement.setString(10, role.name());
            preparedStatement.executeUpdate();
            for (ShiftDateTime shift:availableShifts) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO ShiftPreference VALUES(?,?)");
                preparedStatement2.setString(1, id);
                preparedStatement2.setString(2, shift.name());
                preparedStatement2.executeUpdate();
            }
            connection.close();
            this.name = name;
            this.id = id;
            account = new BankAccount(bank, bankID, bankBranch);
            conditions = new WorkingConditions(employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed);
            this.role = role;
            this.availableShifts = availableShifts;
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }

    public Employee(ResultSet employee){
        try{
            id = employee.getString(1);
            name = employee.getString(2);
            account = new BankAccount(employee.getString(3), employee.getInt(5), employee.getInt(4));
            String [] calendar = (employee.getString(6)).split("/");
            conditions = new WorkingConditions(new GregorianCalendar(Integer.parseInt(calendar[0]) , Integer.parseInt(calendar[1])-1, Integer.parseInt(calendar[2])) ,employee.getInt(9), employee.getInt(7), employee.getInt(8));
            role = Role.valueOf(employee.getString(10));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void setAvailableShifts(ResultSet shifts) {
        try {
            availableShifts = new HashSet<>();
            while (shifts.next()) {
                availableShifts.add(ShiftDateTime.valueOf(shifts.getString(2)));
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public BankAccount getAccount() { return account; }

    public WorkingConditions getConditions() { return conditions; }

    public Set<ShiftDateTime> getAvailableShifts() { return availableShifts; }

    public Role getRole() { return role;}

    public Employee setAvailableShifts(Set<ShiftDateTime> shiftPreferences) {
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            for (ShiftDateTime shift:availableShifts) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RequiredStaff set Count = ? where Role = ? and ID = ?");
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, shift.name());
                preparedStatement.executeUpdate();
            }
            connection.close();
            availableShifts = shiftPreferences;
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public Employee addEmployeeShiftPreference(ShiftDateTime shift){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ShiftPreference VALUES(?,?)");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, shift.name());
            preparedStatement.executeUpdate();
            connection.close();
            availableShifts.add(shift);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public Employee deleteEmployeeShiftPreference(ShiftDateTime shift){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            String deleteShift = "DELETE FROM ShiftPreference WHERE ShiftType = '" + shift.name() + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteShift);
            preparedStatement.executeUpdate();
            connection.close();
            availableShifts.remove(shift);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public boolean isShiftpreferred(ShiftDateTime shift){
        return availableShifts.contains(shift);
    }
}
