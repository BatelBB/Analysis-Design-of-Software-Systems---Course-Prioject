package adss_group_k.shared.response;

import java.util.function.Function;

public class ResponseT<T> extends Response {
    public final T data;

    public ResponseT(boolean success, String error, T data) {
        super(success, error);
        this.data = data;
    }

    public static <T> ResponseT<T> success(T data) {
        return new ResponseT<>(true, null, data);
    }

    public static <T> ResponseT<T> error(String error) {
        return new ResponseT<>(false, error, null);
    }

    public <TEx extends Throwable> T getOrThrow(Function<String, TEx> ifError) throws TEx {
        if(success) return data;
        throw ifError.apply(error);
    }

    public <R> ResponseT<R> castUnchecked() {
        return (ResponseT<R>) this;
    }
}
