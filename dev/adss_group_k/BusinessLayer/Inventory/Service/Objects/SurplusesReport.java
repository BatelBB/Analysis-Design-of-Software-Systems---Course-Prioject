package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.List;

public class SurplusesReport extends Report{

    private List<Product> SurplusesPro;

    public SurplusesReport(adss_group_k.BusinessLayer.Inventory.SurplusesReport report) {
        super(report);
        List<Product> BusinessSurplusesPro = report.getSurplusesPro();
        for (Product p:BusinessSurplusesPro) {
            SurplusesPro.add(new Product(p));
        }
    }
}
