package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookBypassRemoteMaxStrength;
import sakura.kooi.dglabunlocker.hooks.HookDeviceProtection;
import sakura.kooi.dglabunlocker.hooks.HookEnforceLocalStrength;
import sakura.kooi.dglabunlocker.hooks.HookEnforceRemoteMaxStrength;

public class InjectProtocolStrengthDecode implements IHookPointInjector {

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(GlobalVariables.classBluetoothService, classLoader, GlobalVariables.methodBluetoothServiceUpdateStrength, int.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        withCatch("InjectProtocolStrengthDecode", () -> {
                            if (GlobalVariables.isRemote.getBoolean(null)) {
                                withCatch("HookDeviceProtection", () -> {
                                    if (GlobalVariables.deviceProtection) {
                                        param.args[0] = HookDeviceProtection.INSTANCE.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = HookDeviceProtection.INSTANCE.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                });
                                withCatch("HookEnforceRemoteMaxStrength", () -> {
                                    if (GlobalVariables.enforceRemoteMaxStrength) {
                                        param.args[0] = HookEnforceRemoteMaxStrength.INSTANCE.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = HookEnforceRemoteMaxStrength.INSTANCE.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                });
                                withCatch("HookBypassRemoteMaxStrength", () -> {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                                });
                            }
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "Bluetooth sent " + param.args[0] + " " + param.args[1]);
                        withCatch("InjectProtocolStrengthDecode", () -> {
                            if (GlobalVariables.isRemote.getBoolean(null)) {
                                withCatch("HookBypassRemoteMaxStrength", () -> {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                                });
                            }
                                withCatch("HookEnforceLocalStrength", () -> {
                                    if (GlobalVariables.enforceLocalStrength)
                                        HookEnforceLocalStrength.INSTANCE.afterStrengthDecode(context);
                                });
                        });
                    }
                });
    }
}
