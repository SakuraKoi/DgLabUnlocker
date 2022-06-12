package sakura.kooi.dglabunlocker.features.toggleable;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.features.ToggleableFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.business.HookLongPressStrengthHandler;
import sakura.kooi.dglabunlocker.hooks.business.HookProtocolStrengthDecoder;
import sakura.kooi.dglabunlocker.hooks.business.HookStrengthButton;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureEnforceLocalStrength extends ToggleableFeature implements
        HookProtocolStrengthDecoder.IProtocolDecoderInterceptor,
        HookStrengthButton.IStrengthButtonInterceptor,
        HookLongPressStrengthHandler.IStrengthButtonInterceptor{
    private AtomicInteger localStrengthA = new AtomicInteger(10);
    private AtomicInteger localStrengthB = new AtomicInteger(10);

    @Override
    public String getSettingName() {
        return "被控 | 暴力锁死基础强度";
    }

    @Override
    public String getSettingDesc() {
        return "有效避免突然惨遭弹射起飞";
    }

    @Override
    public String getConfigurationKey() {
        return "FeatureEnforceLocalStrength";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookProtocolStrengthDecoder.class, HookStrengthButton.class, HookLongPressStrengthHandler.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.CONTROLLED;
    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    @Override
    public void beforeStrengthDecode(Context context) throws ReflectiveOperationException {}

    public void afterStrengthDecode(Context context) throws ReflectiveOperationException {
        Accessors.localStrengthA.set(localStrengthA.get());
        Accessors.localStrengthB.set(localStrengthB.get());
    }

    @Override
    public void beforeStrengthChange(Context context) throws ReflectiveOperationException {}

    @Override
    public void afterStrengthChange(Context context) throws ReflectiveOperationException {
        this.localStrengthA.set(Accessors.localStrengthA.get());
        this.localStrengthB.set(Accessors.localStrengthB.get());
    }
}
