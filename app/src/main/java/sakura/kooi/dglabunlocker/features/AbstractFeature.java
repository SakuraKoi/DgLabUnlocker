package sakura.kooi.dglabunlocker.features;

import lombok.Getter;
import sakura.kooi.dglabunlocker.hooks.IHook;

public abstract class AbstractFeature {
    @Getter
    private boolean isEnabled = false;

    public abstract String getSettingName();
    public abstract String getSettingDesc();
    public abstract IHook[] getRequiredHooks();

    public abstract boolean isSupported();

    public abstract void testFeatureWorks() throws Exception;

    public void setEnabled(boolean enabled) {
        updateFeatureStatus(enabled);
        this.isEnabled = enabled;
    }

    public abstract void updateFeatureStatus(boolean enabled);
}
