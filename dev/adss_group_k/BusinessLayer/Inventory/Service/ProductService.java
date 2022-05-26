package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Controllers.ProductController;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import adss_group_k.shared.response.*;

public class ProductService {
    private final ProductController product_controller;

    public ProductService() {
        product_controller = ProductController.getInstance();
    }

    //methods
    public ResponseT<List<String>> getProductIdes() {
        try {
            return ResponseT.success(product_controller.getProductIdes());
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

//    public void updateCategoryManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
//        try {
//            product_controller.updateCategoryManDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public Response updateCategoryCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String category, String sub_category, String subsub_category) {
        try {
            product_controller.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateProductSupplierManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, String supplier) {
        try {
            product_controller.updateProductSupplierManDiscount(discount, start_date, end_date, supplier);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

//    public Response updateProductManDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
//        try {
//            product_controller.updateProductManDiscount(discount, start_date, end_date, product_id);
//            return new Response(true, null);
//        } catch (Exception e) {
//            return new Response(false, e.getMessage());
//        }
//    }

    public Response updateProductCusDiscount(double discount, LocalDateTime start_date, LocalDateTime end_date, int product_id) {
        try {
            product_controller.updateProductCusDiscount(discount, start_date, end_date, product_id);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

//    public void updateItemManDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
//        try {
//            product_controller.updateItemManDiscount(product_id, item_id, discount, start_date, end_date);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public Response updateItemCusDiscount(int product_id, int item_id, double discount, LocalDateTime start_date, LocalDateTime end_date) {
        try {
            product_controller.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateProductManPrice(int product_id, double price) {
        try {
            product_controller.updateProductManPrice(product_id, price);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateProductCusPrice(int product_id, double price) {
        try {
            product_controller.updateProductCusPrice(product_id, (float) price);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        try {
            product_controller.updateItemDefect(product_id, item_id, is_defect, defect_reporter);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public ResponseT<Product> addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) {
        try {
            return ResponseT.success(new Product(product_controller.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time, category, sub_category, subsub_category)));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public Response removeProduct(int id) {
        try {
            product_controller.removeProduct(id);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response addItem(int product_id, String store, String location, String supplier, LocalDateTime expiration_date, boolean on_shelf) {
        try {
            product_controller.addItem(product_id, store, location, supplier, expiration_date, on_shelf);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response removeItem(int product_id, int item_id) {
        try {
            product_controller.removeItem(product_id, item_id);
            return new Response(true, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Response(true, null);
        }
    }

    public ResponseT<String> getItemLocation(int product_id, int item_id) {
        try {
            return ResponseT.success(product_controller.getItemLocation(product_id, item_id));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public Response changeItemLocation(int product_id, int item_id, String location) {
        try {
            product_controller.changeItemLocation(product_id, item_id, location);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
        try {
            product_controller.changeItemOnShelf(product_id, item_id, on_shelf);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
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

    public void restart() {
        product_controller.restart();
    }

    public ResponseT<Integer> getMinAmount(String proName) {
        try {
            return ResponseT.success(product_controller.getMinAmount(proName));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public ResponseT<Map<String, Integer>> getDeficiency() {
        try {
            return ResponseT.success(product_controller.getDeficiency().data);
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }
}
