package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class StatusDialog extends Dialog {
    public static String currentLoadedVersion = null;
    public static boolean resourceInjection = false;
    public static boolean fieldsLookup = false;
    public static boolean remoteSettingsDialogInject = false;
    public static boolean bluetoothDecoderInject = false;
    public static boolean protocolStrengthDecodeInject = false;
    public static boolean strengthButtonInject = false;
    public static boolean localStrengthHandlerInject = false;
    public static boolean moduleSettingsDialogInject = false;
    public static boolean guestLogin;

    @SuppressLint("ResourceType")
    public StatusDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "DG-Lab Unlocker 日志", container -> {
            container.addView(addCurrentVersion(context));
            container.addView(addStatus(context, "[加载] 界面资源注入", resourceInjection));
            container.addView(addStatus(context, "[加载] 模块设置界面", moduleSettingsDialogInject));
            container.addView(addStatus(context, "[加载] 应用字段初始化", fieldsLookup));
            container.addView(addStatus(context, "[注入] 远程控制设置", remoteSettingsDialogInject));
            container.addView(addStatus(context, "[注入] 蓝牙命令接收", bluetoothDecoderInject));
            container.addView(addStatus(context, "[注入] 强度协议解码", protocolStrengthDecodeInject));
            container.addView(addStatus(context, "[注入] 强度调整按钮", strengthButtonInject));
            container.addView(addStatus(context, "[注入] 基础强度回调", localStrengthHandlerInject));
            container.addView(addStatus(context, "[注入] 游客设备哈希", guestLogin));
        }));
    }

    private LinearLayout addCurrentVersion(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(context);
        textTitle.setText("[数据] 版本适配参数");
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        TextView textStatus = new TextView(context);
        textStatus.setText(currentLoadedVersion != null ? currentLoadedVersion : "不支持");
        textStatus.setTextColor(currentLoadedVersion != null ? 0xffc6ff00 : 0xfff44336);
        layout.addView(textStatus);
        return layout;
    }

    private LinearLayout addStatus(Context context, String title, boolean isLoaded) {
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(context);
        textTitle.setText(title);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        TextView textStatus = UiUtils.createTextView(context);
        textStatus.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        textStatus.setText(isLoaded ? "成功" : "错误");
        textStatus.setTextColor(isLoaded ? 0xffc6ff00 : 0xfff44336);
        textStatus.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textStatus);
        return layout;
    }
}
