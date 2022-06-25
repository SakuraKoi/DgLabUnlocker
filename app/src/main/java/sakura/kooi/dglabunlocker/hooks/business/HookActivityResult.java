package sakura.kooi.dglabunlocker.hooks.business;

import static sakura.kooi.dglabunlocker.utils.DgLabVersion.V_1_3_2;

import android.content.Context;
import android.content.Intent;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public class HookActivityResult extends AbstractHook<HookActivityResult.IActivityResultInterceptor> {
    public HookActivityResult() {
        super(IActivityResultInterceptor.class);
    }

    @Override
    public String getName() {
        return "窗口消息回调";
    }

    @Override
    public int getMinVersion() {
        return V_1_3_2;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod("android.app.Activity", classLoader, "onActivityResult",
                int.class, int.class, Intent.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        callHandlers(interceptor -> {
                            interceptor.onActivityResult(context, (int)param.args[0], (int)param.args[1], (Intent) param.args[2]);
                        });
                    }
                });
    }

    public interface IActivityResultInterceptor {
        void onActivityResult(Context context, int requestCode, int resultCode, Intent data);
    }
}
