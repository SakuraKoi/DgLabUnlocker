package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class ClickableFeatureDialog extends Dialog {
    public ClickableFeatureDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "附加功能", container -> {
            UiUtils.createButton(container, "一键276 - A", false, e -> {
            });
            UiUtils.createButton(container, "一键276 - B", false, e -> {
            });
        }));
    }
}
