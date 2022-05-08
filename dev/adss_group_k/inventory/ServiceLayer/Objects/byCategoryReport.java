package adss_group_k.inventory.ServiceLayer.Objects;

import adss_group_k.inventory.BusinessLayer.Product;

import java.util.List;

public class byCategoryReport extends Report{

    private List<adss_group_k.inventory.ServiceLayer.Objects.Product> byCategoryPro;

    public byCategoryReport(adss_group_k.inventory.BusinessLayer.byCategoryReport report) {
        super(report);
        List<Product> BusinessByCategoryPro=report.getByCategoryPro();
        for (Product p:BusinessByCategoryPro) {
            byCategoryPro.add(new adss_group_k.inventory.ServiceLayer.Objects.Product(p));
        }
    }
}
