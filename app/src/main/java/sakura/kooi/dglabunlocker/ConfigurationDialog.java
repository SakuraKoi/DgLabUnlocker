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

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

public class ConfigurationDialog {
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "UseCompatLoadingForDrawables"})
    public static View createSettingsPanel(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(dpToPx(layout, 16), dpToPx(layout, 16), dpToPx(layout, 16), dpToPx(layout, 16));
        layout.setBackground(GlobalVariables.resInjectSettingsBackground);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(((Supplier<View>) () -> {
            TextView view = new TextView(context);
            view.setText("DG-Lab Unlocker 设置");
            view.setGravity(Gravity.CENTER);
            view.setPadding(0, 0, 0, dpToPx(layout, 16));
            return view;
        }).get());
        createSwitch(layout, "被控 | 解锁远程强度上限", "最高100完全不够用好吧",
                "unlockRemoteMaxStrength", val -> GlobalVariables.unlockRemoteMaxStrength = val, true);
        createSwitch(layout, "被控 | 拦截强度翻倍BUG", "有效避免突然惨遭弹射起飞",
                "fixDoubleBug", val -> GlobalVariables.fixDoubleBug = val, true);
        createSwitch(layout, "被控 | 屏蔽非法超高强度", "避免恶意用户烧掉你的设备",
                "deviceProtection", val -> GlobalVariables.deviceProtection = val, true);
        createSwitch(layout, "被控 | 强制限制远程强度", "避免魔改客户端搞事情",
                "enforceRemoteMaxStrength", val -> GlobalVariables.enforceRemoteMaxStrength = val, true);
        createSwitch(layout, "主控 | 无视强度上限设置", "想拉多高拉多高 (坏.jpg",
                "bypassRemoteMaxStrength", val -> GlobalVariables.bypassRemoteMaxStrength = val, true);

        return layout;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void createSwitch(LinearLayout container, String title, String desc, String config, Consumer<Boolean> handler, boolean enable) {
        LinearLayout layout = new LinearLayout(container.getContext());
        layout.setPadding(0, dpToPx(layout, 6), 0, 0);
        TextView text = new TextView(container.getContext());
        text.setText(title);
        text.setTextColor(0xffffe99d);
        text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(text);
        Switch swi = new Switch(container.getContext());
        swi.setPadding(dpToPx(layout, 10), 0, 0, 0);
        StateListDrawable trackSelector = new StateListDrawable();
        trackSelector.addState(new int[]{android.R.attr.state_checked}, GlobalVariables.resInjectSwitchOpenTrack);
        trackSelector.addState(StateSet.WILD_CARD, GlobalVariables.resInjectSwitchCloseTrack);
        swi.setTrackDrawable(trackSelector);
        StateListDrawable thumbSelector = new StateListDrawable();
        thumbSelector.addState(new int[]{android.R.attr.state_checked}, GlobalVariables.resInjectSwitchOpenThumb);
        thumbSelector.addState(StateSet.WILD_CARD, GlobalVariables.resInjectSwitchCloseThumb);
        swi.setThumbDrawable(thumbSelector);
        swi.setTextOff("");
        swi.setTextOn("");
        swi.setEnabled(enable);
        swi.setChecked(GlobalVariables.sharedPref.getBoolean(config, false));
        swi.setOnClickListener(e -> {
            handler.accept(swi.isChecked());
            GlobalVariables.sharedPref.edit().putBoolean(config, swi.isChecked()).commit();
            Log.i("DgLabUnlocker", "Config " + config + " set to " + swi.isChecked());
        });
        layout.addView(swi);
        container.addView(layout);

        TextView descT = new TextView(container.getContext());
        descT.setText(desc);
        descT.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        descT.setPadding(0, dpToPx(layout, 1), 0, dpToPx(layout, 4));
        descT.setTextColor(0xffdfd2a5);
        container.addView(descT);
    }

    private static int dpToPx(View view, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getContext().getResources().getDisplayMetrics());
    }
}
