package BusinessLayer;

import BusinessLayer.Report;

import java.util.List;

public class SurplusesReport extends Report {

    private List<Product> SurplusesPro;

    public SurplusesReport(String name, Integer id, String report_producer, List<Product> SurplusesPro) {
        super(name,id,report_producer);
        this.SurplusesPro=SurplusesPro;
    }

    public List<Product> getSurplusesPro() {return SurplusesPro; }

}
