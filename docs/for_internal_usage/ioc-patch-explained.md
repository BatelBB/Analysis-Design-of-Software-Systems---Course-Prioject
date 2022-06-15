# פלסטר\* מטופש ל-IoC

\* בתוכנה, פלסטר הוא תיקון מאולץ ולא מעוצב טוב שנוצר בגלל סיבות של חוסר זמן

בגלל שהכל קצת לחוץ יצא שהכל קצת מבולגן ולא ברור מה "עץ" ה-dependencies שלנו כלומר איזה מחלקה צריך איזה מחלקה. זה יכול ליצור בעיות ובין השאר אנחנו עשויים ליצור תלות מעגלית. בין השאר, יכול להיות שלמשל `SupplierService` יצטרך את `DeliveryService` וגם ההפך. אז יצרתי פלסטר קצת מטופש שיכול לפתור את זה. במקרה של תלות מעגלית, במקום, למשל, להוסיף את `DeliveryService` ל-`SupplierService` [כמו שהסברתי ב-Pull Request](https://gitlab.com/a9877/adss_group_k/-/merge_requests/14), אפשר לעשות את זה:

1. להוסיף ל-`SupplierService` את `ServiceProvider`
2. "למצוא" דרכו את `DeliveryService` רק איפה שצריך ואז להשתמש

```java
class SupplierService {
    DeliveryService delivery; // <- delete
    ServiceProvider serviceProvider; // <- add
    
    SupplierService(
    	DeliveryService delivery // delete
        ServiceProvider serviceProvider // add
    ) {
        this./* blah blah blah you know how constructors work */
    }
    
    void order(OrderParameters) {
        orderController.create(/* blah blah blah*/);
        
        this.delivery.createDelivery(/* blah blah */); // delete
        
        // add:
        DeliveryService delivery = serviceProvider.get(DeliveryService.class);
        delivery.createDelivery(/* blah blah blah*/);
    }
}
```

