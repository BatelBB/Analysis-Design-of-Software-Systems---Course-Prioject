package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.ProductItem;

import java.util.List;

public class byProductReport extends Report{

    private List<adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem> byProductPro;

    public byProductReport(adss_group_k.BusinessLayer.Inventory.byProductReport report) {
        super(report);
        List<ProductItem> BusinessByProductPro=report.getByProductPro();
        for (ProductItem p:BusinessByProductPro) {
            byProductPro.add(new adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem(p));
        }
    }
}
