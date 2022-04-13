package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.HookDoubleBugFix;
import sakura.kooi.dglabunlocker.variables.Accessors;
import sakura.kooi.dglabunlocker.variables.InjectPoints;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class InjectBluetoothServiceReceiver implements IHookPointInjector {

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.classBluetoothServiceDecoder, classLoader, InjectPoints.methodBluetoothServiceDecoder_Decode, byte[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        try {
                            // region Read strength field and print log
                            int localStrengthA = Accessors.localStrengthA.get();
                            int totalStrengthA = Accessors.totalStrengthA.get();
                            int remoteStrengthA = Accessors.remoteStrengthA.get();
                            int localStrengthB = Accessors.localStrengthB.get();
                            int totalStrengthB = Accessors.totalStrengthB.get();
                            int remoteStrengthB = Accessors.remoteStrengthB.get();

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
                            int localStrengthA = Accessors.localStrengthA.get();
                            int totalStrengthA = Accessors.totalStrengthA.get();
                            int remoteStrengthA = Accessors.remoteStrengthA.get();
                            int localStrengthB = Accessors.localStrengthB.get();
                            int totalStrengthB = Accessors.totalStrengthB.get();
                            int remoteStrengthB = Accessors.remoteStrengthB.get();

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

}
