package ServiceLayer.Objects;

import java.util.List;

public class DefectiveReport extends Report{

    private List<Product> DefectivePro;

    public DefectiveReport(BusinessLayer.DefectiveReport report) {
        super(report);
        List<BusinessLayer.Product> BusinessDefectivePro=report.getDefectivePro();
        for (BusinessLayer.Product p:BusinessDefectivePro) {
            DefectivePro.add(new Product(p));
        }
    }
}
