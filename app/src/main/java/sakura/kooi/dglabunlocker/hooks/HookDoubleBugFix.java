package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookDoubleBugFix {
    public static void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        Class<?> globalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);
        Field baseChannelA = globalVariable.getDeclaredField("ab");
        Field totalA = globalVariable.getDeclaredField("V");
        Field remoteA = globalVariable.getDeclaredField("X");
        Field baseChannelB = globalVariable.getDeclaredField("ac");
        Field totalB = globalVariable.getDeclaredField("W");
        Field remoteB = globalVariable.getDeclaredField("Y");
        baseChannelA.setAccessible(true);
        baseChannelB.setAccessible(true);
        totalA.setAccessible(true);
        totalB.setAccessible(true);
        remoteA.setAccessible(true);
        remoteB.setAccessible(true);

        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.service.BlueToothService$18", classLoader, "a", byte[].class,
                new XC_MethodHook() {
                    private AtomicInteger lastStrengthA = new AtomicInteger();
                    private AtomicInteger lastStrengthB = new AtomicInteger();
                    
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            boolean fixed;

                            fixed = tryFixDouble(lastStrengthA, baseChannelA, totalA, remoteA);
                            fixed = fixed || tryFixDouble(lastStrengthB, baseChannelB, totalB, remoteB);

                            if (fixed) {
                                Toast.makeText(context, "拦截了一次翻倍BUG", Toast.LENGTH_SHORT).show();
                            }
                           /* int strengthA = baseChannelA.getInt(null);
                            int strengthB = baseChannelB.getInt(null);
                            Log.i("DgLabUnlocker", "Before base strength = " + strengthA + " / " + strengthB);*/
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "Cannot apply hook for progress bar ui", e);
                        }
                    }

                    private boolean tryFixDouble(AtomicInteger lastStrength, Field baseChannel, Field total, Field remote) throws ReflectiveOperationException {
                        if (remote.getInt(null) == 0) {
                            int base = baseChannel.getInt(null);
                            if (base > 10 && /*base != lastStrength.get() && */Math.abs(base - lastStrength.get()) > 1/* && base == total.getInt(null)*/) {
                                baseChannel.setInt(null, lastStrength.get());
                                return true;
                            } else {
                                lastStrength.set(base);
                            }
                        }
                        return false;
                    }
                });
    }
}