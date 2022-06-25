package sakura.kooi.dglabunlocker.hooks.business;

import static sakura.kooi.dglabunlocker.utils.DgLabVersion.V_ANY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public class HookCurrentActivity extends AbstractHook<Object> {
    private static volatile Activity _currentActivity = null;

    public HookCurrentActivity() {
        super(Object.class);
    }

    @Override
    public String getName() {
        return "当前窗口捕获";
    }

    @Override
    public int getMinVersion() {
        return V_ANY;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("android.app.Instrumentation", classLoader, "newActivity",
                ClassLoader.class, String.class, Intent.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        _currentActivity = (Activity) param.getResult();
                    }
                });
    }

    public static Activity getCurrentActivity() {
        return _currentActivity;
    }
}
