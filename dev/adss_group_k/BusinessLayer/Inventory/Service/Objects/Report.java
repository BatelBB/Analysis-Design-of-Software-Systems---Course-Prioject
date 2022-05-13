package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.Date;
import java.util.List;

import static adss_group_k.BusinessLayer.Inventory.Report.report_type.*;

public class Report {

    Integer id;
    String name;
    Date date;
    String report_producer;
    adss_group_k.BusinessLayer.Inventory.Report.report_type reportType;
    List<ProductItem>productItemList;
    List<Product>productList;

    public Report(adss_group_k.BusinessLayer.Inventory.Report report) {
        id= report.getId();
        name=report.getName();
        date= report.getDate();
        report_producer= report.getReport_producer();
        reportType=report.getReportType();
        List<adss_group_k.BusinessLayer.Inventory.ProductItem> BusinessProductItemList=report.getProductItemList();
        for (adss_group_k.BusinessLayer.Inventory.ProductItem p:BusinessProductItemList) {
            productItemList.add(new adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem(p));
        }
        List<Product> BusinessProductList=report.getProductList();
        for (Product p:BusinessProductList) {
            productList.add(new Product(p));
        }
    }

    public String getName() { return name; }
    public String toString(){
        String s= "Id: "+id+"\n"+ "name: "+name+"\n"+ "date: "+date+"\n"+ "report_producer: "+report_producer+"\n";
        switch (reportType){
            case byCategory:
                return s + "The products in " + productList.get(0).getCat() + " category are:" + "\n"+ productList();
            case byProduct:
                return s + "The Items ids are" + productItemList.get(0).getProductName() + "product:" + "\n" + productItemList();
            case bySupplier:
                return s+ "The Items that "+ productItemList.get(0).getSupplier()+" is provides are:"+"\n" + productItemList();
            case Defective:
                return s+"The defective products are:"+"\n" + productItemList();
            case Surpluses:
                return s+ "The surpluses products items are:\n" + productList();
            case Expired:
                return s+ "The expired products items are:\n"+ productItemList();
            case Missing: {
                s = s + "The missing products items are:\n";
                for (Product p : productList) {
                    s = s + p.getName() + ", current Shelf_qty: " + p.getShelf_qty() + ", current Storage_qty: " + p.getStorage_qty() + "\n";
                }
                return s;
            }
        }
        return null;
    }
    private String productItemList(){
        String s="";
        for (ProductItem p:productItemList) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
    private String productList(){
        String s="";
        for (Product p : productList) {
            s = s + p.getName() + "\n";
        }
        return s;
    }
}
