package BusinessLayer;

public class byProductReport extends Report {

    private String name;
    private Integer id;
    private String report_producer;
    private List<ProductItem> byProductPro;

    public byProductReport(String name, Integer id, String report_producer, List<ProductItem> byProductPro) {
        super(name, id, report_producer);
        this.byProductPro=byProductPro;
    }
}
