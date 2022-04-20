package BusinessLayer;

public class DefectiveReport extends Report {

    private String name;
    private Integer id;
    private String report_producer;
    private List<Product> DefectivePro;

    public DefectiveReport(String name, Integer id, String report_producer, List<Product> DefectivePro) {
        super(name,id,report_producer);
        this.DefectivePro=DefectivePro;
    }
}
