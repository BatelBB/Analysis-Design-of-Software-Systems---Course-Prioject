package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.List;

public class MissingReport extends Report {
    private List<Product> missingPro;


    public MissingReport(adss_group_k.BusinessLayer.Inventory.MissingReport report) {
        super(report);
        List<Product> BusinessMissingPro=report.getMissingPro();
        for (Product p:BusinessMissingPro) {
            missingPro.add(new Product(p));
        }
    }
}
