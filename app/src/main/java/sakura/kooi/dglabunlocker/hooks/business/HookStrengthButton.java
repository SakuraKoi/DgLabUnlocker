package sakura.kooi.dglabunlocker.hooks.business;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookStrengthButton extends AbstractHook<HookStrengthButton.IStrengthButtonInterceptor> {
    public HookStrengthButton() {
        super(IStrengthButtonInterceptor.class);
    }

    @Override
    public String getName() {
        return "强度调整按钮";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_ANY;
    }

    public void apply(Context context, ClassLoader classLoader) {
        InjectPoints.class_StrengthTouchListeners.forEach(classTouchListener -> {
            XposedHelpers.findAndHookMethod(classTouchListener, classLoader,
                "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        callHandlers(handler -> {
                            handler.beforeStrengthChange(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        callHandlers(handler -> {
                            handler.afterStrengthChange(context);
                        });
                    }
                });
        });
    }

    public interface IStrengthButtonInterceptor {
        void beforeStrengthChange(Context context) throws ReflectiveOperationException;
        void afterStrengthChange(Context context) throws ReflectiveOperationException;
    }
}
