package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

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
                View btn = ((ClickableFeature) feature).inflateFeatureLayout(context);
                btn.setEnabled(feature.isLoaded());
                container.addView(btn);
            }
        }));
    }
}
