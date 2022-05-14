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
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private BankAccount account;
    private WorkingConditions conditions;
    private Set<ShiftDateTime> availableShifts;
    public enum Role{
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
            preparedStatement.setString(6, employmentStart.toString());
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
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ShiftPreference VALUES(?,?)");
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
