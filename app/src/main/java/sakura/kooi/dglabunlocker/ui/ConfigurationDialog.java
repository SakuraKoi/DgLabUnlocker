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

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class ConfigurationDialog {
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout container = UiUtils.makeDialogContainer(context, "DG-Lab Unlocker 设置");

        createSettingSwitches(container);

        UiUtils.createButton(container, "附加功能", e -> new ClickableFeatureDialog(context).show());
        UiUtils.createButton(container, "模块运行状态", e -> new StatusDialog(context).show());
        if (HookRegistry.ENABLE_DEV_FEATURE) {
            UiUtils.createButton(container, "实验功能测试", e -> new DevTestDialog(context).show());
        }

        return container;
    }

    private static void createSettingSwitches(LinearLayout container) {
        for (AbstractFeature feature : HookRegistry.featureInstances.values()) {
            if (feature.isUnsupported())
                continue;
            createSettingSwitch(container, feature);
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSettingSwitch(LinearLayout container, AbstractFeature feature) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(container.getContext());
        Switch settingSwitch = UiUtils.createSwitch(container.getContext());
        TextView textDesc = UiUtils.createTextView(container.getContext(), 0xffdfd2a5);
        textTitle.setText(feature.getSettingName());
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        settingSwitch.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        settingSwitch.setChecked(feature.isEnabled());
        settingSwitch.setEnabled(feature.isLoaded());
        settingSwitch.setOnClickListener(e -> {
            boolean enabled = settingSwitch.isChecked();
            try {
                feature.setEnabled(enabled);
                ModuleSettings.sharedPref.edit().putBoolean(feature.getConfigurationKey(), enabled).commit();
                textDesc.setText(feature.getSettingDesc());
                Log.i("DgLabUnlocker", "Config " + feature.getConfigurationKey() + " set to " + settingSwitch.isChecked());
            } catch (Exception ex) {
                feature.setEnabled(false);
                settingSwitch.setChecked(!settingSwitch.isChecked());
                Toast.makeText(container.getContext(),  "发生异常错误", Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(settingSwitch);
        container.addView(layout);

        textDesc.setText(feature.isLoaded() ? feature.getSettingDesc() : "加载时发生错误, 功能不可用");
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, UiUtils.dpToPx(layout, 1), 0, UiUtils.dpToPx(layout, 4));
        container.addView(textDesc);
    }
}
