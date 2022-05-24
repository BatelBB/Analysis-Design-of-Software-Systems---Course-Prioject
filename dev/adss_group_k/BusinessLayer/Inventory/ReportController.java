package adss_group_k.BusinessLayer.Inventory;

import java.util.*;
import java.util.List;

public class ReportController {

    private Map<String, Report> reports;

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

    public Report createMissingReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> missingPro = product_controller.getMissingProducts();
            Report report = new Report(name, id, Report.report_type.Missing,report_producer, missingPro,new LinkedList<>());
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public Report createExpiredReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
            Report report = new Report(name, id, Report.report_type.Expired,report_producer, new LinkedList<>(),ExpiredPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public Report createSurplusesReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> SurplusesPro = product_controller.getSurplusProducts();
            Report report = new Report(name, id, Report.report_type.Surpluses,report_producer, SurplusesPro,new LinkedList<>());
            reports.put(Integer.toString(id), report);
            return report;
        }
    }


    public Report createDefectiveReport(String name, int id, String report_producer) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
            Report report = new Report(name, id, Report.report_type.Defective,report_producer, new LinkedList<>(),DefectivePro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public Report createBySupplierReport(String name, int id, String report_producer, String suppName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
            Report report = new Report(name, id, Report.report_type.bySupplier, report_producer, new LinkedList<>(),bySupplierPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public Report createByProductReport(String name, int id, String report_producer, String proName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
            Report report = new Report(name, id, Report.report_type.byProduct,report_producer, new LinkedList<>(),byProductPro);
            reports.put(Integer.toString(id), report);
            return report;
        }
    }

    public Report createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
            Report report = new Report(name, id, Report.report_type.byCategory,report_producer, byCategoryPro,new LinkedList<>());
            reports.put(Integer.toString(id), report);
            return report;
        }
    }


    public void restart() {
        reports.clear();
    }
}
