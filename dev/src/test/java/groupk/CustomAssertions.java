package groupk;

import groupk.shared.business.Facade;
import groupk.shared.service.Response;
import groupk.shared.service.ServiceBase;

import static org.junit.jupiter.api.Assertions.*;
public class CustomAssertions {
    public static <T> T assertSuccess(Response<T> response) {
        assertFalse(response.isError(), response.getErrorMessage());
        return response.getValue();
    }

    public static <T> T assertSuccess(Facade.ResponseT<T> response) {
        assertTrue(response.success, response.error);
        return response.data;
    }

    public static void assertSuccess(Facade.SI_Response response) {
        assertTrue(response.success, response.error);
    }

    public static void assertSuccess(ServiceBase.Response response) {
        assertTrue(response.success, response.error);
    }
}
