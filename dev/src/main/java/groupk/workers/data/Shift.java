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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Shift VALUES(?,?)");
            preparedStatement.setString(1, type.name());
            preparedStatement.setString(2, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
            preparedStatement.executeUpdate();
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
            for (Employee.Role role : this.requiredStaff.keySet()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO RequiredStaff VALUES(?,?,?,?)");
                preparedStatement2.setInt(1, this.requiredStaff.get(role));
                preparedStatement2.setString(2, role.name());
                preparedStatement2.setString(3, type.name());
                preparedStatement2.setString(4, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
                preparedStatement2.executeUpdate();
            }
            for(Employee employee : staff){
                PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO Workers VALUES(?,?,?)");
                preparedStatement3.setString(1, type.name());
                preparedStatement3.setString(2, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
                preparedStatement3.setString(3, employee.getId());
                preparedStatement3.executeUpdate();
            }
            connection.close();
            this.type = type;
            this.date = date;
            this.staff = staff;
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
            this.requiredStaff = null;
        }
    }

    public Shift(ResultSet shift, ResultSet requiredStaff, LinkedList<Employee> workers){
        try{
            String [] calendar = (shift.getString(2)).split("/");
            date = new GregorianCalendar(Integer.parseInt(calendar[2]) , Integer.parseInt(calendar[1])-1 , Integer.parseInt(calendar[0]));
            type = Type.valueOf(shift.getString(1));
            this.requiredStaff = new HashMap<>();
            try {
                while (requiredStaff.next()) {
                    this.requiredStaff.put(Employee.Role.valueOf(requiredStaff.getString(2)), requiredStaff.getInt(1));
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
            staff = workers;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM RequiredStaff WHERE Type = ? and Date = ?");
            preparedStatement.setString(1 , type.name());
            preparedStatement.setString(2 , date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
            preparedStatement.executeUpdate();
            for (Employee.Role role : requiredStaff.keySet()) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO RequiredStaff VALUES(?,?,?,?)");
                preparedStatement2.setInt(1, this.requiredStaff.get(role));
                preparedStatement2.setString(2, role.name());
                preparedStatement2.setString(3, type.name());
                preparedStatement2.setString(4, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
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
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RequiredStaff set Count = ? where Role = ? and Type = ? and Date = ?");
            preparedStatement.setInt(1, number);
            preparedStatement.setString(2, role.name());
            preparedStatement.setString(2, type.name());
            preparedStatement.setString(3, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
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
            PreparedStatement  preparedStatement = connection.prepareStatement("INSERT INTO Workers VALUES(?,?,?)");
            preparedStatement.setString(1, type.name());
            preparedStatement.setString(2, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
            preparedStatement.setString(3, e.getId());
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
            PreparedStatement  preparedStatement = connection.prepareStatement("DELETE FROM Workers Type = ? and Date = ? and EmployeeID = ?");
            preparedStatement.setString(1, type.name());
            preparedStatement.setString(2, date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1)  + "/" + date.get(Calendar.YEAR));
            preparedStatement.setString(3, e.getId());
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
