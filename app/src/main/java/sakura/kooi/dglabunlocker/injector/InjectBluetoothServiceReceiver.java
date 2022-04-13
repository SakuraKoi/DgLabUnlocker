package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookDoubleBugFix;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class InjectBluetoothServiceReceiver implements IHookPointInjector {

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(GlobalVariables.classBluetoothServiceDecoder, classLoader, GlobalVariables.methodBluetoothServiceDecoder_Decode, byte[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            // region Read strength field and print log
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
                            // endregion

                            if (ModuleSettings.fixDoubleBug)
                                withCatch("HookDoubleBugFix", () -> HookDoubleBugFix.INSTANCE.beforeDataUpdate(context,
                                        localStrengthA, totalStrengthA, remoteStrengthA,
                                        localStrengthB, totalStrengthB, remoteStrengthB));
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "An error occurred", e);
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            // region Read strength field and print log
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
                            // endregion

                            if (ModuleSettings.fixDoubleBug)
                                withCatch("HookDoubleBugFix", () -> HookDoubleBugFix.INSTANCE.afterDataUpdate(context,
                                        localStrengthA, totalStrengthA, remoteStrengthA,
                                        localStrengthB, totalStrengthB, remoteStrengthB));
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "An error occurred", e);
                        }
                    }
                });
    }

    public interface BluetoothServiceDataHandler {
        void beforeDataUpdate(Context context,
                              int localStrengthA, int totalStrengthA, int remoteStrengthA,
                              int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException;

        void afterDataUpdate(Context context,
                             int localStrengthA, int totalStrengthA, int remoteStrengthA,
                             int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException;
    }
}
