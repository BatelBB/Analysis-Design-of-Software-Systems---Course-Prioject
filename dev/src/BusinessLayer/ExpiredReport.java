package BusinessLayer;

import java.util.List;

public class ExpiredReport extends Report {

    private List<ProductItem> ExpiredPro;

    public ExpiredReport(String name, Integer id, String report_producer, List<ProductItem> ExpiredPro) {
        super(name, id, report_producer);
        this.ExpiredPro = ExpiredPro;
    }

    public List<ProductItem> getExpiredPro() { return ExpiredPro; }

}
