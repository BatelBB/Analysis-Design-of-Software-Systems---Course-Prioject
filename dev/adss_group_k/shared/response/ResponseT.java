package adss_group_k.shared.response;

public class ResponseT<T> extends Response {
    public final T data;

    ResponseT(boolean success, String error, T data) {
        super(success, error);
        this.data = data;
    }

    public static <T> ResponseT<T> success(T data) {
        return new ResponseT<>(true, null, data);
    }

    public static <T> ResponseT<T> error(String error) {
        return new ResponseT<>(false, error, null);
    }
}
