package adss_group_k.inventory.ServiceLayer.Objects;

import adss_group_k.inventory.BusinessLayer.ProductItem;

import java.util.List;

public class byProductReport extends Report{

    private List<adss_group_k.inventory.ServiceLayer.Objects.ProductItem> byProductPro;

    public byProductReport(adss_group_k.inventory.BusinessLayer.byProductReport report) {
        super(report);
        List<ProductItem> BusinessByProductPro=report.getByProductPro();
        for (ProductItem p:BusinessByProductPro) {
            byProductPro.add(new adss_group_k.inventory.ServiceLayer.Objects.ProductItem(p));
        }
    }
}
