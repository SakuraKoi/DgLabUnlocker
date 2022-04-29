package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookRemoteSettingsDialog extends AbstractHook<HookRemoteSettingsDialog.IRemoteSettingDialogUiInterceptor> {
    public HookRemoteSettingsDialog() {
        super(IRemoteSettingDialogUiInterceptor.class);
    }

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_RemoteSettingDialog, classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object thisObject = param.thisObject;
                        HookRemoteSettingsDialog.this.callHandlers(interceptor -> {
                            interceptor.interceptUiElements(thisObject);
                        });
                    }
                });
    }

    public interface IRemoteSettingDialogUiInterceptor {
        void interceptUiElements(Object thisObject) throws ReflectiveOperationException;
    }
}
