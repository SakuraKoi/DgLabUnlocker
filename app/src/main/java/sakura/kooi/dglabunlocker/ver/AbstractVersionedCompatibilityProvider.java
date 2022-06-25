package sakura.kooi.dglabunlocker.ver;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;

public abstract class AbstractVersionedCompatibilityProvider {
    private HashMap<String, Class<?>> classCache = new HashMap<>();
    private MethodHandles.Lookup lookup;
    private ClassLoader classLoader;

    protected abstract void initializeAccessors() throws ReflectiveOperationException;
    protected abstract void initializeNames();

    public void initializeAccessors(ClassLoader classLoader) throws ReflectiveOperationException {
        this.lookup = MethodHandles.lookup();
        this.classLoader = classLoader;
        initializeNames();
        initializeAccessors();
    }

    protected Class<?> lookupClass(String className) {
        return classCache.computeIfAbsent(className, className1 -> {
            try {
                return Class.forName(className1, true, classLoader);
            } catch (ClassNotFoundException e) {
                ModuleUtils.logError("DgLabUnlocker", "Failed to find class " + className1, e);
                return null;
            }
        });
    }

    protected <T> FieldAccessor<T> lookupField(String className, String fieldName) throws ReflectiveOperationException {
        Class<?> clazz = lookupClass(className);
        if (clazz != null) {
            return new FieldAccessor<>(lookup, clazz, fieldName);
        }
        return null;
    }
}
