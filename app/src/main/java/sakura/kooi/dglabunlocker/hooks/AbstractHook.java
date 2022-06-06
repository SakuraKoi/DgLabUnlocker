package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public abstract class AbstractHook<T> {
    @Getter
    private Class<T> handlerClass;
    private List<AbstractFeature> handlers = new ArrayList<>();

    @Getter @Setter
    private boolean isHooked = false;

    public AbstractHook(Class<T> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public void registerHandler(Object handler) {
        if (!handlerClass.isInstance(handler))
            throw new IllegalArgumentException("Handler " + handler.getClass().getName() + " cannot be casted to registered to hook "
                    + this.getClass().getName() + " ( require " + handlerClass.getName() + " )");
        if (!(handler instanceof AbstractFeature))
            throw new IllegalArgumentException("Handler " + handler.getClass().getName() + " is not AbstractFeature and cannot be registered");

        this.handlers.add((AbstractFeature) handler);
    }

    public void applyHook(Context context, ClassLoader classLoader) {
        try {
            apply(context, classLoader);
            isHooked = true;
        } catch (Exception e) {
            isHooked = false;
            ModuleUtils.logError("DgLabUnlocker", "Could not apply " + this.getClass().getName(), e);
        }
    }

    public abstract String getName();

    public abstract int getMinVersion();

    public boolean isUnsupported() {
        return HookRegistry.versionCode < getMinVersion();
    }

    protected abstract void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException;

    @SuppressWarnings("unchecked") // is checked
    protected void callHandlers(ConsumerEx<T> dataPipe) {
        for (AbstractFeature handler : handlers) {
            if (!handler.isEnabled() || handler.isUnsupported())
                continue;
            if (handler.getSide() == AbstractFeature.ClientSide.CONTROLLED && Accessors.isRemote)
                continue;
            if (handler.getSide() == AbstractFeature.ClientSide.CONTROLLER && !Accessors.isRemote)
                continue;

            try {
                dataPipe.accept((T) handler);
            } catch (Exception e) {
                Log.e("DgLabUnlocker", "An error occurred in " + handler.getClass().getName(), e);
            }
        }
    }

    protected interface ConsumerEx<T> {
        void accept(T handler) throws Exception;
    }
}
