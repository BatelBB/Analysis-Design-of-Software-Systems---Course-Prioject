package BusinessLayer;

import java.util.List;

public class MissingReport extends Report {

    private List<Product> missingPro;

    public MissingReport(String name, Integer id, String report_producer, List<Product> missingPro) {
        super(name,id,report_producer);
        this.missingPro=missingPro;
    }

    public List<Product> getMissingPro() { return missingPro; }

}
