package BusinessLayer;

import java.util.List;

public class DefectiveReport extends Report {

    private String name;
    private Integer id;
    private String report_producer;
    private List<ProductItem> DefectivePro;

    public DefectiveReport(String name, Integer id, String report_producer, List<ProductItem> DefectivePro) {
        super(name, id, report_producer);
        this.DefectivePro = DefectivePro;
    }
}
