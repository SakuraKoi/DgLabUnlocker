package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;

public abstract class AbstractHook<T> {
    @Getter
    private Class<T> handlerClass;
    private List<AbstractFeature> handlers = new ArrayList<>();

    @Getter
    private boolean isWorking = false;

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
            isWorking = true;
        } catch (Exception e) {
            isWorking = false;
            ModuleUtils.logError("DgLabUnlocker", "Could not apply " + this.getClass().getName(), e);
        }
    }

    protected abstract void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException;

    @SuppressWarnings("unchecked") // is checked
    protected void callHandlers(ConsumerEx<T> dataPipe) throws ReflectiveOperationException {
        boolean isRemote = Accessors.isRemote.get();
        for (AbstractFeature handler : handlers) {
            if (!handler.isEnabled())
                continue;
            if (handler.getSide() == AbstractFeature.ClientSide.CONTROLLED && !isRemote)
                continue;
            if (handler.getSide() == AbstractFeature.ClientSide.CONTROLLER && isRemote)
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
