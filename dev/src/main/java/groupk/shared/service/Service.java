package groupk.shared.service;

import groupk.shared.business.Facade;
import groupk.shared.service.dto.*;

import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private Facade facade;

    public Service() throws Exception {
        facade = new Facade();
    }

    public void deleteEmployeeDB(){
        facade.deleteEmployeeDB();
    }

    public void deleteLogisticsDB() {
        facade.deleteLogisticsDB();
    }

    public void loadEmployeeDB(){ facade.loadEmployeeDB();}

    public  Response<Employee> createEmployee(
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
        return facade.createEmployee(name, id, bank, bankID, bankBranch, employmentStart, salaryPerHour, sickDaysUsed, vacationDaysUsed, role, shiftPreferences);
    }

    public Response<Shift> createShift(String subjectID, Calendar date, Shift.Type type,
                                       LinkedList<Employee> staff,
                                       HashMap<Employee.Role, Integer> requiredStaff){
        return facade.createShift(subjectID, date, type, staff, requiredStaff);
    }

    public Response<Employee> readEmployee(String subjectID, String employeeID) {
        return facade.readEmployee(subjectID, employeeID);
    }

    public Response<Shift> readShift(String subjectID, Calendar date , Shift.Type type) {
        return facade.readShift(subjectID, date, type);
    }

    public Response<Employee> deleteEmployee(String subjectID, String employeeID) {
        return facade.deleteEmployee(subjectID, employeeID);
    }

    //dont use
    public Response<Shift> deleteShift(String subjectID, Shift.Type type, Calendar date){
        return facade.deleteShift(subjectID, type, date);
    }

    public Response<List<Employee>> listEmployees(String subjectID) {
        return facade.listEmployees(subjectID);
    }

    public Response<Shift> addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return facade.addEmployeeToShift(subjectID, date, type, employeeID);
    }

    public Response<Shift> removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        return facade.removeEmployeeFromShift(subjectID, date, type, employeeID);
    }

    public Response<Employee> updateEmployee(String subjectID, Employee changed) {
        return facade.updateEmployee(subjectID, changed);
    }

    public Response<Employee> addEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift) {
        return facade.addEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Response<Employee> setEmployeeShiftsPreference(String subjectID, String employeeID, Set<Employee.ShiftDateTime> shiftPreferences) {
        return facade.setEmployeeShiftsPreference(subjectID, employeeID, shiftPreferences);
    }

    public Response<Employee> deleteEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift){
        return facade.deleteEmployeeShiftPreference(subjectID, employeeID, shift);
    }

    public Response<List<Shift>> listShifts(String subjectID) {
        return facade.listShifts(subjectID);
    }

    public Response<List<Shift>> listEmployeeShifts(String subjectID, String employeeID) {
        return facade.listEmployeeShifts(subjectID, employeeID);
    }

    public Response<Integer> numOfEmployeeShifts(String subjectID, String employeeID) {
        return facade.numOfEmployeeShifts(subjectID, employeeID);
    }

    public Response<List<Employee>> whoCanWorkWithRole(String subjectId, Employee.ShiftDateTime day, Employee.Role role) {
        return facade.whoCanWorkWithRole(subjectId, day, role);
    }

    public Response<List<Employee>> whoCanWork(String subjectId, Employee.ShiftDateTime day) {
        return facade.whoCanWork(subjectId, day);
    }

    public Response<Shift> updateRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        return facade.updateRequiredRoleInShift(subjectId, date, type, role, count);
    }

    public Response<Shift> setRequiredStaffInShift(String subjectId, Calendar date, Shift.Type type, HashMap<Employee.Role, Integer> requiredStaff) {
        return facade.setRequiredStaffInShift(subjectId, date, type, requiredStaff);
    }

    public Response<Boolean> deleteDelivery(String subjectID, int deliveryID) {
        return facade.deleteDelivery(subjectID, deliveryID);
    }

    public Response<List<Delivery>> listDeliveries(String subjectID) {
        return facade.listDeliveries(subjectID);
    }

    public Response<List<String>> listVehicles(String subjectID) {
        return facade.listVehicles(subjectID);
    }

    public Response<List<String>[]> createDelivery(String subjectID, String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<Product> products, long hours, long minutes) {
        return facade.createDelivery(subjectID, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes);
    }

    public Response<List<Delivery>> listDeliveriesWithVehicle(String subjectID, String registration) {
        return facade.listDeliveriesWithVehicle(subjectID, registration);
    }

    public Response<Boolean> createVehicle(String subjectID, String license, String registrationPlate, String model, int weight, int maxWeight) {
        return facade.createVehicle(subjectID, license, registrationPlate, model, weight, maxWeight);
    }

    public Response<Boolean> setWeightForDelivery(String subjectID, int deliveryID, int weight) {
        return facade.setWeightForDelivery(subjectID, deliveryID, weight);
    }

    public Response<Boolean> addProductsToTrucking(String subjectID, int truckingID, Product products) {
        return facade.addProductsToTrucking(subjectID, truckingID, products);
    }

    public Response<List<String>> updateSources(String subjectID, int truckingID, List<Site> sources) {
        return facade.updateSources(subjectID, truckingID, sources);
    }

    public Response<List<String>> updateDestination(String subjectID, int truckingID, List<Site> destinations) {
        return facade.updateDestination(subjectID, truckingID, destinations);
    }

    public Response<List<String>> addSources(String subjectID, int truckingID, List<Site> sources) {
        return facade.addSources(subjectID, truckingID, sources);
    }

    public Response<List<String>> addDestination(String subjectID, int truckingID, List<Site> destinations) {
        return facade.addDestination(subjectID, truckingID, destinations);
    }

    public Response<Boolean> moveProducts(String subjectID, int truckingID, Product product) {
        return facade.moveProducts(subjectID, truckingID, product);
    }

    public Response<Boolean> updateVehicleOnTrucking(String subjectID, int truckingID, String registrationPlate) {
        return facade.updateVehicleOnTrucking(subjectID, truckingID, registrationPlate);
    }

    public Response<Boolean> updateDriverOnTrucking(String subjectID, int truckingID, String driverUsername) {
        return facade.updateDriverOnTrucking(subjectID, truckingID, driverUsername);
    }

    public Response<Boolean> updateDateOnTrucking(String subjectID, int truckingID, LocalDateTime newDate) {
        return facade.updateDateOnTrucking(subjectID, truckingID, newDate);
    }

    public Response<Boolean> addLicenseForDriver(String subjectID, String license) {
        return facade.addLicenseForDriver(subjectID, license);
    }

    public Response<List<String>> getDriverLicenses(String subjectID) {
        return facade.getDriverLicenses(subjectID);
    }

    public Response<String[]> getLicensesList() {
        return facade.getLicensesList();
    }

    public Response<String[]> getProductsSKUList() {
        return facade.getProductsSKUList();
    }

    public Response<String[]> getAreasList() {
        return facade.getAreasList();
    }

}
