package adss_group_k.inventory.ServiceLayer.Objects;

import adss_group_k.inventory.BusinessLayer.Product;

import java.util.List;

public class SurplusesReport extends Report{

    private List<adss_group_k.inventory.ServiceLayer.Objects.Product> SurplusesPro;

    public SurplusesReport(adss_group_k.inventory.BusinessLayer.SurplusesReport report) {
        super(report);
        List<Product> BusinessSurplusesPro = report.getSurplusesPro();
        for (Product p:BusinessSurplusesPro) {
            SurplusesPro.add(new adss_group_k.inventory.ServiceLayer.Objects.Product(p));
        }
    }
}
