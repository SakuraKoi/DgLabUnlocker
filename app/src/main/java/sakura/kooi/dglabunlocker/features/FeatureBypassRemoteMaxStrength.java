package sakura.kooi.dglabunlocker.features;


import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthB;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.HookBluetoothStrengthSender;
import sakura.kooi.dglabunlocker.hooks.HookStrengthButton;

public class FeatureBypassRemoteMaxStrength extends AbstractFeature implements HookBluetoothStrengthSender.IBluetoothStrengthSendInterceptor, HookStrengthButton.IStrengthButtonInterceptor {
    private final AtomicInteger realMaxA = new AtomicInteger();
    private final AtomicInteger realMaxB = new AtomicInteger();

    @Override
    public String getSettingName() {
        return "主控 | 无视强度上限设置";
    }

    @Override
    public String getSettingDesc() {
        return "想拉多高拉多高 (坏.jpg";
    }

    @Override
    public String getConfigurationKey() {
        return "FeatureBypassRemoteMaxStrength";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookBluetoothStrengthSender.class, HookStrengthButton.class);
    }

    @Override
    public boolean isUnsupported() {
        return false;
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.CONTROLLER;
    }

    @Override
    public void initializeAndTest() throws Exception {

    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    @Override
    public void interceptBeforeStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException {
            realMaxA.set(maxStrengthA.get());
            realMaxB.set(maxStrengthB.get());
            maxStrengthA.set(276);
            maxStrengthB.set(276);
    }

    @Override
    public void interceptAfterStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException {
        boolean bypassedA = totalStrengthA.get() >= localStrengthA.get() + realMaxA.get();
        boolean bypassedB = totalStrengthB.get() >= localStrengthB.get() + realMaxB.get();

        if (bypassedA || bypassedB) {
            StringBuilder sb = new StringBuilder();
            sb.append("绕过远程最大强度");
            if (bypassedA)
                sb.append(" A: ").append(realMaxA.get());
            if (bypassedB)
                sb.append(" B: ").append(realMaxB.get());
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        }

        maxStrengthA.set(realMaxA.get());
        maxStrengthB.set(realMaxB.get());
    }

    @Override
    public void beforeStrengthChange(Context context) throws ReflectiveOperationException {
        realMaxA.set(maxStrengthA.get());
        realMaxB.set(maxStrengthB.get());
        maxStrengthA.set(276);
        maxStrengthB.set(276);
    }

    @Override
    public void afterStrengthChange(Context context) throws ReflectiveOperationException {
        maxStrengthA.set(realMaxA.get());
        maxStrengthB.set(realMaxB.get());
    }
}
