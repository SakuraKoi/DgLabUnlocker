package sakura.kooi.dglabunlocker.variables;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.ToggleableFeature;
import sakura.kooi.dglabunlocker.features.WithExtraConfiguration;
import sakura.kooi.dglabunlocker.ui.ModuleDialog;

public class ModuleSettings {
    public static SharedPreferences sharedPref;

    public static void loadConfiguration(Context context) {
        sharedPref = context.getSharedPreferences("dglabunlocker", Context.MODE_PRIVATE);

        for (AbstractFeature feature : HookRegistry.featureInstances.values()) {
            if (feature instanceof ToggleableFeature) {
                ToggleableFeature toggleableFeature = (ToggleableFeature) feature;
                toggleableFeature.setEnabled(sharedPref.getBoolean(toggleableFeature.getConfigurationKey(), false));
            }
            if (feature instanceof WithExtraConfiguration) {
                for (Field field : feature.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(WithExtraConfiguration.ExtraConfigurationField.class)) {
                        WithExtraConfiguration.ExtraConfigurationField extraConfigurationField = field.getAnnotation(WithExtraConfiguration.ExtraConfigurationField.class);
                        if (extraConfigurationField == null || extraConfigurationField.key() == null)
                            throw new IllegalArgumentException("Illegal extra config with null key in feature " + feature.getName());
                        field.setAccessible(true);
                        try {
                            if (field.getType() == int.class) {
                                field.set(feature, sharedPref.getInt(extraConfigurationField.key(), field.getInt(feature)));
                            } else if (field.getType() == String.class) {
                                field.set(feature, sharedPref.getString(extraConfigurationField.key(), (String) field.get(feature)));
                            } else if (field.getType() == boolean.class) {
                                field.set(feature, sharedPref.getBoolean(extraConfigurationField.key(), field.getBoolean(feature)));
                            } else
                                throw new IllegalArgumentException("Illegal extra config " + extraConfigurationField.key() + " in feature " + feature.getName());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void showSettingsDialog(Context context) {
        new ModuleDialog(context).show();
    }
}
