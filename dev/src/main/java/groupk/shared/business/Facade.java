package groupk.shared.business;

import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;
import groupk.shared.business.Inventory.Categories.Category;
import groupk.shared.business.Inventory.Service.Objects.Product;
import groupk.shared.business.Inventory.Service.Objects.ProductItem;
import groupk.shared.business.Inventory.Service.Objects.Report;
import groupk.shared.business.Suppliers.BusinessLogicException;
import groupk.shared.business.Inventory.Service.Objects.SubCategory;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.shared.business.Suppliers.BussinessObject.Order;
import groupk.shared.business.Suppliers.BussinessObject.QuantityDiscount;
import groupk.shared.business.Suppliers.BussinessObject.Supplier;
import groupk.shared.service.Response;
import groupk.shared.service.dto.*;
import groupk.shared.service.ServiceBase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Facade {
    EmployeesController employees;
    LogisticsController logistics;

    ProductController product_controller;
    CategoryController category_controller;
    ReportController report_controller;

    private final ItemController items;
    private final OrderController orders;
    private final QuantityDiscountController discounts;
    private final SupplierController suppliers;

    public Facade(PersistenceController p) {
        employees = new EmployeesController();
        logistics = new LogisticsController();
        category_controller = new CategoryController(p);
        product_controller = new ProductController(p, category_controller);
        suppliers = new SupplierController(p);
        items = new ItemController(p, suppliers);
        discounts = new QuantityDiscountController(p, items);
        orders = new OrderController(p, discounts);
    }

    public void deleteEmployeeDB() {
        employees.deleteEmployeeDB();
    }

    public void deleteLogisticsDB() {
        logistics.deleteDB();
    }

    public void loadEmployeeDB() {
        employees.loadEmployeeDB();
    }

    // Previously addEmployee.
    public Response<Employee> createEmployee(
            String name,
            String id,
            String bank,
            int bankID,
            int bankBranch,
            Calendar employmentStart,
            int salaryPerHour,
            int sickDaysUsed,
            int vacationDaysUsed,
            Employee.Role role,
            Set<Employee.ShiftDateTime> shiftPreferences) {
        return employees.createEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role, shiftPreferences);
    }

    // Previously addShift.
    public Response<Shift> createShift(String subjectID, Calendar date, Shift.Type type,
                                       LinkedList<Employee> staff,
                                       HashMap<Employee.Role, Integer> requiredStaff) {
        return employees.createShift(subjectID, date, type, staff, requiredStaff);
    }

    public Response<Employee> readEmployee(String subjectID, String employeeID) {
        return employees.readEmployee(subjectID, employeeID);
    }

    public Response<Shift> readShift(String subjectID, Calendar date, Shift.Type type) {
        return employees.readShift(subjectID, date, type);
    }

    public Response<Employee> deleteEmployee(String subjectID, String employeeID) {
        if (isFromRole(employeeID, Employee.Role.Driver).getValue()) {
            Response<List<Delivery>> futureDeliveries = logistics.listFutureDeliveries(Integer.parseInt(subjectID));
            if (futureDeliveries.isError() | futureDeliveries.getValue() == null)
                return new Response<>("Oops, we are currently unable to find data on this logistics manager's leads.\n" +
                        "Therefore, the operation cannot be performed at this time.");
            if (futureDeliveries.getValue().size() > 0)
                return new Response<>("Oops, the truck manager has future truckings. Before you address the issue, you will not be able to take action");
        } else if (!isFromRole(subjectID, Employee.Role.Driver).getValue()) {
            Response<List<Delivery>> futureDeliveries = logistics.listFutureDeliveriesByDriver(Integer.parseInt(subjectID));
            if (futureDeliveries.isError() | futureDeliveries.getValue() == null)
                return new Response<>("Oops, we are currently unable to find data on this trucking manager's leads.\n" +
                        "Therefore, the operation cannot be performed at this time.");
            if (futureDeliveries.getValue().size() > 0)
                return new Response<>("Oops, the truck manager has future truckings. Before you address the issue, you will not be able to take action");
        }
        return employees.deleteEmployee(subjectID, employeeID);
    }

    public Response<Shift> deleteShift(String subjectID, Shift.Type type, Calendar date) {
        return employees.deleteShift(subjectID, type, date);
    }

    public Response<List<Employee>> listEmployees(String subjectID) {
        return employees.listEmployees(subjectID);
    }

    public Response<Shift> addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return employees.addEmployeeToShift(subjectID, date, type, employeeID);
    }

    public Response<Shift> removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        if (isTheDriverInShift(subjectID, employeeID, date, type))
            return new Response<>("Oops, the driver has a trucking at that shift. So, we cannot remove the shift");
        return employees.removeEmployeeFromShift(subjectID, date, type, employeeID);
    }

    public Response<Employee> updateEmployee(String subjectID, Employee changed) {
        return employees.updateEmployee(subjectID, changed);
    }

    public Response<Employee> addEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        return employees.addEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Response<Employee> setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.ShiftDateTime> shiftPreferences) {
        return employees.setEmployeeShiftsPreference(subjectID, employeeID, shiftPreferences);
    }

    public Response<Employee> deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        return employees.deleteEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Response<List<Shift>> listShifts(String subjectID) {
        return employees.listShifts(subjectID);
    }

    public Response<List<Shift>> listEmployeeShifts(String subjectID, String employeeID) {
        return employees.listEmployeeShifts(subjectID, employeeID);
    }

    public Response<Integer> numOfEmployeeShifts(String subjectID, String employeeID) {
        return employees.numOfEmployeeShifts(subjectID, employeeID);
    }

    public Response<Shift> updateRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        return employees.updateRequiredRoleInShift(subjectId, date, type, role, count);
    }

    public Response<Shift> setRequiredStaffInShift(String subjectId, Calendar date, Shift.Type type, HashMap<Employee.Role, Integer> requiredStaff) {
        return employees.setRequiredStaffInShift(subjectId, date, type, requiredStaff);
    }

    public Response<List<Employee>> whoCanWorkWithRole(String subjectId, Employee.ShiftDateTime day, Employee.Role role) {
        return employees.whoCanWorkWithRole(subjectId, day, role);
    }

    public Response<List<Employee>> whoCanWork(String subjectId, Employee.ShiftDateTime day) {
        return employees.whoCanWork(subjectId, day);
    }

    // Previously removeTrucking
    public Response<Boolean> deleteDelivery(String subjectID, int deliveryID) {
        Response<Employee> subject = employees.readEmployee(subjectID, subjectID);
        if (subject.isError()) {
            return new Response<>(subject.getErrorMessage());
        }
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("Subject must be of logistics manager role.");
        }
        return logistics.deleteDelivery(Integer.parseInt(subjectID), deliveryID);
    }

    // Previously printBoard
    public Response<List<Delivery>> listDeliveries(String subjectID) {
        if (isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() || isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return logistics.listDeliveries(Integer.parseInt(subjectID));
        }
        if (isFromRole(subjectID, Employee.Role.Driver).getValue()) {
            return logistics.listDeliveriesByDriver(Integer.parseInt(subjectID));
        } else
            return new Response<>("You are not authorized to perform this operation");
    }

    public Response<List<String>> listVehicles(String subjectID) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.listVehicles();
    }

    public Response<Delivery> createDelivery(String subjectID, String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<Product> products, long hours, long minutes) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        if (!isFromRole(driverUsername, Employee.Role.Driver).getValue()) {
            return new Response<>("There is no driver with id: " + driverUsername);
        }
        if (!isTheDriverHasShift(subjectID, driverUsername, date, hours, minutes))
            return new Response<>("The driver has no shift at that time");
        if (!isThereWorkerWithThisRoleInShift(subjectID, date, Employee.Role.Logistics))
            return new Response<>("There is no logistics worker in this shift to except delivery");
        else
            return logistics.createDelivery(Integer.parseInt(subjectID), registrationPlateOfVehicle, date, Integer.parseInt(driverUsername), sources, destinations, products, hours, minutes);
    }

    private boolean isThereWorkerWithThisRoleInShift(String subjectID, LocalDateTime date, Employee.Role role) {
        //date.getMonthValue()-1 because in GregorianCalendar Month from 0 to 11 and LocalDateTime is from 1 to 12
        Calendar calendar = new GregorianCalendar(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        Response<Shift> shift;
        if (date.getHour() + 1 < 16) // the hour is from 0 to 23, therefore 16 is 17
            shift = readShift(subjectID, calendar, Shift.Type.Morning);
        else
            shift = readShift(subjectID, calendar, Shift.Type.Evening);
        if (shift.isError())
            return false;
        for (Employee employee : shift.getValue().getStaff()) {
            if (employee.role.equals(role))
                return true;
        }
        return false;
    }

    private boolean isTheDriverInShift(String subjectID, String driverID, Calendar date, groupk.shared.service.dto.Shift.Type type) {
        Response<Shift> shift = readShift(subjectID, date, type);
        if (shift.isError()) {
            return false;
        }
        for (Employee employee : shift.getValue().getStaff()) {
            if (employee.id.equals(driverID))
                return true;
        }
        return false;
    }

    private boolean isTheDriverHasShift(String subjectID, String driverID, LocalDateTime date, long hours, long minutes) {
        //date.getMonthValue()-1 because in GregorianCalendar Month from 0 to 11 and LocalDateTime is from 1 to 12
        if (date.getHour() < 8 | (date.plusHours(hours).plusMinutes(minutes).getHour() > 0 && date.plusHours(hours).plusMinutes(minutes).getHour() < 8))
            return false;
        Calendar calendar = new GregorianCalendar(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        Response<Shift> shiftResponse;
        if (date.getHour() + 1 < 16) // the hour is from 0 to 23, therefore 16 is 17
            shiftResponse = readShift(subjectID, calendar, Shift.Type.Morning);
        else
            shiftResponse = readShift(subjectID, calendar, Shift.Type.Evening);
        if (shiftResponse.isError()) {
            return false;
        }
        for (Employee employee : shiftResponse.getValue().getStaff()) {
            if (employee.id.equals(driverID))
                return true;
        }
        return false;
    }

    public Response<List<Delivery>> listDeliveriesWithVehicle(String subjectID, String registration) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.listDeliveriesWithVehicle(subjectID);
    }

    public Response<Boolean> createVehicle(String subjectID, String license, String registrationPlate, String model, int weight, int maxWeight) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.createVehicle(license, registrationPlate, model, weight, maxWeight);
    }

    public Response<Boolean> setWeightForDelivery(String subjectID, int deliveryID, int weight) {
        if (!isFromRole(subjectID, Employee.Role.Driver).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.setWeightForDelivery(Integer.parseInt(subjectID), deliveryID, weight);
    }

    public Response<Boolean> addProductsToTrucking(String subjectID, int truckingID, Product products) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.addProductsToTrucking(Integer.parseInt(subjectID), truckingID, products);
    }

    public Response<List<String>> updateSources(String subjectID, int truckingID, List<Site> sources) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.updateSources(Integer.parseInt(subjectID), truckingID, sources);
    }

    public Response<List<String>> updateDestination(String subjectID, int truckingID, List<Site> destinations) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.updateDestination(Integer.parseInt(subjectID), truckingID, destinations);
    }

    public Response<List<String>> addSources(String subjectID, int truckingID, List<Site> sources) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.addSources(Integer.parseInt(subjectID), truckingID, sources);
    }

    public Response<List<String>> addDestination(String subjectID, int truckingID, List<Site> destinations) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.addDestination(Integer.parseInt(subjectID), truckingID, destinations);
    }

    public Response<Boolean> moveProducts(String subjectID, int truckingID, Product product) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.moveProducts(Integer.parseInt(subjectID), truckingID, product);
    }

    public Response<Boolean> updateVehicleOnTrucking(String subjectID, int truckingID, String registrationPlate) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.updateVehicleOnTrucking(Integer.parseInt(subjectID), truckingID, registrationPlate);
    }

    public Response<Boolean> updateDriverOnTrucking(String subjectID, int truckingID, String driverUsername) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        if (!isFromRole(driverUsername, Employee.Role.Driver).getValue()) {
            return new Response<>("There is no driver with id: " + driverUsername);
        }
        Response<Delivery> delivery = getTruckingById(subjectID, truckingID);
        if (delivery.isError())
            return new Response<>(delivery.getErrorMessage());
        if (!isTheDriverHasShift(subjectID, driverUsername, delivery.getValue().date, delivery.getValue().durationInMinutes / 60, delivery.getValue().durationInMinutes % 60))
            return new Response<>("The driver has no shift at that time");
        return logistics.updateDriverOnTrucking(Integer.parseInt(subjectID), truckingID, Integer.parseInt(driverUsername));
    }

    public Response<Boolean> updateDateOnTrucking(String subjectID, int truckingID, LocalDateTime newDate) {
        if (!isFromRole(subjectID, Employee.Role.LogisticsManager).getValue() && !isFromRole(subjectID, Employee.Role.TruckingManger).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        if (!isThereWorkerWithThisRoleInShift(subjectID, newDate, Employee.Role.Logistics))
            return new Response<>("There is no logistics worker in this shift to except delivery");
        return logistics.updateDateOnTrucking(Integer.parseInt(subjectID), truckingID, newDate);
    }

    public Response<Boolean> addLicenseForDriver(String subjectID, String license) {
        if (!isFromRole(subjectID, Employee.Role.Driver).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.addLicenseForDriver(Integer.parseInt(subjectID), license);
    }

    public Response<List<String>> getDriverLicenses(String subjectID) {
        if (!isFromRole(subjectID, Employee.Role.Driver).getValue()) {
            return new Response<>("You are not authorized to perform this operation");
        }
        return logistics.getDriverLicenses(Integer.parseInt(subjectID));
    }

    public Response<Delivery> getTruckingById(String subjectID, int truckingID) {
        return logistics.getTruckinfByID(Integer.parseInt(subjectID), truckingID);
    }

    public Response<Boolean> isFromRole(String employeeID, Employee.Role role) {
        return employees.isFromRole(employeeID, role);
    }

    public Response<String[]> getLicensesList() {
        return logistics.getLicensesList();
    }

    public Response<String[]> getProductsSKUList() {
        return logistics.getProductsSKUList();
    }

    public Response<String[]> getAreasList() {
        return logistics.getAreasList();
    }

    public ResponseT<List<String>> getProductNames() {
        return responseFor(product_controller::getProductNames);
    }

    public SI_Response updateCategoryCusDiscount(float discount, LocalDate start_date, LocalDate end_date, String category, String sub_category, String subsub_category) {
        return responseForVoid(() -> product_controller.updateCategoryCusDiscount(
                discount, start_date, end_date, category, sub_category, subsub_category)
        );
    }

    public SI_Response updateProductCusDiscount(float discount, LocalDate start_date, LocalDate end_date, int product_id) {
        return responseForVoid(() -> product_controller
                .updateProductCusDiscount(discount, start_date, end_date, product_id)
        );
    }

    public SI_Response updateItemCusDiscount(int product_id, int item_id, float discount, LocalDate start_date, LocalDate end_date) {
        return responseForVoid(() -> product_controller
                .updateItemCusDiscount(product_id, item_id, discount, start_date, end_date));
    }

    public SI_Response updateProductCusPrice(int product_id, float price) {
        return responseForVoid(() ->
                product_controller.updateProductCusPrice(product_id, price)
        );
    }

    public SI_Response updateItemDefect(int product_id, int item_id, boolean is_defect, String defect_reporter) {
        return responseForVoid(() -> product_controller
                .updateItemDefect(product_id, item_id, is_defect, defect_reporter));
    }

    public ResponseT<Product> addProduct(String name, String manufacturer, double man_price, float cus_price, int min_qty, int supply_time, String category, String sub_category, String subsub_category) {
        return responseFor(() -> new Product(product_controller
                .addProduct(name, manufacturer, man_price, cus_price, min_qty,
                        supply_time, category, sub_category, subsub_category)
        ));
    }

    public SI_Response removeProduct(int id) {
        return responseForVoid(() -> product_controller.removeProduct(id));
    }

    public ResponseT<ProductItem> addItem(int product_id, String store, String location, int supplier, LocalDate expiration_date, boolean on_shelf) {
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

    public SI_Response removeItem(int product_id, int item_id) {
        return responseForVoid(() -> product_controller.removeItem(product_id, item_id));
    }

    public ResponseT<String> getItemLocation(int product_id, int item_id) {
        return responseFor(() -> product_controller.getItemLocation(product_id, item_id));
    }

    public SI_Response changeItemLocation(int product_id, int item_id, String location) {
        return responseForVoid(() -> product_controller.changeItemLocation(product_id, item_id, location));
    }

    public SI_Response changeItemOnShelf(int product_id, int item_id, boolean on_shelf) {
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

    public ResponseT<List<String>> getCategoriesNames() {
        return responseFor(category_controller::getCategoriesNames);
    }

    public SI_Response addCategory(String name) {
        return responseForVoid(() -> category_controller.addCategory(name));
    }

    public SI_Response addSubCategory(String categoryName, String subCategoryName) {
        return responseForVoid(() -> category_controller.addSubCategory(categoryName, subCategoryName));
    }

    public SI_Response addSubSubCategory(String category, String sub_category, String name) {
        return responseForVoid(() -> category_controller.addSubSubCategory(category, sub_category, name));
    }

    public SI_Response removeCategory(String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeCategory(name, safe_remove));
    }

    public SI_Response removeSubCategory(String category, String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeSubCategory(category, name, safe_remove));
    }

    public SI_Response removeSubSubCategory(String category, String sub_category, String name, boolean safe_remove) {
        return responseForVoid(() -> category_controller.removeSubSubCategory(
                category, sub_category, name, safe_remove));
    }

    public ResponseT<Category> getCategory(String name) {
        return responseFor(() -> category_controller.getCategory(name));
    }

    public ResponseT<SubCategory> getSubCategory(String categoryName, String SubCategoryName) {
        return responseFor(() -> {
            groupk.shared.business.Inventory.Categories.SubCategory subCategory = category_controller.getSubCategory(categoryName, SubCategoryName);
            return new SubCategory(subCategory);
        });
    }

    public ResponseT<List<Integer>> getReportListNames() {
        return responseFor(report_controller::getReportListNames);
    }

    public SI_Response removeReport(int id) {
        return responseForVoid(() -> report_controller.removeReport(id));
    }

    public ResponseT<Report> getReport(int id) {
        return responseFor(() -> new Report(report_controller.getReportForProduct(id)));
    }

    public ResponseT<Report> createMissingReport(String name, String report_producer) {
        return responseFor(() -> new Report(report_controller.createMissingReport(name, report_producer)));
    }

    public ResponseT<Report> createExpiredReport(String name, String report_producer) {
        return responseFor(() -> new Report(report_controller.createExpiredReport(name, report_producer)));
    }

    public ResponseT<Report> createSurplusesReport(String name, String report_producer) {
        return responseFor(() -> new Report(report_controller.createSurplusesReport(name, report_producer)));
    }

    public ResponseT<Report> createDefectiveReport(String name, String report_producer) {
        return responseFor(() -> new Report(report_controller.createDefectiveReport(name, report_producer)));
    }

    public ResponseT<Report> createBySupplierReport(String name, String report_producer, int suppName) {
        return responseFor(() -> new Report(report_controller.createBySupplierReport(name, report_producer, suppName)));
    }

    public ResponseT<Report> createByProductReport(String name, String report_producer, String proName) {
        return responseFor(() -> new Report(report_controller.createByProductReport(name, report_producer, proName)));
    }

    public ResponseT<Report> createByCategoryReport(String name, String report_producer, String CatName, String subCatName, String subSubCatName) {
        return responseFor(() -> new Report(
                report_controller.createByCategoryReport(name, report_producer,
                        CatName, subCatName, subSubCatName)));
    }

    public ResponseT<Order> getOrder(int id) {
        return null;
    }

    public ResponseT<Supplier> createSupplier(int ppn, int bankAccount, String name,
                                              boolean isDelivering, PaymentCondition paymentCondition,
                                              DayOfWeek regularSupplyingDays, String contactName,
                                              String contactPhone, String contactEmail) {
        return responseFor(() -> suppliers.create(new CreateSupplierDTO(
                ppn, bankAccount, name, isDelivering,
                paymentCondition, regularSupplyingDays, contactEmail,
                contactName, contactPhone
        )));
    }

    public Collection<Supplier> getSuppliers() {
        return suppliers.all();
    }

    public ResponseT<Supplier> getSupplier(int ppn) throws BusinessLogicException {
        return responseFor(() -> suppliers.get(ppn));
    }

    public SI_Response deleteSupplier(int ppn) {
        return responseForVoid(() -> {
            Supplier s = suppliers.get(ppn);
            items.deleteAllFromSupplier(s);
            orders.deleteAllFromSupplier(s);
            suppliers.delete(ppn);
        });
    }

    public ResponseT<Item> createItem(int supplierPPN, int catalogNumber, int productID, float price) {
        return responseFor(() -> {
            ResponseT<Supplier> response = getSupplier(supplierPPN);
            if (!response.success) {
                throw new BusinessLogicException(response.error);
            }
            return items.create(
                    response.data,
                    catalogNumber,
                    productID,
                    price
            );
        });
    }

    public Collection<Item> getItems() {
        return items.all();
    }

    public ResponseT<Item> getItem(int ppn, int catalog) {
        return responseFor(() -> items.get(ppn, catalog));
    }

    public ResponseT<QuantityDiscount> createDiscount(int supplierPPN, int catalogN, int amount, float discount) {
        return responseFor(() -> {
            Item item = items.get(supplierPPN, catalogN);
            QuantityDiscount ret = discounts.createDiscount(item, amount, discount);
            orders.refreshPricesAndDiscounts(item);
            return ret;
        });
    }

    public SI_Response deleteDiscount(QuantityDiscount discount) {
        return responseForVoid(() -> {
            discounts.delete(discount.id);
            orders.refreshPricesAndDiscounts(discount.item);
        });
    }

    public ResponseT<Order> createOrder(int supplierPPN, LocalDate ordered, LocalDate delivered, OrderType type) {
        return responseFor(() -> {
                    ResponseT<Supplier> supplier = getSupplier(supplierPPN);
                    if (!supplier.success) {
                        throw new BusinessLogicException(supplier.error);
                    }
                    return orders.create(supplier.data, type, ordered, delivered);
                }
        );
    }

    public Collection<Order> getOrders() {
        return orders.all();
    }

    public SI_Response deleteOrder(int orderId) {
        return responseForVoid(() -> orders.delete(orderId));
    }

    public QuantityDiscount getDiscount(int amount, int ppn, int catalog) throws BusinessLogicException {
        return null;
    }

    public SI_Response orderItem(int orderId, int supplier, int catalogNumber, int amount) {
        return responseForVoid(() -> orders.orderItem(
                orders.get(orderId),
                items.get(supplier, catalogNumber),
                amount
        ));
    }

    public SI_Response setPrice(int supplier, int catalogNumber, float price) {
        return responseForVoid(() -> {
            items.setPrice(supplier, catalogNumber, price);
            orders.refreshPricesAndDiscounts(items.get(supplier, catalogNumber));
        });
    }

    public Collection<QuantityDiscount> getDiscounts() {
        return discounts.getAllDiscounts();
    }

    public SI_Response setOrderOrdered(int orderID, LocalDate ordered) throws BusinessLogicException {
        return responseForVoid(() -> orders.setOrdered(orders.get(orderID), ordered));
    }

    public SI_Response setOrderProvided(int orderID, LocalDate provided) throws BusinessLogicException {
        return responseForVoid(() -> orders.setProvided(orders.get(orderID), provided));
    }

    public SI_Response setSupplierBankAccount(int supplierPPN, int bankAct) {
        return responseForVoid(() -> suppliers.setBankAccount(supplierPPN, bankAct));
    }

    public SI_Response setSupplierCompanyName(int supplierPPN, String newName) {
        return responseForVoid(() -> suppliers.setSupplierName(supplierPPN, newName));
    }

    public SI_Response setSupplierIsDelivering(int supplierPPN, boolean newValue) {
        return responseForVoid(() -> suppliers.setIsDelivering(supplierPPN, newValue));
    }

    public SI_Response setSupplierPaymentCondition(int supplierPPN, PaymentCondition payment) {

        return responseForVoid(() -> suppliers.setPaymentCondition(supplierPPN, payment));
    }

    public SI_Response setSupplierRegularSupplyingDays(int supplierPPN, DayOfWeek dayOfWeek) {
        return responseForVoid(() -> suppliers.setRegularSupplyingDays(supplierPPN, dayOfWeek));
    }

    public SI_Response setSupplierContact(int supplierPPN, String name, String phoneNumber, String email) {

        return responseForVoid(() -> suppliers.setContact(supplierPPN, name, phoneNumber, email));
    }

    public SI_Response updateOrderAmount(int orderID, int supplier, int catalogNumber, int amount) {
        return null;
    }

    protected SI_Response voidOk() {
        return new SI_Response(true, null);
    }

    protected SI_Response voidError(String message) {
        return new SI_Response(false, message);
    }

    protected <T> ResponseT<T> ok(T data) {
        return new ResponseT<T>(true, null, data);
    }

    protected <T> ResponseT<T> error(String error) {
        return new ResponseT<T>(false, error, null);
    }

    protected <T> ResponseT<T> responseFor(ServiceBase.ThrowingFactory<T> lambda) {
        try {
            return ok(lambda.get());
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    protected SI_Response responseForVoid(ServiceBase.ThrowingRunnable lambda) {
        try {
            lambda.run();
            return voidOk();
        } catch (Exception e) {
            return voidError(e.getMessage());
        }
    }



    public static class SI_Response {
        public final boolean success;
        public final String error;


        private SI_Response(boolean success, String error) {
            this.success = success;
            this.error = error;
        }

        @Override
        public String toString() {
            return success ? "OK" : "Error: " + error;
        }
    }

    public static class ResponseT<T> extends SI_Response {
        public final T data;

        private ResponseT(boolean success, String error, T data) {
            super(success, error);
            this.data = data;
        }

        @Override
        public String toString() {
            return success ? Objects.toString(data) : "Error: " + error;
        }
    }

    public static interface ThrowingFactory<T> {
        T get() throws Exception;
    }

    public static interface ThrowingRunnable {
        void run() throws Exception;
    }
}
