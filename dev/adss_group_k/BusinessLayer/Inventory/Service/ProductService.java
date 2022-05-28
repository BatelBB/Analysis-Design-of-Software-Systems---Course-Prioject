package adss_group_k.BusinessLayer.Inventory.Service;

import adss_group_k.BusinessLayer.Inventory.Controllers.ProductController;
import adss_group_k.BusinessLayer.Inventory.ProductItem;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;
import adss_group_k.dataLayer.dao.PersistenceController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import adss_group_k.shared.response.*;

public class ProductService {
    private ProductController product_controller;

    public ProductService(PersistenceController pc, ProductController product_controller) {
        this.product_controller = product_controller;
    }

    //methods
    public ResponseT<List<String>> getProductNames() {
        try {
            return ResponseT.success(product_controller.getProductNames());
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
        }
    }

    public Response updateCategoryCusDiscount(float discount, LocalDate start_date, LocalDate end_date, String category, String sub_category, String subsub_category) {
        try {
            product_controller.updateCategoryCusDiscount(discount, start_date, end_date, category, sub_category, subsub_category);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateProductCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id) {
        try {
            product_controller.updateProductCusDiscount(discount, start_date, end_date, product_id);
            return new Response(true, null);
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateItemCusDiscount(int product_id, int item_id, float discount, LocalDate start_date, LocalDate end_date) {
        try {
            product_controller.updateItemCusDiscount(product_id, item_id, discount, start_date, end_date);
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
            adss_group_k.BusinessLayer.Inventory.Product addProduct = product_controller.addProduct(name, manufacturer, man_price, cus_price, min_qty, supply_time, category, sub_category, subsub_category);
            return ResponseT.success(new Product(addProduct));
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

    public ResponseT<ProductItem> addItem(int product_id, String store, String location, int supplier, LocalDate expiration_date, boolean on_shelf) {
        try {
            return ResponseT.success(product_controller.addItem(product_id, store, location, supplier, expiration_date, on_shelf));
        } catch (Exception e) {
            return ResponseT.error(e.getMessage());
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
