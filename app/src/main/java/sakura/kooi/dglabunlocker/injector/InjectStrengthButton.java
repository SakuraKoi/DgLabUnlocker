package sakura.kooi.dglabunlocker.injector;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookBypassRemoteMaxStrength;

public class InjectStrengthButton {
    public static void apply(Context context, ClassLoader classLoader) {
        HookBypassRemoteMaxStrength hookBypassRemoteMaxStrength = new HookBypassRemoteMaxStrength();
        // Add A
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$15", classLoader, "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.beforeStrengthA(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength A", e);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.afterStrengthA(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength A", e);
                }
            }
        });
        // Add B
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$16", classLoader, "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.beforeStrengthB(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength B", e);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.afterStrengthB(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength B", e);
                }
            }
        });
        // Sub A
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$17", classLoader, "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.beforeStrengthA(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength A", e);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.afterStrengthA(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength A", e);
                }
            }
        });
        // Sub B
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$18", classLoader, "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.beforeStrengthB(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength B", e);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    if (GlobalVariables.bypassRemoteMaxStrength)
                        hookBypassRemoteMaxStrength.afterStrengthB(context);
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in bypassRemoteMaxStrength B", e);
                }
            }
        });
    }

    public interface StrengthAddHandler {
        void beforeStrengthA(Context context) throws IllegalAccessException;

        void beforeStrengthB(Context context) throws IllegalAccessException;

        void afterStrengthA(Context context) throws IllegalAccessException;

        void afterStrengthB(Context context) throws IllegalAccessException;
    }
}
