package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.Product;

import java.util.List;

public class byCategoryReport extends Report{

    private List<adss_group_k.BusinessLayer.Inventory.Service.Objects.Product> byCategoryPro;

    public byCategoryReport(adss_group_k.BusinessLayer.Inventory.byCategoryReport report) {
        super(report);
        List<Product> BusinessByCategoryPro=report.getByCategoryPro();
        for (Product p:BusinessByCategoryPro) {
            byCategoryPro.add(new adss_group_k.BusinessLayer.Inventory.Service.Objects.Product(p));
        }
    }
}
