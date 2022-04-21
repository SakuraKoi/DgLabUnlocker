package sakura.kooi.dglabunlocker.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.variables.ResourceInject;

public class UiUtils {
    public static int dpToPx(View view, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getContext().getResources().getDisplayMetrics());
    }

    public static void createSpace(LinearLayout container) {
        TextView space = new TextView(container.getContext());
        space.setPadding(0, dpToPx(space, 4), 0, 0);
        container.addView(space);
    }

    public static void createButton(LinearLayout container, String title, View.OnClickListener listener) {
        TextView btnStatus = new TextView(container.getContext());
        btnStatus.setPadding(0, dpToPx(btnStatus, 3), 0, dpToPx(btnStatus, 3));
        if (StatusDialog.resourceInjection) {
            btnStatus.setBackground(ResourceInject.buttonBackground.getConstantState().newDrawable());
            btnStatus.setTextColor(Color.BLACK);
        }
        btnStatus.setText(title);
        btnStatus.setGravity(Gravity.CENTER);
        btnStatus.setOnClickListener(listener);
        container.addView(btnStatus);
    }

    @NonNull
    public static LinearLayout makeDialogContainer(Context context, String title) {
        LinearLayout container = new LinearLayout(context);
        container.setPadding(dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16));
        if (StatusDialog.resourceInjection) {
            container.setBackground(ResourceInject.dialogSettingsBackground.getConstantState().newDrawable());
        }
        container.setOrientation(LinearLayout.VERTICAL);
        TextView header = new TextView(context);
        header.setText(title);
        header.setGravity(Gravity.CENTER);
        header.setPadding(0, 0, 0, dpToPx(container, 16));
        container.addView(header);
        return container;
    }
}
