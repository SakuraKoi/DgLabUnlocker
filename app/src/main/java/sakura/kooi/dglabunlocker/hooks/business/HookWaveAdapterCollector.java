package sakura.kooi.dglabunlocker.hooks.business;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_MainListCustomizAdapter;

import android.content.Context;

import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;

public class HookWaveAdapterCollector extends AbstractHook<Object> {
    private static final WeakHashMap<Object, Void> adapters = new WeakHashMap<>();
    public HookWaveAdapterCollector() {
        super(Object.class);
    }

    @Override
    public String getName() {
        return "波形列表收集";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_1_3_2;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookConstructor(class_MainListCustomizAdapter, classLoader, List.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object adapter = param.thisObject;
                adapters.put(adapter, null);
            }
        });
    }

    public static Collection<Object> getAdapters() {
        return adapters.keySet();
    }
}
