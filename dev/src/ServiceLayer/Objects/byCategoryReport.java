package ServiceLayer.Objects;

import BusinessLayer.Product;

import java.util.List;

public class byCategoryReport extends Report{

    private List<ServiceLayer.Objects.Product> byCategoryPro;

    public byCategoryReport(BusinessLayer.byCategoryReport report) {
        super(report);
        List<Product> BusinessByCategoryPro=report.getByCategoryPro();
        for (Product p:BusinessByCategoryPro) {
            byCategoryPro.add(new ServiceLayer.Objects.Product(p));
        }
    }
}
