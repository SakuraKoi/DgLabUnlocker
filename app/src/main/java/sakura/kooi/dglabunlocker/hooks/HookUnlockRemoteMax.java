package sakura.kooi.dglabunlocker.hooks;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookUnlockRemoteMax {
    public static void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.RemoteSettingDialog", classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    Object thisObject = param.thisObject;
                    Field fieldChannelA = thisObject.getClass().getDeclaredField("a_channel_strength_range");
                    Field fieldChannelB = thisObject.getClass().getDeclaredField("b_channel_strength_range");
                    fieldChannelA.setAccessible(true);
                    fieldChannelB.setAccessible(true);

                    Object barChannelA = fieldChannelA.get(thisObject);
                    Object barChannelB = fieldChannelB.get(thisObject);
                    if (barChannelA == null || barChannelB == null) {
                        Log.e("DgLabUnlocker", "Cannot found progress bar ui");
                        return;
                    }
                    Method setMax = barChannelA.getClass().getDeclaredMethod("setMax", int.class);
                    setMax.setAccessible(true);
                    setMax.invoke(barChannelA, 276);
                    setMax.invoke(barChannelB, 276);
                    Toast.makeText(context, "远程最大强度解锁成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "Cannot apply hook for progress bar ui", e);
                }
            }
        });
    }
}
