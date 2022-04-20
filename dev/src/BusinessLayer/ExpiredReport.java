package BusinessLayer;

public class ExpiredReport extends Report {

    private String name;
    private Integer id;
    private String report_producer;
    private List<Product> ExpiredPro;

    public ExpiredReport(String name, Integer id, String report_producer, List<Product> ExpiredPro) {
        super(name,id,report_producer);
        this.ExpiredPro=ExpiredPro;
    }
}
