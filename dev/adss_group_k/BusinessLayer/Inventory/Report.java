package adss_group_k.BusinessLayer.Inventory;

import adss_group_k.dataLayer.records.ReportRecord;
import adss_group_k.dataLayer.records.readonly.ReportData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {

    public enum report_type {
        byCategory,
        byProduct,
        bySupplier,
        Defective,
        Expired,
        Missing,
        Surpluses,
    }

    private Integer id;
    private String name;
    private LocalDate date;
    private String report_producer;
    private report_type reportType;
    private List<Product> productList;
    private List<ProductItem> productItemList;

    public Report(String name, Integer id, report_type reportType, String report_producer, List<Product> productList, List<ProductItem> productItemList) {
        this.name = name;
        this.id = id;
        date = LocalDate.now();
        this.reportType = reportType;
        this.report_producer = report_producer;
        this.productList = productList;
        this.productItemList = productItemList;
    }

    public Report(ReportData report) {
        this.name = report.getName();
        this.id = report.getId();
        this.date = report.getDate().toLocalDate();
        this.reportType = report_type.values()[report.getType()];
        this.report_producer = report.getReportProducer();
        this.productList = new ArrayList<>();
        this.productItemList = new ArrayList<>();
    }

    public void addProduct(Product p) {
        productList.add(p);
    }

    public void addProductItem(ProductItem pi) {
        productItemList.add(pi);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<ProductItem> getProductItemList() {
        return productItemList;
    }

    public report_type getReportType() {
        return reportType;
    }

    public String getReport_producer() {
        return report_producer;
    }

}
