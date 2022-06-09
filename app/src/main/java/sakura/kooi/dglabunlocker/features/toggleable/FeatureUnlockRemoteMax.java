package sakura.kooi.dglabunlocker.features.toggleable;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import sakura.kooi.dglabunlocker.features.ToggleableFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.business.HookRemoteSettingsDialog;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class FeatureUnlockRemoteMax extends ToggleableFeature implements HookRemoteSettingsDialog.IRemoteSettingDialogUiInterceptor {
    @Override
    public String getSettingName() {
        return "被控 | 解锁远程强度上限";
    }

    @Override
    public String getSettingDesc() {
        return "最高100完全不够用好吧";
    }

    @Override
    public String getConfigurationKey() {
        return "FeatureUnlockRemoteMax";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookRemoteSettingsDialog.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.ALL;
    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    @Override
    public void interceptUiElements(Context context, Object thisObject) throws ReflectiveOperationException {
        Field fieldChannelA = thisObject.getClass().getDeclaredField(InjectPoints.field_RemoteSettingDialog_strengthA);
        Field fieldChannelB = thisObject.getClass().getDeclaredField(InjectPoints.field_RemoteSettingDialog_strengthB);
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
    }
}
