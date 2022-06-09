package sakura.kooi.dglabunlocker.hooks.preload;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothService;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothService_updateClientSide;

import android.content.Context;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class HookUpdateClientSide extends AbstractHook<Object> {
    public HookUpdateClientSide() {
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
