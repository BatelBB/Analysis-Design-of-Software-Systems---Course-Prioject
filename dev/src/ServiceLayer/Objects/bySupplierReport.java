package ServiceLayer.Objects;

import java.util.List;

public class bySupplierReport extends Report{

    private List<ServiceLayer.Objects.Product> bySupplierPro;

    public bySupplierReport(BusinessLayer.bySupplierReport report) {
        super(report);
        List<BusinessLayer.Product> BusinessBySupplierPro=report.getBySupplierPro();
        for (BusinessLayer.Product p:BusinessBySupplierPro) {
            bySupplierPro.add(new ServiceLayer.Objects.Product(p));
        }

    }
}
