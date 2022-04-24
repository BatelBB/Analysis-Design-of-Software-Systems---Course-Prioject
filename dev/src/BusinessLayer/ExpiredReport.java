package BusinessLayer;

import java.util.List;

public class ExpiredReport extends Report {

    private List<Product> ExpiredPro;

    public ExpiredReport(String name, Integer id, String report_producer, List<Product> ExpiredPro) {
        super(name,id,report_producer);
        this.ExpiredPro=ExpiredPro;
    }

    public List<Product> getExpiredPro() { return ExpiredPro; }
}
