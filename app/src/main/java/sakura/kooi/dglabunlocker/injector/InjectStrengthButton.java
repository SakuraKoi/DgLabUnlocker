package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.HookBypassRemoteMaxStrength;
import sakura.kooi.dglabunlocker.variables.InjectPoints;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class InjectStrengthButton implements IHookPointInjector {

    public void apply(Context context, ClassLoader classLoader) {
        InjectPoints.classTouchListeners.forEach(classTouchListener -> {
            XposedHelpers.findAndHookMethod(classTouchListener, classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (ModuleSettings.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.beforeStrength(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBypassRemoteMaxStrength", () -> {
                            if (ModuleSettings.bypassRemoteMaxStrength)
                                HookBypassRemoteMaxStrength.INSTANCE.afterStrength(context);
                        });
                    }
                });
        });
    }
}
