package adss_group_k.dataLayer.records.readonly;

import java.sql.Date;

/**
 * "ProductId" INTEGER NOT NULL,
 * "ProductItemId" INTEGER NOT NULL,
 * "DiscountPairId" INTEGER NOT NULL,
 * "Discount" FLOAT NOT NULL,
 * "StartDate" DateTime NOT NULL,
 * "EndDate" DateTime NOT NULL,
 * "PRIMARY KEY("DiscountPairId","ProductItemId","ProductId")
 **/
public interface DiscountPairData {
    int getProductId();
    int getProductItemId();
    int getDiscountPairId();
    float getDiscount();
    Date getStartDate();
    Date getEndDate();
}
