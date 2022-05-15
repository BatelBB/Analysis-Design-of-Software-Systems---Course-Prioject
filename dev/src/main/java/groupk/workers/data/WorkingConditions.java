package groupk.workers.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class WorkingConditions {
    private Calendar employmentStart;
    private int salaryPerHour;
    private int sickDaysUsed;
    private int vacationDaysUsed;

    public void setEmploymentStart(String id, Calendar employmentStart) {
        try {
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set EmploymentStart = ? where ID = ?");
            preparedStatement.setString(1, employmentStart.get(Calendar.DATE) + "/" + (employmentStart.get(Calendar.MONTH)+1)  + "/" + employmentStart.get(Calendar.YEAR));
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.employmentStart = employmentStart;
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public void setSalaryPerHour(String id, int salaryPerHour) {
        try {
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set SalaryPerHour = ? where ID = ?");
            preparedStatement.setInt(1, salaryPerHour);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.salaryPerHour = salaryPerHour;
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public void setSickDaysUsed(String id, int sickDaysUsed) {
        try {
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set SickDaysUsed = ? where ID = ?");
            preparedStatement.setInt(1, sickDaysUsed);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.sickDaysUsed = sickDaysUsed;
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public void setVacationDaysUsed(String id, int vacationDaysUsed) {
        try {
            Connection connection = DriverManager.getConnection(DalController.url);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Employee set VacationDaysUsed = ? where ID = ?");
            preparedStatement.setInt(1, vacationDaysUsed);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            connection.close();
            this.vacationDaysUsed = vacationDaysUsed;
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public WorkingConditions(Calendar employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed){
        this.employmentStart = employmentStart;
        this.salaryPerHour = salaryPerHour;
        this.sickDaysUsed = sickDaysUsed;
        this. vacationDaysUsed = vacationDaysUsed;

    }
    public Calendar getEmploymentStart() {
        return employmentStart;
    }
    public int getSalaryPerHour() {
        return salaryPerHour;
    }
    public int getSickDaysUsed() {
        return sickDaysUsed;
    }
    public int getVacationDaysUsed() {
        return vacationDaysUsed;
    }

}
