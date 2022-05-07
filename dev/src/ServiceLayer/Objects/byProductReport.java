package ServiceLayer.Objects;

import BusinessLayer.ProductItem;

import java.util.List;

public class byProductReport extends Report{

    private List<ServiceLayer.Objects.ProductItem> byProductPro;
    String proName;

    public byProductReport(BusinessLayer.byProductReport report) {
        super(report);
        List<ProductItem> BusinessByProductPro=report.getByProductPro();
        proName=report.getProName();
        for (ProductItem p:BusinessByProductPro) {
            byProductPro.add(new ServiceLayer.Objects.ProductItem(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The Items ids are" +proName+ "product:"+"\n";
        for (ServiceLayer.Objects.ProductItem p:byProductPro) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
