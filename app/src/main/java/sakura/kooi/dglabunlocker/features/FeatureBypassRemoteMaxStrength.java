package sakura.kooi.dglabunlocker.features;


import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthB;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.HookBluetoothStrengthSender;
import sakura.kooi.dglabunlocker.hooks.HookLongPressStrengthHandler;
import sakura.kooi.dglabunlocker.hooks.HookStrengthButton;
import sakura.kooi.dglabunlocker.hooks.HookStrengthText;

public class FeatureBypassRemoteMaxStrength extends AbstractFeature implements
        HookBluetoothStrengthSender.IBluetoothStrengthSendInterceptor,
        HookStrengthButton.IStrengthButtonInterceptor,
        HookLongPressStrengthHandler.IStrengthButtonInterceptor,
        HookStrengthText.StrengthTextHandler {
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
        return Arrays.asList(HookBluetoothStrengthSender.class, HookStrengthButton.class, HookLongPressStrengthHandler.class, HookStrengthText.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.CONTROLLER;
    }

    @Override
    public void updateFeatureStatus(boolean enabled) {

    }

    // helper method to prevent desync write (filter spoofed max strength)
    public void updateRealMax() throws ReflectiveOperationException {
        int maxA = maxStrengthA.get();
        if (maxA != 300)
            realMaxA.set(maxA);

        int maxB = maxStrengthB.get();
        if (maxB != 300)
            realMaxB.set(maxB);

        maxStrengthA.set(300);
        maxStrengthB.set(300);
    }

    @Override
    public void interceptBeforeStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException {
        updateRealMax();
    }

    @Override
    public void interceptAfterStrength(Context context, AtomicInteger strengthA, AtomicInteger strengthB) throws ReflectiveOperationException {
        maxStrengthA.set(realMaxA.get());
        maxStrengthB.set(realMaxB.get());
    }

    @Override
    public void beforeStrengthChange(Context context) throws ReflectiveOperationException {
        updateRealMax();
    }

    @Override
    public void afterStrengthChange(Context context) throws ReflectiveOperationException {
        maxStrengthA.set(realMaxA.get());
        maxStrengthB.set(realMaxB.get());
    }

    @Override
    public void interceptStrengthText(TextView textA, TextView textB) throws ReflectiveOperationException {
        boolean bypassedA = totalStrengthA.get() > localStrengthA.get() + Math.max(1, realMaxA.get());
        textA.setTextColor(bypassedA ? 0xffffa500 : 0xffffe99d);
        boolean bypassedB = totalStrengthB.get() > localStrengthB.get() + Math.max(1, realMaxB.get());
        textB.setTextColor(bypassedB ? 0xffffa500 : 0xffffe99d);
    }
}
