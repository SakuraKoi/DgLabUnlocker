package sakura.kooi.dglabunlocker.features;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public abstract class AbstractFeature {

    @Getter @Setter
    private boolean isLoaded = false;

    public abstract String getName();

    public abstract List<Class<? extends AbstractHook<?>>> getRequiredHooks();

    public boolean isUnsupported() {
        for (Class<? extends AbstractHook<?>> hook : getRequiredHooks()) {
            AbstractHook<?> hookInstance = HookRegistry.hookInstances.get(hook);
            // every required hook's instance should has been created
            if (hookInstance == null)
                return true;
            if (hookInstance.isUnsupported())
                return true;
        }
        return false;
    }

    public abstract ClientSide getSide();

    public void initialize() throws Exception {}

    public enum ClientSide {
        ALL, CONTROLLER, CONTROLLED;
    }
}
