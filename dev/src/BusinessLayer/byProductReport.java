package BusinessLayer;

import java.util.List;

public class byProductReport extends Report {

    private List<ProductItem> byProductPro;

    public byProductReport(String name, Integer id, String report_producer, List<ProductItem> byProductPro) {
        super(name, id, report_producer);
        this.byProductPro=byProductPro;
    }

    public List<ProductItem> getByProductPro() { return byProductPro; }
}
