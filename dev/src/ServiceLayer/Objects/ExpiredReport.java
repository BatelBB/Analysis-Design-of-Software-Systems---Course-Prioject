package ServiceLayer.Objects;

import BusinessLayer.MissingReport;
import BusinessLayer.Product;

import java.util.List;

public class ExpiredReport extends Report{
    private List<ServiceLayer.Objects.Product> ExpiredPro;

    public ExpiredReport(BusinessLayer.ExpiredReport report) {
        super(report);
        List<Product> BusinessExpiredPro = report.getExpiredPro();
        for (BusinessLayer.Product p:BusinessExpiredPro) {
            ExpiredPro.add(new ServiceLayer.Objects.Product(p));
        }
    }
}
