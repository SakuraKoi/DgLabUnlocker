package sakura.kooi.dglabunlocker.hooks.business;

import android.content.Context;
import android.os.Message;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookLongPressStrengthHandler extends AbstractHook<HookLongPressStrengthHandler.IStrengthButtonInterceptor> {
    public HookLongPressStrengthHandler() {
        super(HookLongPressStrengthHandler.IStrengthButtonInterceptor.class);
    }

    @Override
    public String getName() {
        return "长按强度回调";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_1_3_2;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod(InjectPoints.class_StrengthLongPressHandler, classLoader,
                "handleMessage", Message.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Message message = (Message) param.args[0];
                        int what = message.what;
                        if (what >= 10 && what <= 13) {
                            callHandlers(handler -> {
                                handler.beforeStrengthChange(context);
                            });
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Message message = (Message) param.args[0];
                        int what = message.what;
                        if (what >= 10 && what <= 13) {
                            callHandlers(handler -> {
                                handler.afterStrengthChange(context);
                            });
                        }
                    }
                });
    }

    public interface IStrengthButtonInterceptor {
        void beforeStrengthChange(Context context) throws ReflectiveOperationException;

        void afterStrengthChange(Context context) throws ReflectiveOperationException;
    }
}
