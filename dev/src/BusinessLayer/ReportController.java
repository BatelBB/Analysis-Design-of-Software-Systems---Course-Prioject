package BusinessLayer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportController {

    private Map<Integer, Report> reports;
    private ProductController product_controller;
    private static ReportController reportController;

    public static ReportController getInstance() {
        if (reportController == null) reportController = new ReportController();
        return reportController;
    }

    private ReportController() {
        product_controller = ProductController.getInstance();
    }

    public void addReport(String name, Integer id, Date date, String report_producer) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            Report report = new Report(name, id, report_producer);
            reports.put(id, report);
        }
    }


    public void removeReport(Integer id) {
        reports.remove(id);
    }

    public Report getReport(Integer id) {
        return reports.get(id);
    }

    public Report createMissingReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> missingPro = product_controller.getMissingProducts();
            MissingReport report = new MissingReport(name, id, report_producer, missingPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createExpiredReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
            ExpiredReport report = new ExpiredReport(name, id, report_producer, ExpiredPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createSurplusesReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> SurplusesPro = product_controller.getSurplusProducts();
            SurplusesReport report = new SurplusesReport(name, id, report_producer, SurplusesPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createDefectiveReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
            DefectiveReport report = new DefectiveReport(name, id, report_producer, DefectivePro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createBySupplierReport(String name, Integer id, String report_producer, String suppName) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
            bySupplierReport report = new bySupplierReport(name, id, report_producer, bySupplierPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createByProductReport(String name, Integer id, String report_producer, String proName) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
            byProductReport report = new byProductReport(name, id, report_producer, byProductPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createByCategoryReport(String name, Integer id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        if (reports.containsKey(id)) throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
            byCategoryReport report = new byCategoryReport(name, id, report_producer, byCategoryPro);
            reports.put(id, report);
            return report;
        }
    }


}
