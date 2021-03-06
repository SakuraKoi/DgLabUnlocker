package sakura.kooi.dglabunlocker.variables;

import android.content.Context;
import android.content.SharedPreferences;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.ToggleableFeature;
import sakura.kooi.dglabunlocker.ui.ModuleDialog;

public class ModuleSettings {
    public static SharedPreferences sharedPref;

    public static void loadConfiguration(Context context) {
        sharedPref = context.getSharedPreferences("dglabunlocker", Context.MODE_PRIVATE);

        for(AbstractFeature feature : HookRegistry.featureInstances.values()) {
            if (feature instanceof ToggleableFeature) {
                ToggleableFeature toggleableFeature = (ToggleableFeature) feature;
                toggleableFeature.setEnabled(sharedPref.getBoolean(toggleableFeature.getConfigurationKey(), false));
            }
        }
    }

    public static void showSettingsDialog(Context context) {
        new ModuleDialog(context).show();
    }
}
