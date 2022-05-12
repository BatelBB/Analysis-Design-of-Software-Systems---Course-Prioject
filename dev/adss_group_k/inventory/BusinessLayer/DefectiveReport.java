package adss_group_k.inventory.BusinessLayer;

import java.util.List;

public class DefectiveReport extends Report {

    private List<ProductItem> DefectivePro;

    public DefectiveReport(String name,String report_producer, List<ProductItem> DefectivePro) {
        super(name, report_producer);
        this.DefectivePro = DefectivePro;
    }

    public List<ProductItem> getDefectivePro() { return DefectivePro; }
    public String toString(){
        String s= super.toString()+ "The defective products are:"+"\n";
        for (ProductItem p:DefectivePro) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
