package sakura.kooi.dglabunlocker.utils;

import android.util.TypedValue;
import android.view.View;

public class UiUtils {
    public static int dpToPx(View view, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getContext().getResources().getDisplayMetrics());
    }
}
