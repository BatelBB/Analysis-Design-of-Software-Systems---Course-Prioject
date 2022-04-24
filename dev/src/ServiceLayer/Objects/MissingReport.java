package ServiceLayer.Objects;

import BusinessLayer.Report;

import java.util.List;

public class MissingReport extends ServiceLayer.Objects.Report {
    private List<Product> missingPro;


    public MissingReport(BusinessLayer.MissingReport report) {
        super(report);
        List<BusinessLayer.Product> BusinessMissingPro=report.getMissingPro();
        for (BusinessLayer.Product p:BusinessMissingPro) {
            missingPro.add(new Product(p));
        }
    }
}
