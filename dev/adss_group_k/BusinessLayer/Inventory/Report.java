package adss_group_k.BusinessLayer.Inventory;

import java.util.Date;
import java.util.List;

public class Report {

    public enum report_type{Missing,Expired,Surpluses,Defective,bySupplier,byProduct,byCategory}

    protected Integer id;
    protected String name;
    protected Date date;
    protected String report_producer;
    protected report_type reportType;
    List<Product> productList;
    List<ProductItem> productItemList;

    public Report(String name, Integer id,report_type reportType, String report_producer,List<Product> productList,List<ProductItem> productItemList) {
        this.name=name;
        this.id=id;
        date=new Date();
        this.reportType=reportType;
        this.report_producer=report_producer;
        this.productList=productList;
        this.productItemList= productItemList;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public Date getDate() { return date; }

    public List<Product> getProductList() {return productList;}

    public List<ProductItem> getProductItemList() {return productItemList;}

    public report_type getReportType() {return reportType;}

    public String getReport_producer() { return report_producer; }

}
