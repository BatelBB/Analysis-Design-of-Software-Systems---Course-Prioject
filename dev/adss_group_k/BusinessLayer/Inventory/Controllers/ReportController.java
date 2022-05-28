package adss_group_k.BusinessLayer.Inventory.Controllers;

import adss_group_k.BusinessLayer.Inventory.Product;
import adss_group_k.BusinessLayer.Inventory.ProductItem;
import adss_group_k.BusinessLayer.Inventory.Report;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.readonly.ReportData;
import adss_group_k.shared.response.ResponseT;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ReportController {

    private int report_ids;
    private Map<Integer, Report> reports;
    private final ProductController product_controller;
    private static ReportController reportController;
    private final PersistenceController pc;

    //CONSTRUCTORS
    public static ReportController getInstance(PersistenceController pc) {
        if (reportController == null)
            reportController = new ReportController(pc);
        return reportController;
    }

    private ReportController(PersistenceController pc) {
        report_ids = 0;
        product_controller = ProductController.getInstance(pc);
        reports = new HashMap<>();
        this.pc = pc;
    }

    //METHODS
    public List<Integer> getReportListNames() {
        List<Integer> ReportListNames = new LinkedList<>();
        for (Map.Entry<Integer, Report> entry : reports.entrySet())
            ReportListNames.add(entry.getKey());
        return ReportListNames;

    }

    public void removeReport(int id) {
        if (!reports.containsKey(id))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            reports.remove(id);
        }
    }

    public Report getReport(int id) {
        if (!reports.containsKey(id))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            return reports.get(id);
        }
    }

    //CREATIONS
    public Report createMissingReport(String name, String report_producer) {
        List<Product> missingPro = product_controller.getMissingProducts();
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.Missing.ordinal(),
                "missing");
        Report report = new Report(name, report_ids, Report.report_type.Missing, report_producer, missingPro, new LinkedList<>());
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createExpiredReport(String name, String report_producer) {
        List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
        Report report = new Report(name, report_ids, Report.report_type.Expired, report_producer, new LinkedList<>(), ExpiredPro);
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createSurplusesReport(String name, String report_producer) {
        List<Product> SurplusesPro = product_controller.getSurplusProducts();
        Report report = new Report(name, report_ids, Report.report_type.Surpluses, report_producer, SurplusesPro, new LinkedList<>());
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createDefectiveReport(String name, String report_producer) {
        List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
        Report report = new Report(name, report_ids, Report.report_type.Defective, report_producer, new LinkedList<>(), DefectivePro);
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createBySupplierReport(String name, String report_producer, int suppName) {
        List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
        Report report = new Report(name, report_ids, Report.report_type.bySupplier, report_producer, new LinkedList<>(), bySupplierPro);
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createByProductReport(String name, String report_producer, String proName) {
        List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
        Report report = new Report(name, report_ids, Report.report_type.byProduct, report_producer, new LinkedList<>(), byProductPro);
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public Report createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) {
        List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
        Report report = new Report(name, report_ids, Report.report_type.byCategory, report_producer, byCategoryPro, new LinkedList<>());
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }


    public void restart() {
        reports.clear();
    }
}
