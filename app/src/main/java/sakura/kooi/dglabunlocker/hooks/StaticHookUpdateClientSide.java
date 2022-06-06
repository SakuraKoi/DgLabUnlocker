package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothService;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothService_updateClientSide;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class StaticHookUpdateClientSide extends AbstractHook<Object> {
    public StaticHookUpdateClientSide() {
        super(Object.class);
    }

    @Override
    public String getName() {
        return "连接状态监听";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_ANY;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod(class_BluetoothService, classLoader,
                method_BluetoothService_updateClientSide, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    Accessors.isRemote = !Objects.equals(param.args[0], 0);
                }
            });
    }
}
