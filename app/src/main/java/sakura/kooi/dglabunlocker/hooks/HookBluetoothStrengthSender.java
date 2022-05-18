package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookBluetoothStrengthSender extends AbstractHook<HookBluetoothStrengthSender.IBluetoothStrengthSendInterceptor> {
    public HookBluetoothStrengthSender() {
        super(IBluetoothStrengthSendInterceptor.class);
    }

    @Override
    public String getName() {
        return "蓝牙强度发送";
    }

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_BluetoothService, classLoader, InjectPoints.method_BluetoothService_updateStrength, int.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        AtomicInteger strengthA = new AtomicInteger((int) param.args[0]);
                        AtomicInteger strengthB = new AtomicInteger((int) param.args[1]);

                        callHandlers(handler -> {
                            handler.interceptBeforeStrength(context, strengthA, strengthB);
                        });

                        param.args[0] = strengthA.get();
                        param.args[1] = strengthB.get();
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        AtomicInteger strengthA = new AtomicInteger((int) param.args[0]);
                        AtomicInteger strengthB = new AtomicInteger((int) param.args[1]);

                        callHandlers(handler -> {
                            handler.interceptAfterStrength(context, strengthA, strengthB);
                        });

                        param.args[0] = strengthA.get();
                        param.args[1] = strengthB.get();
                        Log.d("DgLabUnlocker", "Bluetooth sent strength " + param.args[0] + " " + param.args[1]);
                    }
                });
    }

    public interface IBluetoothStrengthSendInterceptor {
        void interceptBeforeStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException;
        void interceptAfterStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException;
    }
}
