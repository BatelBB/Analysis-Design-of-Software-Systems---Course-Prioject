package adss_group_k.inventory.BusinessLayer;

import java.util.List;

public class ExpiredReport extends Report {

    private List<ProductItem> ExpiredPro;

    public ExpiredReport(String name,String report_producer, List<ProductItem> ExpiredPro) {
        super(name, report_producer);
        this.ExpiredPro = ExpiredPro;
    }

    public List<ProductItem> getExpiredPro() { return ExpiredPro; }
    public String toString(){
        String s= super.toString()+ "The expired products items are:\n";
        for (ProductItem p:ExpiredPro) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
