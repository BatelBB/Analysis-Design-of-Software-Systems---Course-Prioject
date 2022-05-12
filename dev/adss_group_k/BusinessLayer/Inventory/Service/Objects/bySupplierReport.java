package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import java.util.List;

public class bySupplierReport extends Report{

    private List<ProductItem> bySupplierPro;

    public bySupplierReport(adss_group_k.BusinessLayer.Inventory.bySupplierReport report) {
        super(report);
        List<adss_group_k.BusinessLayer.Inventory.ProductItem> BusinessBySupplierPro=report.getBySupplierPro();
        for (adss_group_k.BusinessLayer.Inventory.ProductItem p:BusinessBySupplierPro) {
            bySupplierPro.add(new ProductItem(p));
        }

    }
}
