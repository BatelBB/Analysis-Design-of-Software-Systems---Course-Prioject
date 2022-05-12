package adss_group_k.inventory.ServiceLayer.Objects;

import java.util.List;

public class MissingReport extends Report {
    private List<Product> missingPro;



    public MissingReport(adss_group_k.inventory.BusinessLayer.MissingReport report) {
        super(report);
        List<adss_group_k.inventory.BusinessLayer.Product> BusinessMissingPro=report.getMissingPro();
        for (adss_group_k.inventory.BusinessLayer.Product p:BusinessMissingPro) {
            missingPro.add(new Product(p));
        }
    }

    public List<Product> getMissingPro() {return missingPro;}
}
