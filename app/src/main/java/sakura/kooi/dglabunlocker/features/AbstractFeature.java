package sakura.kooi.dglabunlocker.features;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public abstract class AbstractFeature {
    @Getter
    private boolean isEnabled = false;
    @Getter @Setter
    private boolean isWorking = false;

    public abstract String getSettingName();
    public abstract String getSettingDesc();
    public abstract String getConfigurationKey();

    public abstract List<Class<? extends AbstractHook<?>>> getRequiredHooks();

    public abstract boolean isUnsupported();

    public abstract ClientSide getSide();

    public abstract void initializeAndTest() throws Exception;

    public void setEnabled(boolean enabled) {
        updateFeatureStatus(enabled);
        this.isEnabled = enabled;
    }

    public abstract void updateFeatureStatus(boolean enabled);

    public enum ClientSide {
        ALL, CONTROLLER, CONTROLLED;
    }
}
