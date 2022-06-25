package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class DevTestDialog extends Dialog {
    public DevTestDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "实验功能测试", container -> {
            UiUtils.createButton(container, "模拟资源加载错误", e -> {
                StatusDialog.resourceInjection = false;
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
            });
        }));
    }
}
