package BusinessLayer;

import java.util.List;

public class byCategoryReport extends Report {

    private List<Product> byCategoryPro;
    private String catName;

    public byCategoryReport(String name, Integer id, String report_producer, List<Product> byCategoryPro, String catName) {
        super(name, id, report_producer);
        this.byCategoryPro=byCategoryPro;
        this.catName=catName;
    }

    public List<Product> getByCategoryPro() { return byCategoryPro; }
    public String getCatName() {return catName;}


}
