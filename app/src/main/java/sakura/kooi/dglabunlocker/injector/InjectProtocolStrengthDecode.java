package sakura.kooi.dglabunlocker.injector;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookDeviceProtection;
import sakura.kooi.dglabunlocker.hooks.HookEnforceRemoteMaxStrength;

public class InjectProtocolStrengthDecode {
    public static void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.BlueToothService", classLoader, "a", int.class, int.class,
                new XC_MethodHook() {
                    private HookDeviceProtection hookDeviceProtection = new HookDeviceProtection();
                    private HookEnforceRemoteMaxStrength hookEnforceRemoteMaxStrength = new HookEnforceRemoteMaxStrength();

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if (GlobalVariables.isRemote.getBoolean(null)) {
                            try {
                                try {
                                    if (GlobalVariables.deviceProtection) {
                                        param.args[0] = hookDeviceProtection.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = hookDeviceProtection.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                } catch (Exception e) {
                                    Log.e("DgLabUnlocker", "An error occurred in deviceProtection", e);
                                }
                                try {
                                    if (GlobalVariables.enforceRemoteMaxStrength) {
                                        param.args[0] = hookEnforceRemoteMaxStrength.handleStrengthA(context, (Integer) param.args[0]);
                                        param.args[1] = hookEnforceRemoteMaxStrength.handleStrengthB(context, (Integer) param.args[1]);
                                    }
                                } catch (Exception e) {
                                    Log.e("DgLabUnlocker", "An error occurred in enforceRemoteMaxStrength", e);
                                }
                            } catch (Exception e) {
                                Log.e("DgLabUnlocker", "An error occurred", e);
                            }
                        }
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
