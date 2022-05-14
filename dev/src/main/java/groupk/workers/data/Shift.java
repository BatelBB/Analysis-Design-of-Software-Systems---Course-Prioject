package groupk.workers.data;
import java.sql.*;
import java.util.*;

public class Shift {
    public enum Type {morning, evening};
    private Type type;
    private Calendar date;
    private LinkedList<Employee> staff;
    private HashMap<Employee.Role, Integer> requiredStaff;
    private int id;

    public Shift(Calendar date, Type type, LinkedList<Employee> staff, HashMap<Employee.Role, Integer> requiredStaff){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Shift");
            id = 1;
            while(resultSet.next())
                id ++;
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Shift VALUES(?,?,?)");
            preparedStatement.setString(1, type.name());
            preparedStatement.setString(2, date.toString());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            for (Employee.Role role : requiredStaff.keySet()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO RequiredStaff VALUES(?,?,?)");
                preparedStatement2.setInt(1, requiredStaff.get(role));
                preparedStatement2.setString(2, role.name());
                preparedStatement2.setInt(3, id);
                preparedStatement2.executeUpdate();
            }
            for(Employee employee : staff){
                PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO Workers VALUES(?,?)");
                preparedStatement3.setInt(1, id);
                preparedStatement3.setString(2, employee.getId());
                preparedStatement3.executeUpdate();
            }
            connection.close();
            this.type = type;
            this.date = date;
            this.staff = staff;
            this.requiredStaff = new HashMap<>();
            for (Employee.Role r : Employee.Role.values()) {
                if(requiredStaff.containsKey(r))
                    this.requiredStaff.put(r, requiredStaff.get(r));
                else {
                    if(type.equals(Type.morning))
                        this.requiredStaff.put(r, 1);
                    else{
                        if(!(r.equals(Employee.Role.HumanResources)|r.equals(Employee.Role.LogisticsManager)|r.equals(Employee.Role.StoreManager)))
                            this.requiredStaff.put(r, 1);
                        else
                            this.requiredStaff.put(r, 0);
                    }
                }
            }
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }

    public Type getType() {
        return type;
    }
    public Calendar getDate() {return date; }
    public LinkedList<Employee> getStaff() {return staff; }

    public HashMap<Employee.Role, Integer> getRequiredStaff() {
        return requiredStaff;
    }

    public Shift setRequiredStaff(HashMap<Employee.Role, Integer> requiredStaff){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM RequiredStaff WHERE ID = ?");
            preparedStatement.setInt(1 , id);
            preparedStatement.executeUpdate();
            for (Employee.Role role : requiredStaff.keySet()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO RequiredStaff VALUES(?,?,?)");
                preparedStatement2.setInt(1, requiredStaff.get(role));
                preparedStatement2.setString(2, role.name());
                preparedStatement2.setInt(3, id);
                preparedStatement2.executeUpdate();
                this.requiredStaff.put(role, requiredStaff.get(role));
            }
            connection.close();
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public Shift setRequiredRoleInShift(Employee.Role role, int number){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RequiredStaff set Count = ? where Role = ? and ID = ?");
            preparedStatement.setInt(1, number);
            preparedStatement.setString(2, role.name());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            connection.close();
            requiredStaff.replace(role, number);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public boolean isEmployeeWorking(String id){
        for (Employee e : staff){
            if(e.getId().equals(id))
                return true;
        }
        return false;
    }

    public Shift addEmployee(Employee e){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement  preparedStatement = connection.prepareStatement("INSERT INTO Workers VALUES(?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, e.getId());
            preparedStatement.executeUpdate();
            connection.close();
            staff.add(e);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }

    public Shift removeEmployee(Employee e){
        try{
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement  preparedStatement = connection.prepareStatement("DELETE FROM Workers ShiftID = ? and EmployeeID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, e.getId());
            preparedStatement.executeUpdate();
            connection.close();
            staff.remove(e);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return this;
    }
}
