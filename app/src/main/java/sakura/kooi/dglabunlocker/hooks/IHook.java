package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;

public interface IHook {
    void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException;
}
