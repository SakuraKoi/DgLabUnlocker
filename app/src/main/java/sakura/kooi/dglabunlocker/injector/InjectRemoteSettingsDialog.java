package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.HookUnlockRemoteMax;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class InjectRemoteSettingsDialog implements IHookPointInjector {
    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.RemoteSettingDialog", classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        withCatch("HookUnlockRemoteMax", () -> {
                            if (ModuleSettings.unlockRemoteMaxStrength)
                                HookUnlockRemoteMax.INSTANCE.unlockRemoteMaxStrength(param, context);
                        });
                    }
                });
    }
}
