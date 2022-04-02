package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.os.Message;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookBypassRemoteMaxStrength;

public class InjectStrengthLongPressHandler implements IHookPointInjector {
    @Override
    public void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.ui.activity.HomeActivity$1", classLoader,
                "handleMessage", Message.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        withCatch("InjectStrengthLongPressHandler", () -> {
                            Message msg = (Message) param.args[0];
                            if (msg != null) {
                                int what = msg.what;
                                if (what == 10 || what == 11) {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        withCatch("HookBypassRemoteMaxStrength", () -> HookBypassRemoteMaxStrength.INSTANCE.beforeStrengthA(context));
                                } else if (what == 12 || what == 13) {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        withCatch("HookBypassRemoteMaxStrength", () -> HookBypassRemoteMaxStrength.INSTANCE.beforeStrengthB(context));
                                }
                            }
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("InjectStrengthLongPressHandler", () -> {
                            Message msg = (Message) param.args[0];
                            if (msg != null) {
                                int what = msg.what;
                                if (what == 10 || what == 11) {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        withCatch("HookBypassRemoteMaxStrength", () -> HookBypassRemoteMaxStrength.INSTANCE.afterStrengthA(context));
                                } else if (what == 12 || what == 13) {
                                    if (GlobalVariables.bypassRemoteMaxStrength)
                                        withCatch("HookBypassRemoteMaxStrength", () -> HookBypassRemoteMaxStrength.INSTANCE.afterStrengthB(context));
                                }
                            }
                        });
                    }
                });
        // com.bjsm.dungeonlab.ui.activity.HomeActivity$1
        // public void handleMessage(Message var1) {
        //      int var2 = var1.what;
        // what = 10 11 12 13
    }
}
