package sakura.kooi.dglabunlocker.injector;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookUnlockRemoteMax;

public class InjectRemoteSettingsDialog {
    public static void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.RemoteSettingDialog", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.unlockRemoteMaxStrength)
                        HookUnlockRemoteMax.unlockRemoteMaxStrength(param, context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in unlockRemoteMaxStrength", e);
                }
            }
        });
    }
}
