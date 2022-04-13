package sakura.kooi.dglabunlocker.injector;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.hooks.HookEnforceLocalStrength;

public class InjectControlledStrengthButton implements IHookPointInjector {

    public void apply(Context context, ClassLoader classLoader) {
            XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.RemotelyService2", classLoader,
                    "a", int.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            AtomicInteger localStrengthA = new AtomicInteger((int)param.args[0]);
                            AtomicInteger localStrengthB = new AtomicInteger((int)param.args[1]);
                            withCatch("HookEnforceLocalStrength", () -> {
                                if (GlobalVariables.enforceLocalStrength)
                                    HookEnforceLocalStrength.INSTANCE.handleLocalStrengthChange(context, localStrengthA, localStrengthB);
                            });
                            param.args[0] = localStrengthA.get();
                            param.args[1] = localStrengthB.get();
                        }
                    });
    }
}
