package BusinessLayer;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportController {

    private Map<String, Report> reports;
    private ProductController product_controller;

    private static ReportController reportController;

    public static ReportController getInstance() {
        if (reportController == null) reportController = new ReportController();
        return reportController;
    }

    private ReportController() {
        product_controller = ProductController.getInstance();
        reports = new HashMap<>();
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
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            reports.remove(Integer.toString(id));
        }
    }

    public Report getReport(int id) {
        if (!reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("Category doesn't exists");
        else {
            return reports.get(Integer.toString(id));
        }
    }

    public MissingReport createMissingReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> missingPro = product_controller.getMissingProducts();
            MissingReport report = new MissingReport(name, id, report_producer, missingPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public ExpiredReport createExpiredReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
            ExpiredReport report = new ExpiredReport(name, id, report_producer, ExpiredPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public SurplusesReport createSurplusesReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> SurplusesPro = product_controller.getSurplusProducts();
            SurplusesReport report = new SurplusesReport(name, id, report_producer, SurplusesPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }


    public DefectiveReport createDefectiveReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
            DefectiveReport report = new DefectiveReport(name, id, report_producer, DefectivePro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public bySupplierReport createBySupplierReport(String name, int id, String report_producer, String suppName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
            bySupplierReport report = new bySupplierReport(name, id, report_producer, bySupplierPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public byProductReport createByProductReport(String name, int id, String report_producer, String proName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
            byProductReport report = new byProductReport(name, id, report_producer, byProductPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public byCategoryReport createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
            byCategoryReport report = new byCategoryReport(name, id, report_producer, byCategoryPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }


}
