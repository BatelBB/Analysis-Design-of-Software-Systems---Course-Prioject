package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.ReportController;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Report;
import adss_group_k.shared.response.*;

import java.util.List;

public class ReportService {
    private final ReportController report_controller;

    public ReportService() {
        report_controller = ReportController.getInstance();
    }

    public ResponseT<List<Integer>> getReportListNames() {
        try {
            return ResponseT.success(report_controller.getReportListNames());
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public Response removeReport(int id) {
        try {
            report_controller.removeReport(id);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public ResponseT<Report> getReport(int id) {
        try {
            return ResponseT.success(new Report(report_controller.getReport(id)));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createMissingReport(String name, int id, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createMissingReport(name, id, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createExpiredReport(String name, int id, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createExpiredReport(name, id, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createSurplusesReport(String name, int id, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createSurplusesReport(name, id, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createDefectiveReport(String name, int id, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createDefectiveReport(name, id, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createBySupplierReport(String name, int id, String report_producer, String suppName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createBySupplierReport(name, id, report_producer, suppName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createByProductReport(String name, int id, String report_producer, String proName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createByProductReport(name, id, report_producer, proName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createByCategoryReport(String name, int id, String report_producer, String CatName, String subCatName, String subSubCatName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createByCategoryReport(name, id, report_producer, CatName, subCatName, subSubCatName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }


    public void restart() {
        report_controller.restart();
    }


}
