package adss_group_k.inventory.ServiceLayer.Objects;

import java.util.List;

public class bySupplierReport extends Report{

    private List<ProductItem> bySupplierPro;

    public bySupplierReport(adss_group_k.inventory.BusinessLayer.bySupplierReport report) {
        super(report);
        List<adss_group_k.inventory.BusinessLayer.ProductItem> BusinessBySupplierPro=report.getBySupplierPro();
        for (adss_group_k.inventory.BusinessLayer.ProductItem p:BusinessBySupplierPro) {
            bySupplierPro.add(new ProductItem(p));
        }

    }
}
