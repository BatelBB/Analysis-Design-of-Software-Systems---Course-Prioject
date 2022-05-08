package adss_group_k.inventory.ServiceLayer;

import adss_group_k.inventory.BusinessLayer.ReportController;
import adss_group_k.inventory.ServiceLayer.Objects.MissingReport;
import adss_group_k.inventory.ServiceLayer.Objects.Report;
import adss_group_k.inventory.ServiceLayer.Objects.*;

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
            adss_group_k.inventory.BusinessLayer.MissingReport report = report_controller.createMissingReport(name, id, report_producer);
            return ResponseT.fromValue(new MissingReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ExpiredReport> createExpiredReport(String name, int id, String report_producer) {
        try {
            adss_group_k.inventory.BusinessLayer.ExpiredReport report = report_controller.createExpiredReport(name, id, report_producer);
            return ResponseT.fromValue(new ExpiredReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<SurplusesReport> createSurplusesReport(String name, int id, String report_producer) {
        try {
            adss_group_k.inventory.BusinessLayer.SurplusesReport report = report_controller.createSurplusesReport(name, id, report_producer);
            return ResponseT.fromValue(new SurplusesReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<DefectiveReport> createDefectiveReport(String name, int id, String report_producer) {
        try {
            adss_group_k.inventory.BusinessLayer.DefectiveReport report = report_controller.createDefectiveReport(name, id, report_producer);
            return ResponseT.fromValue(new DefectiveReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<bySupplierReport> createBySupplierReport(String name, int id, String report_producer, String suppName) {
        try {
            adss_group_k.inventory.BusinessLayer.bySupplierReport report = report_controller.createBySupplierReport(name, id, report_producer, suppName);
            return ResponseT.fromValue(new bySupplierReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<byProductReport> createByProductReport(String name, int id, String report_producer, String proName) {
        try {
            adss_group_k.inventory.BusinessLayer.byProductReport report = report_controller.createByProductReport(name, id, report_producer, proName);
            return ResponseT.fromValue(new byProductReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<byCategoryReport> createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        try {
            adss_group_k.inventory.BusinessLayer.byCategoryReport report = report_controller.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);
            return ResponseT.fromValue(new byCategoryReport(report));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    public void restart() {
        report_controller.restart();
    }
}
