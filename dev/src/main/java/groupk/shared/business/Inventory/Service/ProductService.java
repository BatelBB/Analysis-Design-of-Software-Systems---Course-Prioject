package groupk.BusinessLayer.Inventory.Service;

import groupk.BusinessLayer.Inventory.Controllers.ProductController;
import groupk.BusinessLayer.Inventory.Service.Objects.Product;
import groupk.BusinessLayer.Inventory.Service.Objects.ProductItem;
import groupk.BusinessLayer.Suppliers.Service.ISupplierService;
import groupk.dataLayer.dao.PersistenceController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import groupk.serviceLayer.ServiceBase;

public class ProductService extends ServiceBase {
    private final ProductController product_controller;

    public ProductService(PersistenceController pc, ProductController product_controller) {
        this.product_controller = product_controller;
    }

    //methods
    public ResponseT<List<String>> getProductNames() {
        return responseFor(product_controller::getProductNames);
    }

    public Response updateCategoryCusDiscount(float discount, LocalDate start_date, LocalDate end_date, String category, String sub_category, String subsub_category) {
        return responseForVoid(() -> product_controller.updateCategoryCusDiscount(
                discount, start_date, end_date, category, sub_category, subsub_category)
        );
    }

    public Response updateProductCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id) {
        return responseForVoid(() -> product_controller
                .updateProductCusDiscount(discount, start_date, end_date, product_id)
        );
    }

    public Response updateItemCusDiscount(int product_id, int item_id, float discount, LocalDate start_date, LocalDate end_date) {
        return responseForVoid(() ->  product_controller
                .updateItemCusDiscount(product_id, item_id, discount, start_date, end_date));
    }

    public Response updateProductCusPrice(int product_id, float price) {
        return responseForVoid(() ->
            product_controller.updateProductCusPrice(product_id, price)
        );
    }

    public Response updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        return responseForVoid(() -> product_controller
                .updateItemDefect(product_id, item_id, is_defect, defect_reporter));
    }

    public ResponseT<Product> addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) {
        return responseFor(() -> new Product(product_controller
                .addProduct(name, manufacturer, man_price, cus_price, min_qty,
                        supply_time, category, sub_category, subsub_category)
        ));
    }

    public Response removeProduct(int id) {
        return responseForVoid(() -> product_controller.removeProduct(id));
    }

    public ResponseT<groupk.BusinessLayer.Inventory.Service.Objects.ProductItem> addItem(int product_id, String store, String location, int supplier, LocalDate expiration_date, boolean on_shelf) {
        return responseFor(() ->
                new ProductItem(
                        product_controller.addItem(
                                product_id, store, location,
                                supplier, expiration_date,
                                on_shelf
                        )
                )
        );
    }

    public ResponseT<Boolean> removeItem(int product_id, int item_id) {
        return responseFor(() -> product_controller.removeItem(product_id, item_id));
    }

    public ResponseT<String> getItemLocation(int product_id, int item_id) {
        return responseFor(() -> product_controller.getItemLocation(product_id, item_id));
    }
    public ResponseT<Integer> getMinQty(int product_id){
        return responseFor(() ->product_controller.getMinQty(product_id));
    }

    public Response changeItemLocation(int product_id, int item_id, String location) {
        return responseForVoid(() -> product_controller.changeItemLocation(product_id, item_id, location));
    }

    public Response changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        return responseForVoid(() -> product_controller.changeItemOnShelf(product_id, item_id, on_shelf));
    }

    public boolean productsInCategory(String category) {
        return product_controller.productsInCategory(category);
    }

    public boolean productsInSubCategory(String category, String sub_category) {
        return product_controller.productsInSubCategory(category, sub_category);
    }

    public boolean productsInSubSubCategory(String category, String sub_category, String sub_sub_category) {
        return product_controller.productsInSubSubCategory(category, sub_category, sub_sub_category);
    }

    public ResponseT<List<groupk.BusinessLayer.Inventory.Product>> getProducts() {
        return responseFor(product_controller::getProducts);
    }

//    public Response addOrderRecord(int orderId, Map<Integer, Integer> productAmount) {
//        return responseForVoid(()->product_controller.addOrderRecord(orderId,productAmount));
//    }

//    public Response receiveTrucking(int trucking_id){
//        return responseForVoid(()->product_controller.receiveTrucking(trucking_id));
//    }
}
