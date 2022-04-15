package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class DevTestDialog extends Dialog {
    public DevTestDialog(@NonNull Context context) {
        super(context);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LinearLayout container = UiUtils.makeDialogContainer(context, "实验功能测试");

        this.setContentView(container);
    }
}
