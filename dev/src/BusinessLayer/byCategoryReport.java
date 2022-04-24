package BusinessLayer;

import java.util.List;

public class byCategoryReport extends Report {

    private List<Product> byCategoryPro;

    public byCategoryReport(String name, Integer id, String report_producer, List<Product> byCategoryPro) {
        super(name, id, report_producer);
        this.byCategoryPro=byCategoryPro;
    }

    public List<Product> getByCategoryPro() { return byCategoryPro; }
}
