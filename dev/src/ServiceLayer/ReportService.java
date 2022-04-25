package ServiceLayer;

import BusinessLayer.ReportController;
import ServiceLayer.Objects.MissingReport;
import ServiceLayer.Objects.Report;

import java.util.List;

public class ReportService {

    private final ReportController reportController;

    public ReportService() {
        reportController = ReportController.getInstance();
    }

    public static List<Integer> getReportListNames() { return ReportController.getReportListNames();}

    public void removeReport(int id) {
        try {
            reportController.removeReport(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Report getReport(int id) {
        try {
            return new Report(reportController.getReport(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public MissingReport createMissingReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.MissingReport report = reportController.createMissingReport(name, id, report_producer);
            return new MissingReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.ExpiredReport createExpiredReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.ExpiredReport report = reportController.createExpiredReport(name, id, report_producer);
            return new ServiceLayer.Objects.ExpiredReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.SurplusesReport createSurplusesReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.SurplusesReport report = reportController.createSurplusesReport(name, id, report_producer);
            return new ServiceLayer.Objects.SurplusesReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.DefectiveReport createDefectiveReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.DefectiveReport report = reportController.createDefectiveReport(name, id, report_producer);
            return new ServiceLayer.Objects.DefectiveReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.bySupplierReport createBySupplierReport(String name, int id, String report_producer, String suppName) {
        try {
            BusinessLayer.bySupplierReport report = reportController.createBySupplierReport(name, id, report_producer, suppName);
            return new ServiceLayer.Objects.bySupplierReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.byProductReport createByProductReport(String name, int id, String report_producer, String proName) {
        try {
            BusinessLayer.byProductReport report = reportController.createByProductReport(name, id, report_producer, proName);
            return new ServiceLayer.Objects.byProductReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServiceLayer.Objects.byCategoryReport createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        try {
            BusinessLayer.byCategoryReport report = reportController.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);
            return new ServiceLayer.Objects.byCategoryReport(report);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
