package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.ClickableFeature;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public class ClickableFeatureDialog extends Dialog {
    public ClickableFeatureDialog(@NonNull Context context) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, "附加功能", container -> {
            for (AbstractFeature feature : HookRegistry.featureInstances.values()) {
                if (!(feature instanceof ClickableFeature))
                    continue;
                if (feature.isUnsupported())
                    continue;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                ((ClickableFeature) feature).inflateFeatureLayout(context, layout);
                layout.setEnabled(feature.isLoaded());
                container.addView(layout);
            }
        }));
    }
}
