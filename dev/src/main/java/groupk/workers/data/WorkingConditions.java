package groupk.workers.data;
import java.util.Date;

public class WorkingConditions {
    private Date employmentStart;
    public int salaryPerHour;
    private int sickDaysUsed;
    private int vacationDaysUsed;

    public WorkingConditions(Date employmentStart, int salaryPerHour, int sickDaysUsed, int vacationDaysUsed){
        this.employmentStart = employmentStart;
        this.salaryPerHour = salaryPerHour;
        this.sickDaysUsed = sickDaysUsed;
        this. vacationDaysUsed = vacationDaysUsed;

    }
    public Date getEmploymentStart() {
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
