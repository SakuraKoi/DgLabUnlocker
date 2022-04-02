package sakura.kooi.dglabunlocker.injector;

import android.content.Context;

public interface IHookPointInjector {
    void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException;
}
