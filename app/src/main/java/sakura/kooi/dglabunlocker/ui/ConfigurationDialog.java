package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.Consumer;

import sakura.kooi.dglabunlocker.remote.WebsocketRPC;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class ConfigurationDialog {
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout container = UiUtils.makeDialogContainer(context, "DG-Lab Unlocker 设置");

        createSettingSwitches(container);

        UiUtils.createButton(container, "模块运行状态", e -> new StatusDialog(context).show());
        if (HookRegistry.ENABLE_DEV_FEATURE) {
            UiUtils.createButton(container, "实验功能测试", e -> new DevTestDialog(context).show());
        }

        return container;
    }

    private static void createSettingSwitches(LinearLayout container) {
        if (HookRegistry.ENABLE_DEV_FEATURE) // 目前还没用
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
        createRpcSwitch(container);
    }

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})
    private static void createRpcSwitch(LinearLayout container) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(container.getContext());
        textTitle.setText("远程 | 开放RPC服务端口");
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        TextView textDesc = UiUtils.createTextView(container.getContext(), 0xffdfd2a5);
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, UiUtils.dpToPx(layout, 1), 0, UiUtils.dpToPx(layout, 4));
        WebsocketRPC.updateStatus(textDesc);

        Switch settingSwitch = UiUtils.createSwitch(container.getContext());
        settingSwitch.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        settingSwitch.setChecked(WebsocketRPC.isRunning);
        settingSwitch.setOnClickListener(e -> {
            try {
                WebsocketRPC.update(settingSwitch.isChecked(), textDesc);
                ModuleSettings.sharedPref.edit().putBoolean("remoteRpc", settingSwitch.isChecked()).commit();
                Log.i("DgLabUnlocker", "Config " + "remoteRpc" + " set to " + settingSwitch.isChecked());
            } catch (Exception ex) {
                ModuleUtils.logError("DgLabUnlocker", "Failed start/stop rpc server", ex);
                Toast.makeText(container.getContext(), (settingSwitch.isChecked()? "启动": "停止") + "RPC服务失败", Toast.LENGTH_SHORT).show();
                settingSwitch.setChecked(!settingSwitch.isChecked());
            }
        });
        layout.addView(settingSwitch);
        container.addView(layout);
        container.addView(textDesc);
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSwitch(LinearLayout container, String title, String desc, String config, Consumer<Boolean> handler) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(container.getContext());
        textTitle.setText(title);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        Switch settingSwitch = UiUtils.createSwitch(container.getContext());
        settingSwitch.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        settingSwitch.setChecked(ModuleSettings.sharedPref.getBoolean(config, false));
        settingSwitch.setOnClickListener(e -> {
            handler.accept(settingSwitch.isChecked());
            ModuleSettings.sharedPref.edit().putBoolean(config, settingSwitch.isChecked()).commit();
            Log.i("DgLabUnlocker", "Config " + config + " set to " + settingSwitch.isChecked());
        });
        layout.addView(settingSwitch);
        container.addView(layout);

        TextView textDesc = UiUtils.createTextView(container.getContext(), 0xffdfd2a5);
        textDesc.setText(desc);
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, UiUtils.dpToPx(layout, 1), 0, UiUtils.dpToPx(layout, 4));
        container.addView(textDesc);
    }

}
