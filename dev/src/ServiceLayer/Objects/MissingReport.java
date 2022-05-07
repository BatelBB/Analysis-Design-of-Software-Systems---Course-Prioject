package ServiceLayer.Objects;

import BusinessLayer.Report;

import java.util.List;

public class MissingReport extends ServiceLayer.Objects.Report {
    private List<Product> missingPro;


    public MissingReport(BusinessLayer.MissingReport report) {
        super(report);
        List<BusinessLayer.Product> BusinessMissingPro=report.getMissingPro();
        for (BusinessLayer.Product p:BusinessMissingPro) {
            missingPro.add(new Product(p));
        }
    }
    public String toString(){
        String s= super.toString()+ "The missing products items are:\n";
        for (Product p:missingPro) {
            s=s+p.getName()+", current Shelf_qty: "+p.getShelf_qty()+", current Storage_qty: "+p.getStorage_qty()+"\n";
        }
        return s;
    }
}
