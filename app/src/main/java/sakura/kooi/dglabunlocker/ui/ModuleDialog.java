package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.BuildConfig;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public class ModuleDialog extends Dialog {
    public ModuleDialog(@NonNull Context context) {
        super(context);
        if (StatusDialog.resourceInjection) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        this.setContentView(makeLayout(context));
    }

    public static LinearLayout makeLayout(@NonNull Context context) {
        LinearLayout container = UiUtils.makeDialogContainer(context, "DG-Lab 魔改模块");

        container.addView(UiUtils.createTextView(context, "应用版本: " +
                (StatusDialog.currentLoadedVersion == null ? "未知" : StatusDialog.currentLoadedVersion) + " - " + HookRegistry.versionCode
        ));
        container.addView(UiUtils.createTextView(context, "模块版本: " + BuildConfig.VERSION_NAME));

        UiUtils.createButton(container, "功能设置", e -> new ConfigurationDialog(context).show());
        UiUtils.createButton(container, "附加功能", e -> new ClickableFeatureDialog(context).show());
        UiUtils.createButton(container, "模块运行状态", e -> new StatusDialog(context).show());
        if (HookRegistry.ENABLE_DEV_FEATURE) {
            UiUtils.createButton(container, "实验功能测试", e -> new DevTestDialog(context).show());
        }

        UiUtils.createSpacing(container, 4);
        TextView textAuthor = new TextView(context);
        textAuthor.setText("Github @SakuraKoi/DgLabUnlocker");
        textAuthor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textAuthor.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        container.addView(textAuthor);

        container.setMinimumWidth(UiUtils.dpToPx(container, 270));
        return container;
    }
}
