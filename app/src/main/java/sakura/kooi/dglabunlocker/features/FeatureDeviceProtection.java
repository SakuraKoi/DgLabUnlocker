package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.HookBluetoothStrengthSender;

public class FeatureDeviceProtection extends AbstractFeature implements HookBluetoothStrengthSender.IBluetoothStrengthSendInterceptor {
    @Override
    public String getSettingName() {
        return "被控 | 屏蔽非法超高强度";
    }

    @Override
    public String getSettingDesc() {
        return "防止恶意用户烧掉你的设备";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookBluetoothStrengthSender.class);
    }

    @Override
    public boolean isUnsupported() {
        return false;
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.CONTROLLED;
    }

    @Override
    public void initializeAndTest() throws Exception {

    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    @Override
    public void interceptBeforeStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) {
        if (strengthA.get() > 306) {
            strengthA.set(306);
            Log.e("DgLabUnlocker", "Protected: too high strength at channel A");
        }
        if (strengthB.get() > 306) {
            strengthB.set(306);
            Log.e("DgLabUnlocker", "Protected: too high strength at channel B");
        }
    }

    @Override
    public void interceptAfterStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) {

    }
}
