package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class ClickableFeatureDialog extends Dialog {
    public ClickableFeatureDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "附加功能", container -> {
            UiUtils.createButton(container, "一键276 - A", false, e -> {
                Toast.makeText(context, "坏蛋! 还没写完, 咕咕咕了", Toast.LENGTH_SHORT).show();
            });
            UiUtils.createButton(container, "一键276 - B", true, e -> {
                Toast.makeText(context, "坏蛋! 还没写完, 咕咕咕了", Toast.LENGTH_SHORT).show();
            });
        }));
    }
}
