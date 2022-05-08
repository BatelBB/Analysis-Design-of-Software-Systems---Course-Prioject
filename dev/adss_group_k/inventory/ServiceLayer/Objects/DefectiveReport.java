package adss_group_k.inventory.ServiceLayer.Objects;

import java.util.List;

public class DefectiveReport extends Report{

    private List<ProductItem> DefectivePro;

    public DefectiveReport(adss_group_k.inventory.BusinessLayer.DefectiveReport report) {
        super(report);
        List<adss_group_k.inventory.BusinessLayer.ProductItem> BusinessDefectivePro=report.getDefectivePro();
        for (adss_group_k.inventory.BusinessLayer.ProductItem p:BusinessDefectivePro) {
            DefectivePro.add(new ProductItem(p));
        }
    }
}
