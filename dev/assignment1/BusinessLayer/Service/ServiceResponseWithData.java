package assignment1.BusinessLayer.Service;

public class ServiceResponseWithData<T> extends ServiceResponse {
    public final T data;

    ServiceResponseWithData(boolean success, String error, T data) {
        super(success, error);
        this.data = data;
    }

    static <T> ServiceResponseWithData<T> success(T data) {
        return new ServiceResponseWithData<>(true, null, data);
    }

    static <T> ServiceResponseWithData<T> error(String error) {
        return new ServiceResponseWithData<>(false, error, null);
    }
}
