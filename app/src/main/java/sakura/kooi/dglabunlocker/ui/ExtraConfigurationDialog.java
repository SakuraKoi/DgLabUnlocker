package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.TableLayout;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.WithExtraConfiguration;
import sakura.kooi.dglabunlocker.utils.UiUtils;

public class ExtraConfigurationDialog extends Dialog {
    public ExtraConfigurationDialog(Context context, AbstractFeature feature) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, feature.getName() + " 配置", container -> {
            LinkedHashMap<WithExtraConfiguration.ExtraConfigurationField, Field> configs = new LinkedHashMap<>();
            for (Field field : feature.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(WithExtraConfiguration.ExtraConfigurationField.class)) {
                    WithExtraConfiguration.ExtraConfigurationField extraConfigurationField = field.getAnnotation(WithExtraConfiguration.ExtraConfigurationField.class);
                    if (extraConfigurationField == null || extraConfigurationField.key() == null)
                        throw new IllegalArgumentException("Illegal extra config with null key in feature " + feature.getName());
                    field.setAccessible(true);
                    if (field.getType() == int.class || (field.getType() == String.class || field.getType() == boolean.class)) {
                        configs.put(extraConfigurationField, field);
                    } else throw new IllegalArgumentException("Illegal extra config " + extraConfigurationField.key() + " in feature " + feature.getName());
                }
            }

            // TODO create setting input from configs
            TableLayout table = new TableLayout(context);
            container.addView(table);

            for (Map.Entry<WithExtraConfiguration.ExtraConfigurationField, Field> entry : configs.entrySet()) {
                var config = entry.getKey();
                var field = entry.getValue();
            }

            // TODO add confirm and cancel button
            // TODO register actionListener -> save data on confirm
        }));
    }
}
