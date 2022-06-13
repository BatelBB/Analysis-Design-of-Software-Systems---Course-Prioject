package groupk.inventory_suppliers.shared.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Yuval Dolev
 */
public class ClassContainer {
    private Map<Class<?>, Supplier<?>> suppliers;
    private Set<Class<?>> singletons;
    private Map<Class<?>, Object> singletonInstances;

    public ClassContainer() {
        suppliers = new HashMap<>();
        singletonInstances = new HashMap<>();
        singletons = new HashSet<>();
    }

    public boolean supports(Class<?> service) {
        return suppliers.containsKey(service);
    }

    public <T> T get(Class<T> service) {
        mustSupport(service);
        if(singletons.contains(service)) {
            if(!singletonInstances.containsKey(service)) {
                Object instance = suppliers.get(service).get();
                singletonInstances.put(service, instance);
            }
            return (T) singletonInstances.get(service);
        }
        return (T) suppliers.get(service).get();
    }

    public <TService, TImpl extends TService> void perRequest(Class<TService> serviceType, Supplier<TImpl> factory) {
        mustNotSupport(serviceType);
        suppliers.put(serviceType, factory);
    }

    public <TService, TImpl extends TService> void perRequest(Class<TService> serviceType, Class<TImpl> implType) {
        perRequest(serviceType, createSupplierFromConstructor(implType));
    }

    public <TService, TImpl extends TService> void singleton(Class<TService> serviceType, Supplier<TImpl> factory) {
        mustNotSupport(serviceType);
        suppliers.put(serviceType, factory);
        singletons.add(serviceType);
    }

    public <TService, TImpl extends TService> void singleton(Class<TService> serviceType, Class<TImpl> implType) {
        singleton(serviceType, createSupplierFromConstructor(implType));
    }

    public <T> void singleton(Class<T> serviceType) {
        singleton(serviceType, serviceType);
    }

    public <T> void perRequest(Class<T> serviceType) {
        perRequest(serviceType, serviceType);
    }

    private void mustSupport(Class<?> serviceType) {
        if(!supports(serviceType)) {
            throw new NoSuchElementException("This ClassContainer does not support type: " + serviceType.getName());
        }
    }

    private void mustNotSupport(Class<?> serviceType) {
        if(supports(serviceType)) {
            throw new RuntimeException("This ClassContainer already registered type: " + serviceType.getName());
        }
    }

    private <T> Supplier<T> createSupplierFromConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> single = null;
        for (Constructor<?> ctor: constructors) {
            int modifiers = ctor.getModifiers();
            if(Modifier.isPublic(modifiers)) {
                if(single == null) {
                    single = ctor;
                } else {
                    throw new RuntimeException("To be used like that in ClassContainer, "
                            + clazz.getName() +
                            "needs to have exactly one public constructor, but multiple found.");
                }
            }
        }
        if(single == null) {
            throw new RuntimeException("To be used like that in ClassContainer, "
                    + clazz.getName() +
                    " needs to have exactly one public constructor, but none found.");
        }

        Parameter[] parameters = single.getParameters();
        Constructor<?> finalSingle = single;
        return () -> {
            Object[] dependencies = new Object[parameters.length];
            for (int i = 0; i < dependencies.length; i++) {
                dependencies[i] = get(parameters[i].getType());
            }
            try {
                return (T) finalSingle.newInstance(dependencies);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}