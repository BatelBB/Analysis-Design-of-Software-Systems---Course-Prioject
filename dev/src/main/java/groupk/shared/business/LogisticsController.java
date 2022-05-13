package groupk.shared.business;

import groupk.shared.service.Response;
import groupk.shared.service.dto.Delivery;
import groupk.shared.service.dto.Driver;
import groupk.shared.service.dto.Product;
import groupk.shared.service.dto.Site;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class LogisticsController {
    // Previously removeTrucking
    public Response<Delivery> deleteDelivery(int deliveryID) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously printBoard
    public Response<List<Delivery>> listDeliveries() {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously printBoardOfDriver, printTruckingsHistoryOfDriver and printFutureTruckingsOfDriver.
    // Both can be simplified to a single call and filter.
    public Response<List<Delivery>> listDeliveriesByDriver(String driverID) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously getVehiclesRegistrationPlates.
    public Response<List<String>> listVehicles() {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously addTrucking.
    public Response<Delivery> createDelivery(String registrationPlateOfVehicle, Calendar date, String driverUsername, List<Site> sources, List<Site> destinations, List<Product> products, long durationInMinutes) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously printBoardOfVehicle.
    // Since it now returns a list, it can be used like printTruckingsHistoryOfVehicle and printFutureTruckingsOfVehicle by filtering.
    public Response<List<Delivery>> listDeliveriesWithVehicle(String registration) {
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
    public Response<Delivery> updateDelivery(Delivery updated) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously addVehicle.
    public Response createVehicle(Driver.License license, String registrationPlate, String model, int weight, int maxWeight) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Driver> createDriver(String employeeID, Set<Driver.License> licenses) {
        throw new UnsupportedOperationException("TODO");
    }

    public Response<Driver> updateDriver(Driver updated) {
        throw new UnsupportedOperationException("TODO");
    }

    // Previously setWeightForTrucking.
    // Haven't touched this one because I believe it might do more than updateDelivery.
    public Response setWeightForDelivery(int deliveryID, int weight) {
        throw new UnsupportedOperationException("TODO");
    }
}
