package adss_group_k.dataLayer.records;

import adss_group_k.dataLayer.records.readonly.ProductInReportData;

public class ProductInReportRecord extends BaseRecord<Integer> implements ProductInReportData {
    private int product_id;
    private int report_id;

    public ProductInReportRecord(int product_id, int report_id) {
        this.product_id = product_id;
        this.report_id = report_id;
    }

    @Override
    public Integer key() {
        return report_id;
    }

    @Override
    public int getReportId() {
        return report_id;
    }

    @Override
    public int getProductId() {
        return product_id;
    }
}
