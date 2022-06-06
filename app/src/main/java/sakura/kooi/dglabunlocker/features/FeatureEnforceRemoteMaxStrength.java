package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.HookBluetoothStrengthSender;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureEnforceRemoteMaxStrength extends AbstractFeature implements HookBluetoothStrengthSender.IBluetoothStrengthSendInterceptor {
    @Override
    public String getSettingName() {
        return "被控 | 强制限制远程强度";
    }

    @Override
    public String getSettingDesc() {
        return "下面那个功能的防御";
    }

    @Override
    public String getConfigurationKey() {
        return "FeatureEnforceRemoteMaxStrength";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookBluetoothStrengthSender.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.CONTROLLED;
    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    @Override
    public void interceptBeforeStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException {
        int maxA = Accessors.maxStrengthA.get() + 30;
        int maxB = Accessors.maxStrengthB.get() + 30;
        boolean limited = false;
        if (strengthA.get() > maxA) {
            Log.w("DgLabUnlocker", "Enforced max strength A: " + strengthA.get() + " > " + maxA);
            strengthA.set(maxA);
            limited = true;
        }
        if (strengthB.get() > maxB) {
            Log.w("DgLabUnlocker", "Enforced max strength B: " + strengthB.get() + " > " + maxB);
            strengthB.set(maxB);
            limited = true;
        }
        if (limited)
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void interceptAfterStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) {

    }
}
