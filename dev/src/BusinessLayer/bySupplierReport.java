package BusinessLayer;

import java.util.List;

public class bySupplierReport extends Report {

    private List<Product> bySupplierPro;

    public bySupplierReport(String name, Integer id, String report_producer, List<ProductItem> bySupplierPro) {
        super(name, id, report_producer);
        this.bySupplierPro = bySupplierPro;
    }

    public List<Product> getBySupplierPro() { return bySupplierPro; }
}
