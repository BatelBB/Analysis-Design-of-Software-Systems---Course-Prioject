BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "SubSubCategory" (
	"name"	TEXT NOT NULL,
	"subcategory"	TEXT NOT NULL,
	"category"	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "Product" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"customerPrice"	REAL NOT NULL,
	"minQty"	INTEGER NOT NULL,
	"storageQty"	INTEGER NOT NULL,
	"shelfQty"	INTEGER NOT NULL,
	"subSubcategory"	TEXT NOT NULL,
	"subcategory"	TEXT NOT NULL,
	"category"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Order" (
	"id"	INTEGER NOT NULL,
	"orderType"	INTEGER NOT NULL,
	"price"	REAL NOT NULL,
	"ordered"	DATETIME NOT NULL,
	"provided"	DATETIME NOT NULL,
	"ppn"	INTEGER NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ItemInOrder" (
	"qty"	INTEGER NOT NULL,
	"itemSupplierPPN"	INTEGER NOT NULL,
	"itemCatalogNumber"	INTEGER NOT NULL,
	"orderId"	INTEGER NOT NULL,
	PRIMARY KEY("itemSupplierPPN","itemCatalogNumber","orderId")
);
CREATE TABLE IF NOT EXISTS "QuantityDiscount" (
	"id"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	"discount"	REAL NOT NULL,
	"itemSupplierPPN"	INTEGER NOT NULL,
	"itemCatalogNumber"	INTEGER NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ProductReport" (
	"id"	INTEGER NOT NULL,
	"reportProducer"	TEXT NOT NULL,
	"name"	TEXT NOT NULL,
	"date"	DATETIME NOT NULL,
	"type"	INTEGER NOT NULL,
	"query"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ItemReport" (
	"id"	INTEGER NOT NULL,
	"reportProducer"	TEXT NOT NULL,
	"name"	TEXT NOT NULL,
	"date"	DATETIME NOT NULL,
	"type"	INTEGER NOT NULL,
	"query"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ProductInReport" (
	"qty"	INTEGER NOT NULL,
	"reportId"	INTEGER NOT NULL,
	"productId"	INTEGER NOT NULL,
	PRIMARY KEY("reportId","productId")
);
CREATE TABLE IF NOT EXISTS "ItemInReport" (
	"qty"	INTEGER NOT NULL,
	"reportId"	INTEGER NOT NULL,
	"itemId"	INTEGER NOT NULL,
	PRIMARY KEY("reportId","itemId")
);
CREATE TABLE IF NOT EXISTS "Subcategory" (
	"Category"	TEXT NOT NULL,
	"Name"	TEXT NOT NULL,
	PRIMARY KEY("Name","Category")
);
CREATE TABLE IF NOT EXISTS "Category" (
	"Name"	TEXT NOT NULL,
	PRIMARY KEY("Name")
);
CREATE TABLE IF NOT EXISTS "Supplier" (
	"ppn"	INTEGER NOT NULL,
	"bankNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"isDelivering"	TINYINT NOT NULL,
	"paymentCondition"	INTEGER NOT NULL,
	"regularSupplyingDay"	INTEGER NOT NULL,
	"contactEmail"	TEXT NOT NULL,
	"contactName"	TEXT NOT NULL,
	"contactPhone"	TEXT NOT NULL,
	PRIMARY KEY("ppn")
);
CREATE TABLE IF NOT EXISTS "Item" (
	"supplierPPN"	INTEGER NOT NULL,
	"catalogNumber"	INTEGER NOT NULL,
	"productId"	INTEGER NOT NULL,
	"price"	FLOAT NOT NULL,
	PRIMARY KEY("supplierPPN","catalogNumber")
);
CREATE TABLE IF NOT EXISTS "DiscountPair" (
	"ProductId"	INTEGER NOT NULL,
	"ProductItemId"	INTEGER NOT NULL,
	"DiscountPairId"	INTEGER NOT NULL,
	"Discount"	FLOAT NOT NULL,
	"StartDate"	DateTime NOT NULL,
	"EndDate"	DateTime NOT NULL,
	PRIMARY KEY("DiscountPairId","ProductItemId","ProductId")
);
CREATE TABLE IF NOT EXISTS "ProductItem" (
	"ProductId"	INTEGER NOT NULL,
	"Id"	NUMERIC NOT NULL,
	"Store"	TEXT NOT NULL,
	"Location"	TEXT NOT NULL,
	"Supplier"	INTEGER NOT NULL,
	"ExpirationDate"	DATETIME NOT NULL,
	"IsDefect"	TINYINT NOT NULL,
	"OnShelf"	TINYINT NOT NULL,
	"DefectReporter"	TEXT,
	PRIMARY KEY("ProductId","Id")
);
INSERT INTO "Category" ("Name") VALUES ('Occult');
INSERT INTO "Supplier" ("ppn","bankNumber","name","isDelivering","paymentCondition","regularSupplyingDay","contactEmail","contactName","contactPhone") VALUES (1,111,'Office Stuff',1,1,5,'ofir@office.stuff','Ofir Office','05'),
 (2,222,'Foods & Goods',0,0,-1,'frank@food.stuff','Frank Food','06');
COMMIT;
