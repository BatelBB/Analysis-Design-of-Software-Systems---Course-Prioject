package adss_group_k;

public class SchemaInit {
    public final static String schemaInit =
            "BEGIN TRANSACTION;\n" +
                    "CREATE TABLE IF NOT EXISTS \"SubSubCategory\" (\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"subcategory\"\tTEXT NOT NULL,\n" +
                    "\t\"category\"\tTEXT NOT NULL\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Product\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"customerPrice\"\tREAL NOT NULL,\n" +
                    "\t\"minQty\"\tINTEGER NOT NULL,\n" +
                    "\t\"storageQty\"\tINTEGER NOT NULL,\n" +
                    "\t\"shelfQty\"\tINTEGER NOT NULL,\n" +
                    "\t\"subSubcategory\"\tTEXT NOT NULL,\n" +
                    "\t\"subcategory\"\tTEXT NOT NULL,\n" +
                    "\t\"category\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");\n" +
                    "CREATE TABLE \"Order\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"orderType\"\tINTEGER NOT NULL,\n" +
                    "\t\"price\"\tREAL NOT NULL,\n" +
                    "\t\"ordered\"\tDATETIME NOT NULL,\n" +
                    "\t\"provided\"\tDATETIME NOT NULL,\n" +
                    "\t\"ppn\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ")" +
                    "CREATE TABLE IF NOT EXISTS \"ItemInOrder\" (\n" +
                    "\t\"qty\"\tINTEGER NOT NULL,\n" +
                    "\t\"itemSupplierPPN\"\tINTEGER NOT NULL,\n" +
                    "\t\"itemCatalogNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"orderId\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"itemSupplierPPN\",\"itemCatalogNumber\",\"orderId\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"QuantityDiscount\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"quantity\"\tINTEGER NOT NULL,\n" +
                    "\t\"discount\"\tREAL NOT NULL,\n" +
                    "\t\"itemSupplierPPN\"\tINTEGER NOT NULL,\n" +
                    "\t\"itemCatalogNumber\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"ProductReport\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"reportProducer\"\tTEXT NOT NULL,\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"date\"\tDATETIME NOT NULL,\n" +
                    "\t\"type\"\tINTEGER NOT NULL,\n" +
                    "\t\"query\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"ItemReport\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"reportProducer\"\tTEXT NOT NULL,\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"date\"\tDATETIME NOT NULL,\n" +
                    "\t\"type\"\tINTEGER NOT NULL,\n" +
                    "\t\"query\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"ProductInReport\" (\n" +
                    "\t\"qty\"\tINTEGER NOT NULL,\n" +
                    "\t\"reportId\"\tINTEGER NOT NULL,\n" +
                    "\t\"productId\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"reportId\",\"productId\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"ItemInReport\" (\n" +
                    "\t\"qty\"\tINTEGER NOT NULL,\n" +
                    "\t\"reportId\"\tINTEGER NOT NULL,\n" +
                    "\t\"itemId\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"reportId\",\"itemId\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Subcategory\" (\n" +
                    "\t\"Category\"\tTEXT NOT NULL,\n" +
                    "\t\"Name\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"Name\",\"Category\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Category\" (\n" +
                    "\t\"Name\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"Name\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Supplier\" (\n" +
                    "\t\"ppn\"\tINTEGER NOT NULL,\n" +
                    "\t\"bankNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"isDelivering\"\tTINYINT NOT NULL,\n" +
                    "\t\"paymentCondition\"\tINTEGER NOT NULL,\n" +
                    "\t\"regularSupplyingDay\"\tINTEGER NOT NULL,\n" +
                    "\t\"contactEmail\"\tTEXT NOT NULL,\n" +
                    "\t\"contactName\"\tTEXT NOT NULL,\n" +
                    "\t\"contactPhone\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"ppn\")\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Item\" (\n" +
                    "\t\"supplierPPN\"\tINTEGER NOT NULL,\n" +
                    "\t\"catalogNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"productId\"\tINTEGER NOT NULL,\n" +
                    "\t\"price\"\tFLOAT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"supplierPPN\",\"catalogNumber\")\n" +
                    ");\n" +
                    "INSERT INTO \"Category\" (\"Name\") VALUES ('Occult');\n" +
                    "INSERT INTO \"Supplier\" (\"ppn\",\"bankNumber\",\"name\",\"isDelivering\",\"paymentCondition\",\"regularSupplyingDay\",\"contactEmail\",\"contactName\",\"contactPhone\") VALUES (1,111,'Office Stuff',1,1,5,'ofir@office.stuff','Ofir Office','05'),\n" +
                    " (2,222,'Foods & Goods',0,0,-1,'frank@food.stuff','Frank Food','06');\n" +
                    "COMMIT;\n";
}
