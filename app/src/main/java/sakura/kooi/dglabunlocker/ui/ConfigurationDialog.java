package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.function.Consumer;

import sakura.kooi.dglabunlocker.XposedModuleInit;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;
import sakura.kooi.dglabunlocker.variables.ResourceInject;

public class ConfigurationDialog {
    private static void createSettingSwitches(LinearLayout container) {
        if (XposedModuleInit.ENABLE_DEV_FEATURE) // 目前还没用
            createSwitch(container, "被控 | 游客随机二维码", "每次进游客都会刷一个新的二维码",
                "randomQrCode", val -> ModuleSettings.randomQrCode = val);

        createSwitch(container, "被控 | 屏蔽非法超高强度", "防止恶意用户烧掉你的设备",
                "deviceProtection", val -> ModuleSettings.deviceProtection = val);
        createSwitch(container, "被控 | 解锁远程强度上限", "最高100完全不够用好吧",
                "unlockRemoteMaxStrength", val -> ModuleSettings.unlockRemoteMaxStrength = val);
        createSwitch(container, "被控 | 暴力锁死基础强度", "有效避免突然惨遭弹射起飞",
                "enforceLocalStrength", val -> ModuleSettings.enforceLocalStrength = val);
        createSwitch(container, "被控 | 强制限制远程强度", "下面那个功能的防御",
                "enforceRemoteMaxStrength", val -> ModuleSettings.enforceRemoteMaxStrength = val);
        createSwitch(container, "主控 | 无视强度上限设置", "想拉多高拉多高 (坏.jpg",
                "bypassRemoteMaxStrength", val -> ModuleSettings.bypassRemoteMaxStrength = val);
    }

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout container = UiUtils.makeDialogContainer(context, "DG-Lab Unlocker 设置");

        createSettingSwitches(container);

        UiUtils.createSpace(container);
        UiUtils.createButton(container, "模块运行状态", e -> new StatusDialog(context).show());
        if (XposedModuleInit.ENABLE_DEV_FEATURE) {
            UiUtils.createSpace(container);
            UiUtils.createButton(container, "实验功能测试", e -> new DevTestDialog(context).show());
        }

        return container;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSwitch(LinearLayout container, String title, String desc, String config, Consumer<Boolean> handler) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = new TextView(container.getContext());
        textTitle.setText(title);
        textTitle.setTextColor(0xffffe99d);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);
        Switch settingSwitch = new Switch(container.getContext());
        settingSwitch.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        StateListDrawable trackSelector = new StateListDrawable();
        trackSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenTrack.getConstantState().newDrawable());
        trackSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseTrack.getConstantState().newDrawable());
        settingSwitch.setTrackDrawable(trackSelector);
        StateListDrawable thumbSelector = new StateListDrawable();
        thumbSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenThumb.getConstantState().newDrawable());
        thumbSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseThumb.getConstantState().newDrawable());
        settingSwitch.setThumbDrawable(thumbSelector);
        settingSwitch.setTextOff("");
        settingSwitch.setTextOn("");
        settingSwitch.setChecked(ModuleSettings.sharedPref.getBoolean(config, false));
        settingSwitch.setOnClickListener(e -> {
            handler.accept(settingSwitch.isChecked());
            ModuleSettings.sharedPref.edit().putBoolean(config, settingSwitch.isChecked()).commit();
            Log.i("DgLabUnlocker", "Config " + config + " set to " + settingSwitch.isChecked());
        });
        layout.addView(settingSwitch);
        container.addView(layout);

        TextView textDesc = new TextView(container.getContext());
        textDesc.setText(desc);
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, UiUtils.dpToPx(layout, 1), 0, UiUtils.dpToPx(layout, 4));
        textDesc.setTextColor(0xffdfd2a5);
        container.addView(textDesc);
    }

}
