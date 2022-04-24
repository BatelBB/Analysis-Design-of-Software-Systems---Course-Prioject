package BusinessLayer;

import java.util.List;

public class DefectiveReport extends Report {

    private List<ProductItem> DefectivePro;

    public DefectiveReport(String name, Integer id, String report_producer, List<ProductItem> DefectivePro) {
        super(name, id, report_producer);
        this.DefectivePro = DefectivePro;
    }

    public List<ProductItem> getDefectivePro() { return DefectivePro; }
}
