package BusinessLayer;

import java.util.Date;
import java.util.Map;
public class ReportController {

    private Map<String, Report> reports;
    private ProductController productController;
    private static ReportController reportController;

    public static ReportController getInstance()
    {
        if (reportController==null)
            reportController= new ReportController();
        return reportController;
    }

    private ReportController(){
        productController = ProductController.getInstance();
    }

    public void addReport(String name, int id, Date date, String report_producer){
        if (reports.containsKey(Integer.toString(id)))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            Report report = new Report(name, id, report_producer);
            reports.put(id,report);
            }
        }


    public void removeReport(Integer id) { reports.remove(id); } //להוסיף זריקות חריגה

    public Report getReport(Integer id) { return reports.get(id); } //להוסיף

    public Report createMissingReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> missingPro = productController.getMissingProduct();
            MissingReport report = new MissingReport(name, id, report_producer, missingPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createExpiredReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> ExpiredPro = productController.getExpiredProduct();
            ExpiredReport report = new ExpiredReport(name, id, report_producer, ExpiredPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createSurplusesReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> SurplusesPro = productController.getSurplusesProduct();
            SurplusesReport report = new SurplusesReport(name, id, report_producer, SurplusesPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createSurplusesReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> SurplusesPro = productController.getSurplusesProduct();
            SurplusesReport report = new SurplusesReport(name, id, report_producer, SurplusesPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createDefectiveReport(String name, Integer id, String report_producer) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> DefectivePro = productController.getDefectiveProduct();
            DefectiveReport report = new DefectiveReport(name, id, report_producer, DefectivePro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createBySupplierReport(String name, Integer id, String report_producer, String suppName) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> bySupplierPro = productController.getbySupplierProduct(suppName);
            bySupplierReport report = new bySupplierReport(name, id, report_producer, bySupplierPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createByProductReport(String name, Integer id, String report_producer, String proName) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<ProductItem> byProductPro = productController.getByProduct(proName);
            byProductReport report = new byProductReport(name, id, report_producer, byProductPro);
            reports.put(id, report);
            return report;
        }
    }

    public Report createByCategoryReport(String name, Integer id, String report_producer, String CatName) {
        if (reports.containsKey(id))
            throw new IllegalArgumentException("The ReportId already exists in the system");
        else {
            List<Product> byCategoryPro = productController.getByCategoryProduct(CatName);
            byCategoryReport report = new byCategoryReport(name, id, report_producer, byCategoryPro);
            reports.put(id, report);
            return report;
        }
    }


}
