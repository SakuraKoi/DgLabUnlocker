package sakura.kooi.dglabunlocker.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.function.Supplier;

import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public class StatusDialog extends Dialog {
    public static String currentLoadedVersion = null;
    public static boolean resourceInjection = false;
    public static boolean fieldsLookup = false;
    public static boolean moduleSettingsDialogInject = false;

    @SuppressLint("ResourceType")
    public StatusDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "DG-Lab Unlocker 日志", container -> {
            container.addView(addCurrentVersion(context));
            container.addView(addStatus(context, "[加载] 界面资源注入", false, resourceInjection));
            container.addView(addStatus(context, "[加载] 模块设置界面", false, moduleSettingsDialogInject));
            container.addView(addStatus(context, "[加载] 应用字段初始化", false, fieldsLookup));

            for (AbstractHook<?> hook : HookRegistry.hookInstances.values()) {
                container.addView(addStatus(context, "[注入] " + hook.getName(), hook.isUnsupported(), hook.isHooked()));
            }

            for (Map.Entry<String, Map.Entry<Supplier<String>, Supplier<Integer>>> customStatus : HookRegistry.customStatuses.entrySet()) {
                container.addView(addStatus(context, customStatus.getKey(),
                        customStatus.getValue().getKey().get(),
                        customStatus.getValue().getValue().get()));
            }
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

    private LinearLayout addStatus(Context context, String title, boolean isUnsupported, boolean isHooked) {
        return addStatus(context, title, isUnsupported ? "不支持" : isHooked ? "成功" : "错误", !isUnsupported && isHooked ? 0xffc6ff00 : 0xfff44336);
    }

    private LinearLayout addStatus(Context context, String title, String status, int color) {
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(0, UiUtils.dpToPx(layout, 6), 0, 0);
        TextView textTitle = UiUtils.createTextView(context);
        textTitle.setText(title);
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(textTitle);

        TextView textStatus = UiUtils.createTextView(context);
        textStatus.setPadding(UiUtils.dpToPx(layout, 10), 0, 0, 0);
        textStatus.setText(status);
        textStatus.setTextColor(color);
        textStatus.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textStatus);
        return layout;
    }
}
