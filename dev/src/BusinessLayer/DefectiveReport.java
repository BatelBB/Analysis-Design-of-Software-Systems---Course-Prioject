package BusinessLayer;

import java.util.List;

public class DefectiveReport extends Report {

    private List<Product> DefectivePro;

    public DefectiveReport(String name, Integer id, String report_producer, List<Product> DefectivePro) {
        super(name,id,report_producer);
        this.DefectivePro=DefectivePro;
    }

    public List<Product> getDefectivePro() { return DefectivePro; }
}
