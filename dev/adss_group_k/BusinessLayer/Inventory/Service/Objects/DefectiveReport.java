package adss_group_k.BusinessLayer.Inventory.Service.Objects;

import adss_group_k.BusinessLayer.Inventory.ProductItem;

import java.util.List;

public class DefectiveReport extends Report{

    private List<ProductItem> DefectivePro;

    public DefectiveReport(adss_group_k.BusinessLayer.Inventory.DefectiveReport report) {
        super(report);
        List<ProductItem> BusinessDefectivePro=report.getDefectivePro();
        for (ProductItem p:BusinessDefectivePro) {
            DefectivePro.add(new ProductItem(p));
        }
    }
}
