package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookBypassRemoteMaxStrength;

public class InjectStrengthButton implements IHookPointInjector {
    public void apply(Context context, ClassLoader classLoader) {
        // Add A
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$15", classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                        });
                    }
                });
        // Add B
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$16", classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                        });
                    }
                });
        // Sub A
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$17", classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                        });
                    }
                });
        // Sub B
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$18", classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (GlobalVariables.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                        });
                    }
                });
    }
}
