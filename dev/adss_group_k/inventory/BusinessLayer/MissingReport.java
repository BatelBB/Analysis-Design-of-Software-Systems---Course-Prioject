package adss_group_k.inventory.BusinessLayer;

import java.util.List;

public class MissingReport extends Report {

    private List<Product> missingPro;

    public MissingReport(String name, String report_producer, List<Product> missingPro) {
        super(name,report_producer);
        this.missingPro=missingPro;
    }

    public List<Product> getMissingPro() { return missingPro; }
    public String toString(){
        String s= super.toString()+ "The missing products items are:\n";
        for (Product p:missingPro) {
            s=s+p.getName()+", current Shelf_qty: "+p.getShelf_qty()+", current Storage_qty: "+p.getStorage_qty()+"\n";
        }
        return s;
    }
}
