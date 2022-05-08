package ServiceLayer.Objects;

import BusinessLayer.Product;

import java.util.List;

public class SurplusesReport extends Report{

    private List<ServiceLayer.Objects.Product> SurplusesPro;

    public SurplusesReport(BusinessLayer.SurplusesReport report) {
        super(report);
        List<Product> BusinessSurplusesPro = report.getSurplusesPro();
        for (BusinessLayer.Product p:BusinessSurplusesPro) {
            SurplusesPro.add(new ServiceLayer.Objects.Product(p));
        }
    }
}
