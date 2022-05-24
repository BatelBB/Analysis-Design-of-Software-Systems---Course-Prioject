package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.Date;
import java.util.List;

import static adss_group_k.BusinessLayer.Inventory.Report.report_type.*;

public class Report {

    Integer id;
    String name;
    Date date;
    String report_producer;
    adss_group_k.BusinessLayer.Inventory.Report.report_type reportType;



    public Report(adss_group_k.BusinessLayer.Inventory.Report report) {
        id= report.getId();
        name=report.getName();
        date= report.getDate();
        report_producer= report.getReport_producer();
        reportType=report.getReportType();
    }

    public String getName() { return name; }


}
