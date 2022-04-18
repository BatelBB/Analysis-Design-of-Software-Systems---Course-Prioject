import java.util.Date;
import java.util.Map;
public class ReportController {

    Map<String, Report> reports;


    public void addReport(String name, Report.ReportType reportType, Date date, String report_producer){

        try {
            /*if (categories.containsKey(name))
                throw new IllegalArgumentException("The categories already exists in the system");
            else {*/
            Report report = new Report(name, reportType, report_producer);
            reports.put(name,report);
            //}
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void removeReport(String name) throws Exception {
        try {
            reports.remove(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Report getReport(String name) throws Exception {
        try {
            return reports.get(name);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
