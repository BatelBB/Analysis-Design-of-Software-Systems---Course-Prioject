package groupk.workers.data;
import java.util.Calendar;
import java.util.Date;

public class WorkingConditions {
    private Calendar employmentStart;
    private int salaryPerHour;
    private int sickDaysUsed;
    private int vacationDaysUsed;

    public void setEmploymentStart(Calendar employmentStart) {
        this.employmentStart = employmentStart;
    }

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public void setSickDaysUsed(int sickDaysUsed) {
        this.sickDaysUsed = sickDaysUsed;
    }

    public void setVacationDaysUsed(int vacationDaysUsed) {
        this.vacationDaysUsed = vacationDaysUsed;
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
