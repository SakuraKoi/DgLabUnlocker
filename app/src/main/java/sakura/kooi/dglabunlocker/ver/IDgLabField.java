package sakura.kooi.dglabunlocker.ver;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

public interface IDgLabField {
    void initDgLabFields(ClassLoader classLoader, Context context) throws ReflectiveOperationException;

    default Field lookupField(Class<?> clazz, String name) throws ReflectiveOperationException {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException e) {
            Log.e("DgLabUnlocker", "Failed lookup field " + clazz.getName() + " : " + name, e);
            throw e;
        }
    }
}
