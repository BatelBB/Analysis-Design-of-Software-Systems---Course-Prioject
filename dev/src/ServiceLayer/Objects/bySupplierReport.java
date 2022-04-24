package ServiceLayer.Objects;

import java.util.List;

public class bySupplierReport extends Report{

    private List<ServiceLayer.Objects.ProductItem> bySupplierPro;

    public bySupplierReport(BusinessLayer.bySupplierReport report) {
        super(report);
        List<BusinessLayer.ProductItem> BusinessBySupplierPro=report.getBySupplierPro();
        for (BusinessLayer.ProductItem p:BusinessBySupplierPro) {
            bySupplierPro.add(new ServiceLayer.Objects.ProductItem(p));
        }

    }
}
