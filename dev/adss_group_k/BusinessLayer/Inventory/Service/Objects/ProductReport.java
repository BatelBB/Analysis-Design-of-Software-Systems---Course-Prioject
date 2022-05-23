package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.List;

public class ProductReport extends Report{
    List<Product> productList;

    public ProductReport(adss_group_k.BusinessLayer.Inventory.Report report) {
        super(report);
        List<Product> BusinessProductList=report.getProductList();
        for (Product p:BusinessProductList) {
            productList.add(new Product(p));
        }
    }

    public String toString(){
        String s= "Id: "+id+"\n"+ "name: "+name+"\n"+ "date: "+date+"\n"+ "report_producer: "+report_producer+"\n";
        switch (reportType){
            case byCategory:
                return s + "The products in " + productList.get(0).getCat() + " category are:" + "\n"+ productList();
            case Surpluses:
                return s+ "The surpluses products items are:\n" + productList();
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
    private String productList(){
        String s="";
        for (Product p : productList) {
            s = s + p.getName() + "\n";
        }
        return s;
    }

}
