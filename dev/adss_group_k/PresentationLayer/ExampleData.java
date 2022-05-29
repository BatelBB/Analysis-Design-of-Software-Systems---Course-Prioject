package adss_group_k.PresentationLayer;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem;
import adss_group_k.BusinessLayer.Suppliers.BussinessObject.Item;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.dataLayer.records.OrderType;
import adss_group_k.dataLayer.records.PaymentCondition;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ExampleData {
    public static void loadExampleData(Service inventory, SupplierService suppliers) {
        int ppn_office = suppliers.createSupplier(1, 1, "OfficeLi",
                true, PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "Ofir", "1-800-office", "ofir@office.li").data.getPpn();
        int ppn_foods = suppliers.createSupplier(2, 2, "FoodsAreUs",
                true, PaymentCondition.DirectDebit, DayOfWeek.THURSDAY,
                "Frank", "1-800-food", "frank@foods.ru").data.getPpn();
        int ppn_lorem = suppliers.createSupplier(3, 3, "Lorem Stuff",
                true, PaymentCondition.Credit, DayOfWeek.WEDNESDAY,
                "Lauren", "1-800-Lorem", "lauren@lorem.li").data.getPpn();

        inventory.addCategory("office");
        inventory.addSubCategory("office", "writing");
        inventory.addSubSubCategory("office", "writing", "pens");
        inventory.addCategory("food");
        inventory.addSubCategory("food", "dairy");
        inventory.addSubSubCategory("food", "dairy", "10%");
        inventory.addSubSubCategory("food", "dairy", "5%");
        inventory.addSubCategory("food", "bread");
        inventory.addSubSubCategory("food", "bread", "rye");
        inventory.addSubSubCategory("food", "bread", "wheat");

        // Michael, fill some reasonable fields here
        Product pen = inventory.addProduct(
                "GelGrip Pen, 1.0mm, Blue",
                "Pilot",
                10,
                12,
                10,
                10, // Michael, fill some reasonable fields here
                "office",
                "writing",
                "pens"
        ).data;
        Product milk5 = inventory.addProduct(
                "Mister milk",
                "Mister Milker",
                30,
                40,
                3,
                5,
                "food",
                "dairy",
                "5%"
        ).data; // same
        Product milk10 = inventory.addProduct(
                "Miss milk",
                "Miss Milker",
                30,
                40,
                3,
                5,
                "food",
                "dairy",
                "10%"
        ).data; // same

        ProductItem milk5_0 = inventory.addItem(
                milk5.getProduct_id(),
                "Yavne",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 29),
                true
        ).data;

        ProductItem milk5_1 = inventory.addItem(
                milk5.getProduct_id(),
                "Yehud",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 28),
                true
        ).data;

        ProductItem milk5_2 = inventory.addItem(
                milk5.getProduct_id(),
                "Yeruham",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 27),
                true
        ).data;

        ProductItem milk5_3 = inventory.addItem(
                milk5.getProduct_id(),
                "Yad Ha-Shmonah",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 26),
                true
        ).data;

        ProductItem milk5_4 = inventory.addItem(
                milk5.getProduct_id(),
                "Yad Binyamin",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 25),
                true
        ).data;

        inventory.updateProductCusDiscount(0.5f, LocalDate.of(2022, 3, 10), LocalDate.of(2022, 3, 20), milk5.getProduct_id());

        // Office items
        Item penItem = suppliers.createItem(
                ppn_office,
                10,
                pen.getProduct_id(),
                11
        ).data;

        // Food items
        Item milk5FromFoodsAreUs = suppliers.createItem(
                ppn_foods,
                2,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromFoodsAreUs = suppliers.createItem(
                ppn_foods,
                3,
                milk10.getProduct_id(),
                5
        ).data;
        Item milk5FromLorem = suppliers.createItem(
                ppn_lorem,
                3,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromLorem = suppliers.createItem(
                ppn_lorem,
                5,
                milk10.getProduct_id(),
                5.5f
        ).data;

        int[] discountAmounts = {10, 50, 100, 200};
        float[] discountPercents = {0.01f, 0.05f, 0.1f, 0.25f};

        for (int i = 0; i < discountAmounts.length; i++) {
            suppliers.createDiscount(ppn_office, penItem.getCatalogNumber(),
                    discountAmounts[i], discountPercents[i]);
        }

        LocalDate date_jan_1 = LocalDate.of(2022, 1, 1);
        LocalDate date_jan_2 = LocalDate.of(2022, 1, 2);
        LocalDate date_feb_1 = LocalDate.of(2022, 2, 1);
        LocalDate date_feb_2 = LocalDate.of(2022, 2, 2);

        int order1 = suppliers.createOrder(ppn_office, date_jan_1, date_jan_2, OrderType.Periodical).data.getId();
        int order2 = suppliers.createOrder(ppn_foods, date_jan_1, date_jan_2, OrderType.Shortages).data.getId();

        // order things
        suppliers.orderItem(order1, ppn_office, penItem.getCatalogNumber(), 10);
        suppliers.orderItem(order2, ppn_foods, milk5FromFoodsAreUs.getCatalogNumber(), 5);
        // fill other tables
        inventory.createByCategoryReport("my first report", "Naziff", "", "", "");
        inventory.createBySupplierReport("my second report", "Shariff", 2);
    }
}
