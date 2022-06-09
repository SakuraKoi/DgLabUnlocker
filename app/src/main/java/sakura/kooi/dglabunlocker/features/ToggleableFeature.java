package sakura.kooi.dglabunlocker.features;

import lombok.Getter;

public abstract class ToggleableFeature extends AbstractFeature {
    @Getter
    private boolean isEnabled = false;

    @Override
    public String getName() {
        return getSettingName();
    }

    public abstract String getSettingName();
    public abstract String getSettingDesc();
    public abstract String getConfigurationKey();

    public void setEnabled(boolean enabled) {
        updateFeatureStatus(enabled);
        this.isEnabled = enabled;
    }

    public abstract void updateFeatureStatus(boolean enabled);
}
