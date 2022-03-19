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
                    private AtomicInteger lastStrengthA = new AtomicInteger(0);
                    private AtomicInteger lastStrengthB = new AtomicInteger(0);

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        lastStrengthA.set(baseChannelA.getInt(null));
                        lastStrengthB.set(baseChannelB.getInt(null));

                        Log.d("DgLabUnlocker", "Before A base = " + lastStrengthA.get() +
                                " total = " + totalA.getInt(null) +
                                " remote = " + remoteA.getInt(null) +
                                " | B base = " + lastStrengthB.get() +
                                " total = " + totalB.getInt(null) +
                                " remote = " + remoteB.getInt(null));
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("DgLabUnlocker", "After A base = " + baseChannelA.getInt(null) +
                                " total = " + totalA.getInt(null) +
                                " remote = " + remoteA.getInt(null) +
                                " | B base = " + baseChannelB.getInt(null) +
                                " total = " + totalB.getInt(null) +
                                " remote = " + remoteB.getInt(null));

                        try {
                            boolean fixed;

                            fixed = tryFixDouble(lastStrengthA, baseChannelA, totalA, remoteA);
                            fixed = fixed || tryFixDouble(lastStrengthB, baseChannelB, totalB, remoteB);

                            if (fixed) {
                                Toast.makeText(context, "拦截了一次强度翻倍BUG", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("DgLabUnlocker", "Cannot apply hook for double bug fix", e);
                        }
                    }

                    private boolean tryFixDouble(AtomicInteger lastStrength, Field baseChannel, Field total, Field remote) throws ReflectiveOperationException {
                        if (remote.getInt(null) == 0) {
                            int base = baseChannel.getInt(null);
                            if (base > 10 && /*base != lastStrength.get() && */Math.abs(base - lastStrength.get()) > 1/* && base == total.getInt(null)*/) {
                                baseChannel.setInt(null, lastStrength.get());
                                Log.e("DgLabUnlocker", "Double bug blocked, set to " + lastStrength.get());
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }
}