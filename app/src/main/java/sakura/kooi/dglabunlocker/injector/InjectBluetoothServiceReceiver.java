package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthB;
import static sakura.kooi.dglabunlocker.GlobalVariables.remoteStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.remoteStrengthB;
import static sakura.kooi.dglabunlocker.GlobalVariables.totalStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.totalStrengthB;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookDoubleBugFix;

public class InjectBluetoothServiceReceiver {
    public static void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.BlueToothService$18", classLoader, "a", byte[].class,
                new XC_MethodHook() {;
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "BeforeDataUpdate -> A local = " + localStrengthA.getInt(null) +
                                " total = " + totalStrengthA.getInt(null) +
                                " remote = " + remoteStrengthA.getInt(null) +
                                " | B local = " + localStrengthB.getInt(null) +
                                " total = " + totalStrengthB.getInt(null) +
                                " remote = " + remoteStrengthB.getInt(null));

                        if (GlobalVariables.fixDoubleBug)
                            HookDoubleBugFix.beforeDataUpdate();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "AfterDataUpdate -> A local = " + localStrengthA.getInt(null) +
                                " total = " + totalStrengthA.getInt(null) +
                                " remote = " + remoteStrengthA.getInt(null) +
                                " | B local = " + localStrengthB.getInt(null) +
                                " total = " + totalStrengthB.getInt(null) +
                                " remote = " + remoteStrengthB.getInt(null));

                        if (GlobalVariables.fixDoubleBug)
                            HookDoubleBugFix.afterDataUpdate(context);
                    }
                });
    }
}
