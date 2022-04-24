package ServiceLayer.Objects;

import BusinessLayer.ProductItem;

import java.util.List;

public class byProductReport extends Report{

    private List<ServiceLayer.Objects.ProductItem> byProductPro;

    public byProductReport(BusinessLayer.byProductReport report) {
        super(report);
        List<ProductItem> BusinessByProductPro=report.getByProductPro();
        for (ProductItem p:BusinessByProductPro) {
            byProductPro.add(new ServiceLayer.Objects.ProductItem(p));
        }
    }
}
