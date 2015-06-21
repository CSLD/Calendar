package net.balhar.calendar.frontend.annotation;

import net.balhar.calendar.frontend.Provider;
import net.balhar.calendar.frontend.Unsupported;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Proxy to abstract controller from handling annotation based services.
 */
@Service
public class AnnotationProxy {
    private Method collection;
    private Method single;
    private Method create;
    private Method update;
    private Method delete;

    private Provider provider;

    @Autowired
    public AnnotationProxy(Provider provider) {
        this.provider = provider;
    }

    public Object collection(Object... args) {
        if (collection == null) {
            collection = retrieveMethod(ResourceCollection.class);
        }
        try {
            return collection.invoke(provider, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Unsupported();
        }
    }

    public Object single(Object... args) {
        if (single == null) {
            single = retrieveMethod(Resource.class);
        }
        try {
            return single.invoke(provider, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Unsupported();
        }
    }

    public Object create(Object... args) {
        if (create == null) {
            create = retrieveMethod(Create.class);
        }
        try {
            return create.invoke(provider, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Unsupported();
        }
    }

    public Object update(Object... args) {
        if (update == null) {
            update = retrieveMethod(Update.class);
        }
        try {
            return update.invoke(provider, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Unsupported();
        }
    }

    public Object delete(Object... args) {
        if (delete == null) {
            delete = retrieveMethod(Delete.class);
        }
        try {
            return delete.invoke(provider, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new Unsupported();
        }
    }

    private Method retrieveMethod(Class annotation) {
        Class<?> klass = provider.getClass();
        final List<Method> allMethods = new ArrayList<>(Arrays.asList(klass.getDeclaredMethods()));
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        throw new Unsupported();
    }
}
