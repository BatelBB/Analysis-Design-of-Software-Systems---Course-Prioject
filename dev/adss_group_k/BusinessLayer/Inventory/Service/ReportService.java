package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Controllers.ReportController;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Report;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.shared.response.*;

import java.util.List;

public class ReportService {
    private final ReportController report_controller;

    public ReportService(PersistenceController pc) {
        report_controller = ReportController.getInstance(pc);
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
            return ResponseT.success(new Report(report_controller.getReportForProduct(id)));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createMissingReport(String name, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createMissingReport(name, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createExpiredReport(String name, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createExpiredReport(name, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createSurplusesReport(String name, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createSurplusesReport(name, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createDefectiveReport(String name, String report_producer) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createDefectiveReport(name, report_producer);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createBySupplierReport(String name, String report_producer, int suppName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createBySupplierReport(name, report_producer, suppName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createByProductReport(String name, String report_producer, String proName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createByProductReport(name, report_producer, proName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Report> createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) {
        try {
            adss_group_k.BusinessLayer.Inventory.Report report = report_controller.createByCategoryReport(name, report_producer, CatName, subCatName, subSubCatName);
            return ResponseT.success(new Report(report));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }


    public void restart() {
        report_controller.restart();
    }


}
