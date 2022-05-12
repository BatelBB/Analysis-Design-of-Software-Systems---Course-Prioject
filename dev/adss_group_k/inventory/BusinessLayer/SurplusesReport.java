package adss_group_k.inventory.BusinessLayer;

import java.util.List;

public class SurplusesReport extends Report {

    private List<Product> SurplusesPro;

    public SurplusesReport(String name, String report_producer, List<Product> SurplusesPro) {
        super(name, report_producer);
        this.SurplusesPro = SurplusesPro;
    }

    public List<Product> getSurplusesPro() {return SurplusesPro; }
    public String toString(){
        String s= super.toString()+ "The surpluses products items are:\n";
        for (Product p:SurplusesPro) {
            s=s+p.getName()+", current Shelf_qty: "+p.getShelf_qty()+", current Storage_qty: "+p.getStorage_qty()+"\n";
        }
        return s;
    }

}
