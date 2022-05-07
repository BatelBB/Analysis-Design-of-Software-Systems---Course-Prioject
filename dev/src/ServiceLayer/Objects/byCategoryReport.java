package ServiceLayer.Objects;

import BusinessLayer.Product;

import java.util.List;

public class byCategoryReport extends Report{

    private List<ServiceLayer.Objects.Product> byCategoryPro;
    private String catName;

    public byCategoryReport(BusinessLayer.byCategoryReport report) {
        super(report);
        List<Product> BusinessByCategoryPro=report.getByCategoryPro();
        catName=report.getCatName();
        for (Product p:BusinessByCategoryPro) {
            byCategoryPro.add(new ServiceLayer.Objects.Product(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The products in "+ catName+" category are:"+"\n";
        for (ServiceLayer.Objects.Product p:byCategoryPro) {
            s=s+p.getName()+"\n";
        }
        return s;
    }
}
