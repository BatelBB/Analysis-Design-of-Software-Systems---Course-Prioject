package adss_group_k.BusinessLayer.Inventory.Controllers;

import adss_group_k.BusinessLayer.Inventory.Product;
import adss_group_k.BusinessLayer.Inventory.ProductItem;
import adss_group_k.BusinessLayer.Inventory.Report;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.dataLayer.records.readonly.ProductInReportData;
import adss_group_k.dataLayer.records.readonly.ProductItemInReportData;
import adss_group_k.dataLayer.records.readonly.ReportData;
import adss_group_k.shared.response.ResponseT;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ReportController {

    private int report_ids;
    private final Map<Integer, Report> reports;
    private final ProductController product_controller;
    private static ReportController reportController;
    private final PersistenceController pc;

    //CONSTRUCTORS
    public static ReportController getInstance(PersistenceController pc) {
        if (reportController == null)
            reportController = new ReportController(pc);
        return reportController;
    }

    private ReportController(PersistenceController pc) {
        report_ids = 0;
        product_controller = ProductController.getInstance(pc);
        reports = new HashMap<>();
        this.pc = pc;
    }

    //METHODS
    public List<Integer> getReportListNames() {
        List<Integer> ReportListNames = new LinkedList<>();
        for (Map.Entry<Integer, Report> entry : reports.entrySet())
            ReportListNames.add(entry.getKey());
        return ReportListNames;

    }

    public void removeReport(int id) {
        if (!reports.containsKey(id))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            reports.remove(id);
        }
    }

    public Report getReportForProduct(int id) {
        if (!reports.containsKey(id))
            throw new IllegalArgumentException("Report id doesn't exists");
        else {
            return reports.get(id);
        }
    }

    //CREATIONS

    //Product report
    public Report createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) throws Exception {
        List<Product> byCategoryPro = product_controller.getItemsByCategory(CatName, subCatName, subSubCatName);
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.byCategory.ordinal(),
                "by category");
        return getReportForProduct(byCategoryPro, r);
    }

    //ProductItem report
    public Report createByProductReport(String name, String report_producer, String proName) throws Exception {
        List<ProductItem> byProductPro = product_controller.getItemsByProduct(proName);
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.byProduct.ordinal(),
                "by product");
        return getReportForProductItem(byProductPro, r);
    }

    //ProductItem report
    public Report createBySupplierReport(String name, String report_producer, int suppName) throws Exception {
        List<ProductItem> bySupplierPro = product_controller.getItemsBySupplier(suppName);
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.bySupplier.ordinal(),
                "by supplier");
        return getReportForProductItem(bySupplierPro, r);
    }

    //ProductItem report
    public Report createDefectiveReport(String name, String report_producer) throws Exception {
        List<ProductItem> DefectivePro = product_controller.getDefectiveItems();
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.Defective.ordinal(),
                "defective");
        return getReportForProductItem(DefectivePro, r);
    }

    //ProductItem report
    public Report createExpiredReport(String name, String report_producer) throws Exception {
        List<ProductItem> ExpiredPro = product_controller.getExpiredItems();
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.Expired.ordinal(),
                "expired");
        return getReportForProductItem(ExpiredPro, r);
    }

    //Product report
    public Report createMissingReport(String name, String report_producer) throws Exception {
        List<Product> missingPro = product_controller.getMissingProducts();
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.Missing.ordinal(),
                "missing");
        return getReportForProduct(missingPro, r);
    }

    //Product report
    public Report createSurplusesReport(String name, String report_producer) throws Exception {
        List<Product> SurplusesPro = product_controller.getSurplusProducts();
        ResponseT<ReportData> r = pc.getReports().create(
                report_ids,
                report_producer,
                name,
                java.sql.Date.valueOf(LocalDate.now()),
                Report.report_type.Surpluses.ordinal(),
                "surpluses");
        return getReportForProduct(SurplusesPro, r);
    }

    //PRIVATE METHODS
    private Report getReportForProduct(List<Product> product_list, ResponseT<ReportData> r) throws Exception {
        if (!r.success)
            throw new Exception(r.error);
        Report report = new Report(r.data);
        for (Product p : product_list) {
            ResponseT<ProductInReportData> pir = pc.getProductsInReports().create(report_ids, p.getProduct_id());
            if (!pir.success)
                throw new Exception(r.error);
            report.addProduct(p);
        }
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    private Report getReportForProductItem(List<ProductItem> expiredPro, ResponseT<ReportData> r) throws Exception {
        if (!r.success)
            throw new Exception(r.error);
        Report report = new Report(r.data);
        for (ProductItem pi : expiredPro) {
            ResponseT<ProductItemInReportData> pir = pc.getProductItemsInReports().create(report_ids, pi.getProduct_id(), pi.getId());
            if (!pir.success)
                throw new Exception(r.error);
            report.addProductItem(pi);
        }
        reports.put(report_ids, report);
        report_ids++;
        return report;
    }

    public void restart() {
        reports.clear();
    }
}
