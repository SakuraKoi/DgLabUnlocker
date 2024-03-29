package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.ToggleableFeature;
import sakura.kooi.dglabunlocker.features.WithExtraConfiguration;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class ConfigurationDialog extends Dialog {
    public ConfigurationDialog(@NonNull Context context) {
        super(context);
        setContentView(UiUtils.makeDialogLayout(this, "DG-Lab Unlocker 设置", ConfigurationDialog::createSettingSwitches));
    }

    private static void createSettingSwitches(LinearLayout container) {
        for (AbstractFeature feature : HookRegistry.featureInstances.values()) {
            if (!(feature instanceof ToggleableFeature))
                continue;
            if (feature.isUnsupported())
                continue;
            createSettingSwitch(container, (ToggleableFeature)feature);
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSettingSwitch(LinearLayout container, ToggleableFeature feature) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        if (feature instanceof WithExtraConfiguration) {
            View extraConfigButton = ((WithExtraConfiguration) feature).createSettingButton(container.getContext());
            layout.addView(extraConfigButton);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) extraConfigButton.getLayoutParams();
            layoutParams.rightMargin = UiUtils.dpToPx(layout, 6);
            extraConfigButton.setLayoutParams(layoutParams);
        }
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
