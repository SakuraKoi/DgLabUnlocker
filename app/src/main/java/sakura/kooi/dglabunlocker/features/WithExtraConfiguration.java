package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Retention;

import sakura.kooi.dglabunlocker.ui.ExtraConfigurationDialog;
import sakura.kooi.dglabunlocker.utils.UiUtils;

public interface WithExtraConfiguration {
    default View createSettingButton(Context context) {
        return UiUtils.createSettingButton(context, e -> {
            new ExtraConfigurationDialog(context, (AbstractFeature) this).show();
        });
    }

    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @interface ExtraConfigurationField {
        String key();
        String name();
    }
}
