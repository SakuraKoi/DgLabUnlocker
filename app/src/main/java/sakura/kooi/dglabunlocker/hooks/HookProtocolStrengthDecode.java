package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.features.FeatureBypassRemoteMaxStrength;
import sakura.kooi.dglabunlocker.features.FeatureDeviceProtection;
import sakura.kooi.dglabunlocker.features.FeatureEnforceLocalStrength;
import sakura.kooi.dglabunlocker.features.FeatureEnforceRemoteMaxStrength;
import sakura.kooi.dglabunlocker.variables.Accessors;
import sakura.kooi.dglabunlocker.variables.InjectPoints;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class HookProtocolStrengthDecode implements IHook {

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_BluetoothService, classLoader, InjectPoints.method_BluetoothService_updateStrength, int.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        withCatch("HookProtocolStrengthDecode", () -> {
                            if (Accessors.isRemote.get()) {
                                withCatch("FeatureDeviceProtection", () -> {
                                    if (ModuleSettings.deviceProtection) {
                                        param.args[0] = FeatureDeviceProtection.INSTANCE.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = FeatureDeviceProtection.INSTANCE.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                });
                                withCatch("FeatureEnforceRemoteMaxStrength", () -> {
                                    if (ModuleSettings.enforceRemoteMaxStrength) {
                                        param.args[0] = FeatureEnforceRemoteMaxStrength.INSTANCE.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = FeatureEnforceRemoteMaxStrength.INSTANCE.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                });
                                withCatch("FeatureBypassRemoteMaxStrength", () -> {
                                    if (ModuleSettings.bypassRemoteMaxStrength)
                                        FeatureBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                                });
                            }
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "Bluetooth sent " + param.args[0] + " " + param.args[1]);
                        withCatch("HookProtocolStrengthDecode", () -> {
                            if (Accessors.isRemote.get()) {
                                withCatch("FeatureBypassRemoteMaxStrength", () -> {
                                    if (ModuleSettings.bypassRemoteMaxStrength)
                                        FeatureBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                                });
                            }
                                withCatch("FeatureEnforceLocalStrength", () -> {
                                    if (ModuleSettings.enforceLocalStrength)
                                        FeatureEnforceLocalStrength.INSTANCE.afterStrengthDecode(context);
                                });
                        });
                    }
                });
    }
}
