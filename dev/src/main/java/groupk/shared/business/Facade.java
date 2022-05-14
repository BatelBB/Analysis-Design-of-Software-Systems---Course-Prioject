package groupk.shared.business;

import groupk.shared.service.Response;
import groupk.shared.service.dto.*;

import java.util.*;

public class Facade {
    EmployeesController employees;
    LogisticsController logistics;

    public Facade() {
        employees = new EmployeesController();
        logistics = new LogisticsController();
    }

    // Previously addEmployee.
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
        throw new UnsupportedOperationException("TODO");
    }

    // Previously addShift.
    public Response<Shift> createShift(String subjectID, Calendar date, Shift.Type type,
                                    LinkedList<Employee> staff,
                                    HashMap<Employee.Role, Integer> requiredStaff){
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> readEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> readShift(String subjectID, Calendar date , Shift.Type type) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> deleteEmployee(String subjectID, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<List<Employee>> listEmployees(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> addEmployeeToShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> removeEmployeeFromShift(String subjectID, Calendar date, Shift.Type type, String employeeID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> updateEmployee(String subjectID, Employee changed) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Employee> updateEmployeeShiftPreference(String subjectID, String employeeID, Employee.ShiftDateTime shift, boolean canWork) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<List<Shift>> listShifts(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Shift> updateRequiredRoleInShift(String subjectId, Calendar date, Shift.Type type, Employee.Role role, int count) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously removeTrucking
    public Response<Delivery> deleteDelivery(String subjectID, int deliveryID) {
        Response<Employee> subject = employees.readEmployee(subjectID, subjectID);
        if (subject.isError()) {
            return new Response<>(subject.getErrorMessage());
        }
        if (subject.getValue().role != Employee.Role.TruckingManger) {
            return new Response<>("Subject must be of trucking manager role.");
        }
        return logistics.deleteDelivery(deliveryID);
    }

    // Previously printBoard
    public Response<List<Delivery>> listDeliveries(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously printBoardOfDriver, printTruckingsHistoryOfDriver and printFutureTruckingsOfDriver.
    // Both can be simplified to a single call and filter.
    public Response<List<Delivery>> listDeliveriesByDriver(String subjectID, String driverID) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously getVehiclesRegistrationPlates.
    public Response<List<String>> listVehicles(String subjectID) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously addTrucking.
    public Response<Delivery> createDelivery(String subjectID, String registrationPlateOfVehicle, Calendar date, String driverUsername, List<Site> sources, List<Site> destinations, List<Product> products, long durationInMinutes) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously printBoardOfVehicle.
    // Since it now returns a list, it can be used like printTruckingsHistoryOfVehicle and printFutureTruckingsOfVehicle by filtering.
    public Response<List<Delivery>> listDeliveriesWithVehicle(String subjectID, String registration) {
        throw new UnsupportedOperationException("TODO");
    }

    // Instead of:
    //   addProductToTrucking
    //   updateSourcesOnTrucking
    //   updateDestinationsOnTrucking
    //   moveProductsToTrucking
    //   updateVehicleOnTrucking
    //   updateDriverOnTrucking
    //   updateDateOnTrucking
    //   updateDateOnTrucking
    // Everything can be consistently done through a single method.
    public Response<Delivery> updateDelivery(String subjectID, Delivery updated) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously addVehicle.
    public Response createVehicle(String subjectID, Driver.License license, String registrationPlate, String model, int weight, int maxWeight) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Driver> createDriver(String subjectID, String employeeID, Set<Driver.License> licenses) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Driver> updateDriver(String subjectID, Driver updated) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously setWeightForTrucking.
    // Haven't touched this one because I believe it might do more than updateDelivery.
    public Response setWeightForDelivery(String subjectID, int deliveryID, int weight) {
        throw new UnsupportedOperationException("TODO");
    }
}
