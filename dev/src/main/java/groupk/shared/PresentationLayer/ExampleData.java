package groupk.shared.PresentationLayer;

import groupk.shared.business.Facade;
import groupk.shared.service.Inventory.Objects.Product;
import groupk.shared.service.Inventory.Objects.ProductItem;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.inventory_suppliers.dataLayer.dao.records.OrderType;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;
import groupk.shared.service.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ExampleData {
    public static void loadExampleData(Service service
                                       // TODO logistics & employees - add your stuff here
    ) {
        int ppn_office = service.createSupplier(1, 1, "OfficeLi",
                true, PaymentCondition.Credit, DayOfWeek.SUNDAY,
                "Ofir", "054-1234567", "Pencil 12, Pencilvannia").data.getPpn();
        int ppn_foods = service.createSupplier(2, 2, "FoodsAreUs",
                true, PaymentCondition.DirectDebit, DayOfWeek.THURSDAY,
                "Frank", "050-1234569", "Vegetable 12, Beersheba").data.getPpn();
        int ppn_lorem = service.createSupplier(3, 3, "Lorem Stuff",
                true, PaymentCondition.Credit, DayOfWeek.WEDNESDAY,
                "Lauren", "052-7654321", "Lorem 14, Ipsumia").data.getPpn();

        service.addCategory("office");
        service.addSubCategory("office", "writing");
        service.addSubSubCategory("office", "writing", "pens");
        service.addCategory("food");
        service.addSubCategory("food", "dairy");
        service.addSubSubCategory("food", "dairy", "10%");
        service.addSubSubCategory("food", "dairy", "5%");
        service.addSubCategory("food", "bread");
        service.addSubSubCategory("food", "bread", "rye");
        service.addSubSubCategory("food", "bread", "wheat");

        // Michael, fill some reasonable fields here
        Product pen = service.addProduct(
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
        Product cheaperPen = service.addProduct(
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
        Product milk5 = service.addProduct(
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
        Product milk10 = service.addProduct(
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

        ProductItem milk5_0 = service.addItem(
                milk5.getProduct_id(),
                "Yavne",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 29),
                true
        ).data;

        ProductItem milk5_1 = service.addItem(
                milk5.getProduct_id(),
                "Yehud",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 28),
                true
        ).data;

        ProductItem milk5_2 = service.addItem(
                milk5.getProduct_id(),
                "Yeruham",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 27),
                true
        ).data;

        ProductItem milk5_3 = service.addItem(
                milk5.getProduct_id(),
                "Yad Ha-Shmonah",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 26),
                true
        ).data;

        ProductItem milk5_4 = service.addItem(
                milk5.getProduct_id(),
                "Yad Binyamin",
                "shelf 1",
                2,
                LocalDate.of(2022, 12, 25),
                true
        ).data;

        service.updateProductCusDiscount(0.5f, LocalDate.of(2022, 3, 10), LocalDate.of(2022, 3, 20), milk5.getProduct_id());

        // Office items
        Item penItem = service.createItem(
                ppn_office,
                10,
                pen.getProduct_id(),
                11
        ).data;

        Item cheaperPenItem = service.createItem(
                ppn_lorem,
                10,
                cheaperPen.getProduct_id(),
                5
        ).data;

        // Food items
        Item milk5FromFoodsAreUs = service.createItem(
                ppn_foods,
                2,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromFoodsAreUs = service.createItem(
                ppn_foods,
                3,
                milk10.getProduct_id(),
                5
        ).data;
        Item milk5FromLorem = service.createItem(
                ppn_lorem,
                3,
                milk5.getProduct_id(),
                4
        ).data;
        Item milk10FromLorem = service.createItem(
                ppn_lorem,
                5,
                milk10.getProduct_id(),
                5.5f
        ).data;

        int[] discountAmounts = {10, 50, 100, 200};
        float[] discountPercents = {0.01f, 0.05f, 0.1f, 0.25f};

        for (int i = 0; i < discountAmounts.length; i++) {
            service.createDiscount(ppn_office, penItem.getCatalogNumber(),
                    discountAmounts[i], discountPercents[i]);
        }

        LocalDate date_jan_1 = LocalDate.of(2022, 1, 1);
        LocalDate date_jan_2 = LocalDate.of(2022, 1, 2);
        LocalDate date_feb_1 = LocalDate.of(2022, 2, 1);
        LocalDate date_feb_2 = LocalDate.of(2022, 2, 2);

        int order1 = service.createOrder(ppn_office, date_jan_1, date_jan_2, OrderType.Periodical).data.getId();
        int order2 = service.createOrder(ppn_foods, date_jan_1, date_jan_2, OrderType.Shortages).data.getId();
        int order3 = service.createOrder(ppn_lorem, date_jan_1, date_jan_2, OrderType.Shortages).data.getId();

        // order things
        service.orderItem(order1, ppn_office, penItem.getCatalogNumber(), 10);
        service.orderItem(order2, ppn_foods, milk5FromFoodsAreUs.getCatalogNumber(), 5);
        service.orderItem(order3, ppn_lorem, cheaperPenItem.getCatalogNumber(), 5);
        // fill other tables
        service.createByCategoryReport("my first report", "Naziff", "", "", "");
        service.createBySupplierReport("my second report", "Shariff", 2);

        // TODO employees, logistics, add your things here
    }
}
