package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.ProductItem;

import java.util.List;

public class ExpiredReport extends Report{
    private List<adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem> ExpiredPro;

    public ExpiredReport(adss_group_k.BusinessLayer.Inventory.ExpiredReport report) {
        super(report);
        List<ProductItem> BusinessExpiredPro = report.getExpiredPro();
        for (ProductItem p:BusinessExpiredPro) {
            ExpiredPro.add(new adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem(p));
        }
    }
}
