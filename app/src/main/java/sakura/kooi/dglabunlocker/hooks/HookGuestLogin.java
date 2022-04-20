package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.features.FeatureRandomQrCode;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class HookGuestLogin implements IHook {
    @Override
    public void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.d.n", classLoader,
                "b", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        if (ModuleSettings.randomQrCode)
                            withCatch("FeatureRandomQrCode", () -> {
                                String identifier = (String) param.args[0];
                                param.args[0] = FeatureRandomQrCode.INSTANCE.apply(identifier);
                            });
                    }
                });
    }
}
