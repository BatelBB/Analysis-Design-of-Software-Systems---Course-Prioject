package adss_group_k.inventory.BusinessLayer;

import java.util.*;
import java.util.List;

public class ReportController {

    private Map<String, Report> reports;
    static int i=0; //index for the name of the missing product

    private final ProductController product_controller;

    private static ReportController reportController;

    public static ReportController getInstance() {
        if (reportController == null)
            reportController = new ReportController();
        return reportController;
    }

    private ReportController() {
        product_controller = ProductController.getInstance();
        reports = new HashMap<>();
    }

    public List<Integer> getReportListNames() {
        List<Integer> ReportListNames = new LinkedList<>();
        for (Map.Entry<String, Report> entry : reports.entrySet()) {
            ReportListNames.add(Integer.valueOf(entry.getKey()));
        }
        return ReportListNames;

    }

    /*public void addReport(String name, int id, Date date, String report_producer){
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            Report report = new Report(name, id, report_producer);
            reports.put(Integer.toString(id),report);
        }
    }*/


    public void removeReport(int id) {
        if (!reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            reports.remove(Integer.toString(id));
        }
    }

    public Report getReport(int id) {
        if (!reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            return reports.get(Integer.toString(id));
        }
    }

    public MissingReport createMissingReport(String name, String report_producer) {
            List<Product> missingPro = product_controller.getMissingProducts();
            MissingReport report = new MissingReport(name, report_producer, missingPro);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }

    public ExpiredReport createExpiredReport(String name, String report_producer) {
            List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
            ExpiredReport report = new ExpiredReport(name, report_producer, ExpiredPro);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }

    public SurplusesReport createSurplusesReport(String name, String report_producer) {
            List<Product> SurplusesPro = product_controller.getSurplusProducts();
            SurplusesReport report = new SurplusesReport(name,report_producer, SurplusesPro);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }


    public DefectiveReport createDefectiveReport(String name, String report_producer) {
            List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
            DefectiveReport report = new DefectiveReport(name, report_producer, DefectivePro);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }

    public bySupplierReport createBySupplierReport(String name, String report_producer, String suppName) {
            List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
            bySupplierReport report = new bySupplierReport(name, report_producer, bySupplierPro, suppName);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }

    public byProductReport createByProductReport(String name, String report_producer, String proName) {
            List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
            byProductReport report = new byProductReport(name, report_producer, byProductPro, proName);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }

    public byCategoryReport createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) {
            List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
            byCategoryReport report = new byCategoryReport(name, report_producer, byCategoryPro, CatName);
            reports.put(Integer.toString(report.getId()), report);
            return report;

    }


    public void restart() {
        reports.clear();
    }

    public Map<String, Integer> GetProAmount() {
        Map<String, Integer> map=new HashMap<>();
        MissingReport missingReport=createMissingReport("Missing_Report_"+i,"system");
        i++;
        List<Product> missingPro=missingReport.getMissingPro();
        for (Product p:missingPro
             ) {
            map.put(p.getName(),p.getMin_qty());
        }
        return map;
    }
}
