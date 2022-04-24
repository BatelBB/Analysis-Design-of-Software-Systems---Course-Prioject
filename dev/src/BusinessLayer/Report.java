package BusinessLayer;

import java.util.Date;
public class Report {

    enum ReportType{Missing,Expired,Surpluses,Defective,bySupplier,byProduct,byCategory}

    Integer id;
    String name;
    Date date;
    String report_producer;

    public Report(String name, Integer id, String report_producer) {
        this.name=name;
        this.id=id;
        date=new Date();
        this.report_producer=report_producer;
    }




}
