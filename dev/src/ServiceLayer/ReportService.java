package ServiceLayer;

import BusinessLayer.ReportController;
import ServiceLayer.Objects.MissingReport;
import ServiceLayer.Objects.Report;

import java.util.List;

public class ReportService {
    private final ReportController report_controller;

    public ReportService() {
        report_controller = ReportController.getInstance();
    }

    public ResponseT<List<Integer>> getReportListNames() {
        try {
            return ResponseT.fromValue(report_controller.getReportListNames());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response removeReport(int id) {
        try {
            report_controller.removeReport(id);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Report> getReport(int id) {
        try {
            return ResponseT.fromValue(new Report(report_controller.getReport(id)));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<MissingReport> createMissingReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.MissingReport report = report_controller.createMissingReport(name, id, report_producer);
            return ResponseT.fromValue(new MissingReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.ExpiredReport> createExpiredReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.ExpiredReport report = report_controller.createExpiredReport(name, id, report_producer);
            return ResponseT.fromValue(new ServiceLayer.Objects.ExpiredReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.SurplusesReport> createSurplusesReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.SurplusesReport report = report_controller.createSurplusesReport(name, id, report_producer);
            return ResponseT.fromValue(new ServiceLayer.Objects.SurplusesReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.DefectiveReport> createDefectiveReport(String name, int id, String report_producer) {
        try {
            BusinessLayer.DefectiveReport report = report_controller.createDefectiveReport(name, id, report_producer);
            return ResponseT.fromValue(new ServiceLayer.Objects.DefectiveReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.bySupplierReport> createBySupplierReport(String name, int id, String report_producer, String suppName) {
        try {
            BusinessLayer.bySupplierReport report = report_controller.createBySupplierReport(name, id, report_producer, suppName);
            return ResponseT.fromValue(new ServiceLayer.Objects.bySupplierReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.byProductReport> createByProductReport(String name, int id, String report_producer, String proName) {
        try {
            BusinessLayer.byProductReport report = report_controller.createByProductReport(name, id, report_producer, proName);
            return ResponseT.fromValue(new ServiceLayer.Objects.byProductReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ServiceLayer.Objects.byCategoryReport> createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        try {
            BusinessLayer.byCategoryReport report = report_controller.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);
            return ResponseT.fromValue(new ServiceLayer.Objects.byCategoryReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    public void restart() {
        report_controller.restart();
    }
}
