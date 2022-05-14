package groupk.shared.service;

import groupk.shared.business.Facade;
import groupk.shared.service.dto.*;

import java.util.*;

public class Service {
    private Facade facade;

    public Employee createEmployee(
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

    public Response<Employee> updateEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift, boolean canWork) {
        return updateEmployeeShiftPreference(subjectID, employeeID, shift, canWork);
    }

    public Response<List<Shift>> listShifts(String subjectID) {
        return facade.listShifts(subjectID);
    }

    public Response<Shift> updateRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        return facade.updateRequiredRoleInShift(subjectId, date, type, role, count);
    }

    public Response<Delivery> deleteDelivery(String subjectID, int deliveryID) {
        return facade.deleteDelivery(subjectID, deliveryID);
    }

    public Response<List<Delivery>> listDeliveries(String subjectID) {
        return facade.listDeliveries(subjectID);
    }

    public Response<List<Delivery>> listDeliveriesByDriver(String subjectID, String driverID) {
        return facade.listDeliveriesByDriver(subjectID, driverID);
    }

    public Response<List<String>> listVehicles(String subjectID) {
        return facade.listVehicles(subjectID);
    }

    public Response<Delivery> createDelivery(String subjectID, String registrationPlateOfVehicle, Calendar date, String driverUsername, List<Site> sources, List<Site> destinations, List<Product> products, long durationInMinutes) {
        return facade.createDelivery(subjectID, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, durationInMinutes);
    }

    public Response<List<Delivery>> listDeliveriesWithVehicle(String subjectID, String registration) {
        return facade.listDeliveriesWithVehicle(subjectID, registration);
    }

    public Response<Delivery> updateDelivery(String subjectID, Delivery updated) {
        return facade.updateDelivery(subjectID, updated);
    }

    public Response createVehicle(String subjectID, Driver.License license, String registrationPlate, String model, int weight, int maxWeight) {
        return facade.createVehicle(subjectID, license, registrationPlate, model, weight, maxWeight);
    }

    public Response<Driver> createDriver(String subjectID, String employeeID, Set<Driver.License> licenses) {
        return facade.createDriver(subjectID, employeeID, licenses);
    }

    public Response<Driver> updateDriver(String subjectID, Driver updated) {
        return facade.updateDriver(subjectID, updated);
    }

    public Response setWeightForDelivery(String subjectID, int deliveryID, int weight) {
        return facade.setWeightForDelivery(subjectID, deliveryID, weight);
    }
}
