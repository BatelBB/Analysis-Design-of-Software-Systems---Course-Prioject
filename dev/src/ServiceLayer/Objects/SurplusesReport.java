package ServiceLayer.Objects;

import BusinessLayer.Product;

import java.util.List;

public class SurplusesReport extends Report{

    private List<ServiceLayer.Objects.Product> SurplusesPro;

    public SurplusesReport(BusinessLayer.SurplusesReport report) {
        super(report);
        List<Product> BusinessSurplusesPro = report.getSurplusesPro();
        for (BusinessLayer.Product p:BusinessSurplusesPro) {
            SurplusesPro.add(new ServiceLayer.Objects.Product(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The surpluses products items are:\n";
        for (ServiceLayer.Objects.Product p:SurplusesPro) {
            s=s+p.getName()+", current Shelf_qty: "+p.getShelf_qty()+", current Storage_qty: "+p.getStorage_qty()+"\n";
        }
        return s;
    }
}
