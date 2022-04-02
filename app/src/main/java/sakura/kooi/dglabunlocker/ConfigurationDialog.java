package sakura.kooi.dglabunlocker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.function.Consumer;

public class ConfigurationDialog {
    private static void createSettingSwitches(LinearLayout container) {
        createSwitch(container, "被控 | 解锁远程强度上限", "最高100完全不够用好吧",
                "unlockRemoteMaxStrength", val -> GlobalVariables.unlockRemoteMaxStrength = val);
        createSwitch(container, "被控 | 拦截强度翻倍BUG", "有效避免突然惨遭弹射起飞",
                "fixDoubleBug", val -> GlobalVariables.fixDoubleBug = val);
        createSwitch(container, "被控 | 屏蔽非法超高强度", "防止恶意用户烧掉你的设备",
                "deviceProtection", val -> GlobalVariables.deviceProtection = val);
        createSwitch(container, "被控 | 强制限制远程强度", "下面那个功能的防御",
                "enforceRemoteMaxStrength", val -> GlobalVariables.enforceRemoteMaxStrength = val);
        createSwitch(container, "主控 | 无视强度上限设置", "想拉多高拉多高 (坏.jpg",
                "bypassRemoteMaxStrength", val -> GlobalVariables.bypassRemoteMaxStrength = val);
    }

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout container = new LinearLayout(context);
        container.setPadding(dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16));
        container.setBackground(GlobalVariables.resInjectSettingsBackground);
        container.setOrientation(LinearLayout.VERTICAL);
        TextView header = new TextView(context);
        header.setText("DG-Lab Unlocker 设置");
        header.setGravity(Gravity.CENTER);
        header.setPadding(0, 0, 0, dpToPx(container, 16));
        container.addView(header);

        createSettingSwitches(container);
        return container;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSwitch(LinearLayout container, String title, String desc, String config, Consumer<Boolean> handler) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, dpToPx(layout, 6), 0, 0);
        TextView textTitle = new TextView(container.getContext());
        textTitle.setText(title);
        textTitle.setTextColor(0xffffe99d);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);
        Switch settingSwitch = new Switch(container.getContext());
        settingSwitch.setPadding(dpToPx(layout, 10), 0, 0, 0);
        StateListDrawable trackSelector = new StateListDrawable();
        trackSelector.addState(new int[]{android.R.attr.state_checked}, GlobalVariables.resInjectSwitchOpenTrack);
        trackSelector.addState(StateSet.WILD_CARD, GlobalVariables.resInjectSwitchCloseTrack);
        settingSwitch.setTrackDrawable(trackSelector);
        StateListDrawable thumbSelector = new StateListDrawable();
        thumbSelector.addState(new int[]{android.R.attr.state_checked}, GlobalVariables.resInjectSwitchOpenThumb);
        thumbSelector.addState(StateSet.WILD_CARD, GlobalVariables.resInjectSwitchCloseThumb);
        settingSwitch.setThumbDrawable(thumbSelector);
        settingSwitch.setTextOff("");
        settingSwitch.setTextOn("");
        settingSwitch.setChecked(GlobalVariables.sharedPref.getBoolean(config, false));
        settingSwitch.setOnClickListener(e -> {
            handler.accept(settingSwitch.isChecked());
            GlobalVariables.sharedPref.edit().putBoolean(config, settingSwitch.isChecked()).commit();
            Log.i("DgLabUnlocker", "Config " + config + " set to " + settingSwitch.isChecked());
        });
        layout.addView(settingSwitch);
        container.addView(layout);

        TextView textDesc = new TextView(container.getContext());
        textDesc.setText(desc);
        textDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textDesc.setPadding(0, dpToPx(layout, 1), 0, dpToPx(layout, 4));
        textDesc.setTextColor(0xffdfd2a5);
        container.addView(textDesc);
    }

    private static int dpToPx(View view, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getContext().getResources().getDisplayMetrics());
    }
}
