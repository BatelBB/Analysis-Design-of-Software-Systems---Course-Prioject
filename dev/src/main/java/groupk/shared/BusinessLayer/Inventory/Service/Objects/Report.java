package groupk.shared.BusinessLayer.Inventory.Service.Objects;

import groupk.shared.BusinessLayer.Inventory.Product;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static groupk.shared.BusinessLayer.Inventory.Report.report_type.*;

public class Report {

    Integer id;
    String name;
    LocalDate date;
    String report_producer;
    groupk.shared.BusinessLayer.Inventory.Report.report_type reportType;


    public Report(groupk.shared.BusinessLayer.Inventory.Report report) {
        id = report.getId();
        name = report.getName();
        date = report.getDate();
        report_producer = report.getReport_producer();
        reportType = report.getReportType();
    }

    public String getName() {
        return name;
    }


    public Integer getId() {
        return id;
    }
}
