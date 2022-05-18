package sakura.kooi.dglabunlocker.features;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.HookControlledStrengthButton;
import sakura.kooi.dglabunlocker.hooks.HookProtocolStrengthDecoder;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureEnforceLocalStrength extends AbstractFeature implements HookProtocolStrengthDecoder.IProtocolDecoderInterceptor, HookControlledStrengthButton.ILocalStrengthInterceptor {
    private AtomicInteger localStrengthA = new AtomicInteger();
    private AtomicInteger localStrengthB = new AtomicInteger();

    @Override
    public String getSettingName() {
        return "被控 | 暴力锁死基础强度";
    }

    @Override
    public String getSettingDesc() {
        return "有效避免突然惨遭弹射起飞";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookProtocolStrengthDecoder.class, HookControlledStrengthButton.class);
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
    public void handleLocalStrengthChanged(Context context) throws ReflectiveOperationException {
        this.localStrengthA.set(Accessors.localStrengthA.get());
        this.localStrengthB.set(Accessors.localStrengthB.get());
    }

    @Override
    public void beforeStrengthDecode(Context context) throws ReflectiveOperationException {

    }

    public void afterStrengthDecode(Context context) throws ReflectiveOperationException {
        Accessors.localStrengthA.set(localStrengthA.get());
        Accessors.localStrengthB.set(localStrengthB.get());
    }
}
