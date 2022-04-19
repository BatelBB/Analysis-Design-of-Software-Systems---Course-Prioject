public class bySupplierReport extends Report{

    private String name;
    private Integer id;
    private String report_producer;
    private List<Product> bySupplierPro;

    public bySupplierReport(String name, Integer id, String report_producer, List<Product> bySupplierPro) {
        super(name, id, report_producer);
        this.bySupplierPro=bySupplierPro;
    }
}
