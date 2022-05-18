package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookControlledStrengthButton extends AbstractHook<HookControlledStrengthButton.ILocalStrengthInterceptor> {
    public HookControlledStrengthButton() {
        super(ILocalStrengthInterceptor.class);
    }

    @Override
    public String getName() {
        return "基础强度回调";
    }

    public void apply(Context context, ClassLoader classLoader) {
        InjectPoints.class_StrengthTouchListeners.forEach(classTouchListener -> {
            XposedHelpers.findAndHookMethod(classTouchListener, classLoader,
                    "onTouch", View.class, MotionEvent.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            callHandlers(handler -> {
                                handler.handleLocalStrengthChanged(context);
                            });
                        }
                    });
        });
    }

    public interface ILocalStrengthInterceptor {
        void handleLocalStrengthChanged(Context context) throws ReflectiveOperationException;
    }
}
