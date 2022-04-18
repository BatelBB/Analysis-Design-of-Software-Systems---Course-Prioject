import java.util.Date;
public class Report {

    enum ReportType{Missing,Expired,Surpluses,Defective,bySupplier,byProduct,byCategory}

    String name;
    ReportType reportType;
    Date date;
    String report_producer;

    public Report(String name, ReportType reportType, String report_producer) {
        this.name=name;
        this.reportType=reportType;
        date=new Date();
        this.report_producer=report_producer;
    }


}
