package sakura.kooi.dglabunlocker.features;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public abstract class AbstractFeature {
    @Getter
    private boolean isEnabled = false;
    @Getter @Setter
    private boolean isWorking = false;

    public abstract String getSettingName();
    public abstract String getSettingDesc();
    public String getConfigurationKey() {
        return this.getClass().getSimpleName();
    }
    public abstract List<Class<? extends AbstractHook<?>>> getRequiredHooks();

    public boolean isSupported() {
        boolean supported = isWorking;
        for (Class<? extends AbstractHook<?>> requiredHookClass : getRequiredHooks()) {
            AbstractHook<?> hookInstance = HookRegistry.hookInstances.get(requiredHookClass);
            supported = supported && hookInstance != null && hookInstance.isWorking();
        }
        return supported;
    }

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
