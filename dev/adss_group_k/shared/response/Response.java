package adss_group_k.shared.response;

import adss_group_k.BusinessLayer.Suppliers.BusinessLogicException;

public class Response {
    public final boolean success;
    public final String error;

    Response(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    void throwIfErroneous() throws BusinessLogicException {
        if(!success) {
            throw new BusinessLogicException(error);
        }
    }

    void throwRuntimeIfErroneous() {
        if(!success) {
            throw new RuntimeException(new BusinessLogicException(error));
        }
    }
}
