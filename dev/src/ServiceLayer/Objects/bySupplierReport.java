package ServiceLayer.Objects;

import java.util.List;

public class bySupplierReport extends Report{

    private List<ServiceLayer.Objects.ProductItem> bySupplierPro;
    String suppName;

    public bySupplierReport(BusinessLayer.bySupplierReport report) {
        super(report);
        List<BusinessLayer.ProductItem> BusinessBySupplierPro=report.getBySupplierPro();
        suppName=report.getSuppName();
        for (BusinessLayer.ProductItem p:BusinessBySupplierPro) {
            bySupplierPro.add(new ServiceLayer.Objects.ProductItem(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The Items that "+ suppName+" is provides are:"+"\n";
        for (ProductItem p:bySupplierPro) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
