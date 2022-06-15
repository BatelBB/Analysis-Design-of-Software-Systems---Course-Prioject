package groupk.inventory_suppliers.shared.ioc;

public interface ServiceProvider {
    boolean supports(Class<?> service);

    <T> T get(Class<T> service);
}
