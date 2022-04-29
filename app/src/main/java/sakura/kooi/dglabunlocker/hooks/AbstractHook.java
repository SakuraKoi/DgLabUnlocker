package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;

public abstract class AbstractHook<T> {
    @Getter
    private Class<T> handlerClass;
    private List<T> handlers = new ArrayList<>();

    @Getter
    private boolean isWorking = false;

    public AbstractHook(Class<T> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public void registerHandler(T handler) {
        this.handlers.add(handler);
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
}
