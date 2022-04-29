package sakura.kooi.dglabunlocker.features;

import java.util.List;

import lombok.Getter;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public abstract class AbstractFeature {
    @Getter
    private boolean isEnabled = false;

    public abstract String getSettingName();
    public abstract String getSettingDesc();
    public abstract List<Class<? extends AbstractHook<?>>> getRequiredHooks();

    public abstract boolean isSupported();
    public abstract ClientSide getSide();

    public abstract void testFeatureWorks() throws Exception;

    public void setEnabled(boolean enabled) {
        updateFeatureStatus(enabled);
        this.isEnabled = enabled;
    }

    public abstract void updateFeatureStatus(boolean enabled);

    public enum ClientSide {
        ALL, CONTROLLER, CONTROLLED;
    }
}
