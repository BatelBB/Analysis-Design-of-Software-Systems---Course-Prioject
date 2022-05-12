package adss_group_k.BusinessLayer.Suppliers.Service;

import adss_group_k.BusinessLayer.Suppliers.Entity.MutableContact;
import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Item;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Order;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Supplier;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ExampleSeed {
    public static void seedDatabase(SupplierService service) {
        createSupplierOfficeStuff(service);
        createSupplierGoodFoods(service);
        createSupplierMysteryItems(service);
    }

    private static void createSupplierMysteryItems(SupplierService service) {
        Supplier supplier = service.createSupplier(666, 666666, "Mystery Items",
                false, PaymentCondition.Credit,
                null,
                new MutableContact("Not Satan", "not_satan@hell.doom", "555-6666")
        ).data;

        Item monkeysPaw = service.createItem(supplier.getPpn(), 1,
                "Monkey's Paw", "No strings attached", 0.01f).data;

        service.createDiscount(monkeysPaw, 50, 0.2f);

        Item voodooDoll = service.createItem(supplier.getPpn(), 2,
                "Voodoo Doll", "Human Suffering", 600).data;

        service.createDiscount(voodooDoll, 100, 0.2f);

        Item curseInABox = service.createItem(supplier.getPpn(), 5,
                "Curse in a box", "Gifts for foes", 1000f).data;

        Order order1 = service.createOrder(supplier,
                LocalDate.of(2021, Calendar.JUNE, 6),
                LocalDate.of(2022, Calendar.JUNE, 6)
        , Order.OrderType.Periodical).data;

        service.orderItem(order1, monkeysPaw, 200);
        service.orderItem(order1, voodooDoll, 200);

        Order order2 = service.createOrder(supplier,
                LocalDate.of(2022, Calendar.JUNE, 6),
                LocalDate.of(2023, Calendar.JUNE, 6)
                , Order.OrderType.Periodical).data;

        service.orderItem(order2, monkeysPaw, 50);
        service.orderItem(order2, curseInABox, 1);
    }

    private static void createSupplierGoodFoods(SupplierService service) {
        Supplier supplier = service.createSupplier(2, 22222, "Good Foods",
                true, PaymentCondition.DirectDebit,
                null,
                new MutableContact("Tim Apple", "tim@good.food", "555-0001")
        ).data;

        Item bread = service.createItem(supplier.getPpn(), 1,
                "Whole Bread", "Bread", 7.5f).data;

        service.createDiscount(bread, 50, 0.2f);
        service.createDiscount(bread, 100, 0.3f);
        service.createDiscount(bread, 150, 0.5f);

        Item soyMilk = service.createItem(supplier.getPpn(), 2,
                "Soy Milk", "Dairy alternatives", 6).data;

        service.createDiscount(soyMilk, 100, 0.2f);

        Order order1 = service.createOrder(supplier,
                LocalDate.of(2022, Calendar.FEBRUARY, 5),
                LocalDate.of(2022, Calendar.FEBRUARY, 9)
                , Order.OrderType.Periodical).data;

        service.orderItem(order1, bread, 200);
        service.orderItem(order1, soyMilk, 200);


        Order order2 = service.createOrder(supplier,
                LocalDate.of(2022, Calendar.APRIL, 2),
                LocalDate.of(2022, Calendar.APRIL, 10)
                , Order.OrderType.Periodical).data;

        service.orderItem(order2, bread, 95);
        service.orderItem(order2, soyMilk, 1000);
    }

    private static void createSupplierOfficeStuff(SupplierService service) {
        Supplier supplier = service.createSupplier(1, 1111, "Office Stuff",
                true, PaymentCondition.DirectDebit,
                DayOfWeek.SATURDAY,
                new MutableContact("Ofir Office", "ofir@office.stuff", "555-1234")
        ).data;

        Item pen = service.createItem(supplier.getPpn(), 1,
                "Pen", "Writing", 10).data;

        service.createDiscount(pen, 100, 0.2f);
        service.createDiscount(pen, 200, 0.3f);
        service.createDiscount(pen, 500, 0.5f);

        Item notebook = service.createItem(supplier.getPpn(), 2,
                "Notebook", "Paper", 5).data;

        service.createDiscount(notebook, 100, 0.2f);

        Order order1 = service.createOrder(supplier,
                LocalDate.of(2022, Calendar.FEBRUARY, 1),
                LocalDate.of(2022, Calendar.FEBRUARY, 5)
                , Order.OrderType.Periodical).data;

        service.orderItem(order1, pen, 200);
        service.orderItem(order1, notebook, 200);


        Order order2 = service.createOrder(supplier,
                LocalDate.of(2022, Calendar.APRIL, 1),
                LocalDate.of(2022, Calendar.APRIL, 5)
                , Order.OrderType.Periodical).data;

        service.orderItem(order2, pen, 95);
        service.orderItem(order2, notebook, 1000);
    }

    private static List<DayOfWeek> daysList(DayOfWeek... args) {
        return Arrays.asList(args);
    }
}
