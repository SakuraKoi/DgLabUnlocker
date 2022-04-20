package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.features.FeatureUnlockRemoteMax;
import sakura.kooi.dglabunlocker.variables.InjectPoints;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class HookRemoteSettingsDialog implements IHook {
    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_RemoteSettingDialog, classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        withCatch("FeatureUnlockRemoteMax", () -> {
                            if (ModuleSettings.unlockRemoteMaxStrength)
                                FeatureUnlockRemoteMax.INSTANCE.unlockRemoteMaxStrength(param, context);
                        });
                    }
                });
    }
}
