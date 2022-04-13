package sakura.kooi.dglabunlocker.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class FieldAccessor<T> {
    private MethodHandle setter;
    private MethodHandle getter;

    public FieldAccessor(MethodHandles.Lookup lookup, Class<?> owner, boolean isStatic, String fieldName, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        if (isStatic) {
            getter = lookup.findStaticGetter(owner, fieldName, type);
            setter = lookup.findStaticSetter(owner, fieldName, type);
        } else {
            getter = lookup.findGetter(owner, fieldName, type);
            setter = lookup.findSetter(owner, fieldName, type);
        }
    }

    public FieldAccessor(MethodHandles.Lookup lookup, Class<?> owner, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = owner.getDeclaredField(fieldName);
        field.setAccessible(true);
        getter = lookup.unreflectGetter(field);
        setter = lookup.unreflectSetter(field);
    }

    public FieldAccessor(MethodHandles.Lookup lookup, Field field) throws IllegalAccessException {
        getter = lookup.unreflectGetter(field);
        setter = lookup.unreflectSetter(field);
    }

    public void set(T data) throws ReflectiveOperationException {
        try {
            setter.invoke(data);
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }

    public void set(Object instance, T data) throws ReflectiveOperationException {
        try {
            setter.invoke(instance, data);
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }

    public T get() throws ReflectiveOperationException {
        try {
            return (T) getter.invoke();
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }

    public T get(Object instance) throws ReflectiveOperationException {
        try {
            return (T) getter.invoke(instance);
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }
}
