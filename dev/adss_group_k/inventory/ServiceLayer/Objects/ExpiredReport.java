package adss_group_k.inventory.ServiceLayer.Objects;

import adss_group_k.inventory.BusinessLayer.ProductItem;

import java.util.List;

public class ExpiredReport extends Report{
    private List<adss_group_k.inventory.ServiceLayer.Objects.ProductItem> ExpiredPro;

    public ExpiredReport(adss_group_k.inventory.BusinessLayer.ExpiredReport report) {
        super(report);
        List<ProductItem> BusinessExpiredPro = report.getExpiredPro();
        for (ProductItem p:BusinessExpiredPro) {
            ExpiredPro.add(new adss_group_k.inventory.ServiceLayer.Objects.ProductItem(p));
        }
    }
}
