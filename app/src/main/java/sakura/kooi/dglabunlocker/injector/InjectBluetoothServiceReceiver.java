package sakura.kooi.dglabunlocker.injector;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookDoubleBugFix;

public class InjectBluetoothServiceReceiver {
    public static void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.BlueToothService$18", classLoader, "a", byte[].class,
                new XC_MethodHook() {
                    private HookDoubleBugFix hookDoubleBugFix = new HookDoubleBugFix();

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            int localStrengthA = GlobalVariables.localStrengthA.getInt(null);
                            int totalStrengthA = GlobalVariables.totalStrengthA.getInt(null);
                            int remoteStrengthA = GlobalVariables.remoteStrengthA.getInt(null);
                            int localStrengthB = GlobalVariables.localStrengthB.getInt(null);
                            int totalStrengthB = GlobalVariables.totalStrengthB.getInt(null);
                            int remoteStrengthB = GlobalVariables.remoteStrengthB.getInt(null);

                            Log.d("DgLabUnlocker", "BeforeDataUpdate -> A local = " + localStrengthA +
                                    " total = " + totalStrengthA +
                                    " remote = " + remoteStrengthA +
                                    " | B local = " + localStrengthB +
                                    " total = " + totalStrengthB +
                                    " remote = " + remoteStrengthB);

                            try {
                                if (GlobalVariables.fixDoubleBug)
                                    hookDoubleBugFix.beforeDataUpdate(context,
                                            localStrengthA, totalStrengthA, remoteStrengthA,
                                            localStrengthB, totalStrengthB, remoteStrengthB);
                            } catch (Exception e) {
                                Log.e("DgLabUnlocker", "An error occurred in fixDoubleBug", e);
                            }
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "An error occurred", e);
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            int localStrengthA = GlobalVariables.localStrengthA.getInt(null);
                            int totalStrengthA = GlobalVariables.totalStrengthA.getInt(null);
                            int remoteStrengthA = GlobalVariables.remoteStrengthA.getInt(null);
                            int localStrengthB = GlobalVariables.localStrengthB.getInt(null);
                            int totalStrengthB = GlobalVariables.totalStrengthB.getInt(null);
                            int remoteStrengthB = GlobalVariables.remoteStrengthB.getInt(null);

                            Log.d("DgLabUnlocker", "AfterDataUpdate -> A local = " + localStrengthA +
                                    " total = " + totalStrengthA +
                                    " remote = " + remoteStrengthA +
                                    " | B local = " + localStrengthB +
                                    " total = " + totalStrengthB +
                                    " remote = " + remoteStrengthB);

                            try {
                                if (GlobalVariables.fixDoubleBug)
                                    hookDoubleBugFix.afterDataUpdate(context,
                                            localStrengthA, totalStrengthA, remoteStrengthA,
                                            localStrengthB, totalStrengthB, remoteStrengthB);
                            } catch (Exception e) {
                                Log.e("DgLabUnlocker", "An error occurred in fixDoubleBug", e);
                            }
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "An error occurred", e);
                        }
                    }
                });
    }

    public interface BluetoothServiceDataHandler {
        void beforeDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException;
        void afterDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException;
    }
}
