package ServiceLayer.Objects;

import BusinessLayer.MissingReport;
import BusinessLayer.Product;
import BusinessLayer.ProductItem;

import java.util.List;

public class ExpiredReport extends Report{
    private List<ServiceLayer.Objects.ProductItem> ExpiredPro;

    public ExpiredReport(BusinessLayer.ExpiredReport report) {
        super(report);
        List<ProductItem> BusinessExpiredPro = report.getExpiredPro();
        for (BusinessLayer.ProductItem p:BusinessExpiredPro) {
            ExpiredPro.add(new ServiceLayer.Objects.ProductItem(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The expired products items are:\n";
        for (ServiceLayer.Objects.ProductItem p:ExpiredPro) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
