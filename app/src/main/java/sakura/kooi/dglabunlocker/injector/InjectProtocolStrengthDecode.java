package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookDeviceProtection;
import sakura.kooi.dglabunlocker.hooks.HookEnforceRemoteMaxStrength;

public class InjectProtocolStrengthDecode implements IHookPointInjector {
    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.BlueToothService", classLoader, "a", int.class, int.class,
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
                            }
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "Bluetooth sent " + param.args[0] + " " + param.args[1]);
                    }
                });
    }

    public interface ProtocolStrengthHandler {
        int handleStrengthA(Context context, int strength) throws ReflectiveOperationException;

        int handleStrengthB(Context context, int strength) throws ReflectiveOperationException;
    }
}
