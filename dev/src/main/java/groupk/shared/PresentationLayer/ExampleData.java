package groupk.shared.PresentationLayer;

import groupk.shared.business.Facade;
import groupk.shared.service.Inventory.Objects.Product;
import groupk.shared.service.Inventory.Objects.ProductItem;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.inventory_suppliers.dataLayer.dao.records.OrderType;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ExampleData {
    public static void loadExampleData(Facade facade
                                       // TODO logistics & employees - add your stuff here
    ) {
        int ppn_office = facade.createSupplier(1, 1, "OfficeLi",
                true, PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "Ofir", "054-1234567", "Pencil 12, Pencilvannia").data.getPpn();
        int ppn_foods = facade.createSupplier(2, 2, "FoodsAreUs",
                true, PaymentCondition.DirectDebit, DayOfWeek.THURSDAY,
                "Frank", "050-1234569", "Vegetable 12, Beersheba").data.getPpn();
        int ppn_lorem = facade.createSupplier(3, 3, "Lorem Stuff",
                true, PaymentCondition.Credit, DayOfWeek.WEDNESDAY,
                "Lauren", "052-7654321", "Lorem 14, Ipsumia").data.getPpn();

        facade.addCategory("office");
        facade.addSubCategory("office", "writing");
        facade.addSubSubCategory("office", "writing", "pens");
        facade.addCategory("food");
        facade.addSubCategory("food", "dairy");
        facade.addSubSubCategory("food", "dairy", "10%");
        facade.addSubSubCategory("food", "dairy", "5%");
        facade.addSubCategory("food", "bread");
        facade.addSubSubCategory("food", "bread", "rye");
        facade.addSubSubCategory("food", "bread", "wheat");

        // Michael, fill some reasonable fields here
        Product pen = facade.addProduct(
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
        Product cheaperPen = facade.addProduct(
                "GelGrip Pen, 1.0mm, Blue",
                "Pilot",
                5,
                6,
                10,
                10, // Michael, fill some reasonable fields here
                "office",
                "writing",
                "pens"
        ).data;
        Product milk5 = facade.addProduct(
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
        Product milk10 = facade.addProduct(
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

        ProductItem milk5_0 = facade.addItem(
                milk5.getProduct_id(),
                "Yavne",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 29),
                true
        ).data;

        ProductItem milk5_1 = facade.addItem(
                milk5.getProduct_id(),
                "Yehud",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 28),
                true
        ).data;

        ProductItem milk5_2 = facade.addItem(
                milk5.getProduct_id(),
                "Yeruham",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 27),
                true
        ).data;

        ProductItem milk5_3 = facade.addItem(
                milk5.getProduct_id(),
                "Yad Ha-Shmonah",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 26),
                true
        ).data;

        ProductItem milk5_4 = facade.addItem(
                milk5.getProduct_id(),
                "Yad Binyamin",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 25),
                true
        ).data;

        facade.updateProductCusDiscount(0.5f, LocalDate.of(2022, 3, 10), LocalDate.of(2022, 3, 20), milk5.getProduct_id());

        // Office items
        Item penItem = facade.createItem(
                ppn_office,
                10,
                pen.getProduct_id(),
                11
        ).data;

        Item cheaperPenItem = facade.createItem(
                ppn_lorem,
                10,
                cheaperPen.getProduct_id(),
                5
        ).data;

        // Food items
        Item milk5FromFoodsAreUs = facade.createItem(
                ppn_foods,
                2,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromFoodsAreUs = facade.createItem(
                ppn_foods,
                3,
                milk10.getProduct_id(),
                5
        ).data;
        Item milk5FromLorem = facade.createItem(
                ppn_lorem,
                3,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromLorem = facade.createItem(
                ppn_lorem,
                5,
                milk10.getProduct_id(),
                5.5f
        ).data;

        int[] discountAmounts = {10, 50, 100, 200};
        float[] discountPercents = {0.01f, 0.05f, 0.1f, 0.25f};

        for (int i = 0; i < discountAmounts.length; i++) {
            facade.createDiscount(ppn_office, penItem.getCatalogNumber(),
                    discountAmounts[i], discountPercents[i]);
        }

        LocalDate date_jan_1 = LocalDate.of(2022, 1, 1);
        LocalDate date_jan_2 = LocalDate.of(2022, 1, 2);
        LocalDate date_feb_1 = LocalDate.of(2022, 2, 1);
        LocalDate date_feb_2 = LocalDate.of(2022, 2, 2);

        int order1 = facade.createOrder(ppn_office, date_jan_1, date_jan_2, OrderType.Periodical).data.getId();
        int order2 = facade.createOrder(ppn_foods, date_jan_1, date_jan_2, OrderType.Shortages).data.getId();
        int order3 = facade.createOrder(ppn_lorem, date_jan_1, date_jan_2, OrderType.Shortages).data.getId();

        // order things
        facade.orderItem(order1, ppn_office, penItem.getCatalogNumber(), 10);
        facade.orderItem(order2, ppn_foods, milk5FromFoodsAreUs.getCatalogNumber(), 5);
        facade.orderItem(order3, ppn_lorem, cheaperPenItem.getCatalogNumber(), 5);
        // fill other tables
        facade.createByCategoryReport("my first report", "Naziff", "", "", "");
        facade.createBySupplierReport("my second report", "Shariff", 2);

        // TODO employees, logistics, add your things here
    }
}
