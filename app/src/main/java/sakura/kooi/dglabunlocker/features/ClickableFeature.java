package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class ClickableFeature extends AbstractFeature {
    public abstract void inflateFeatureLayout(Context context, LinearLayout layout);
}
