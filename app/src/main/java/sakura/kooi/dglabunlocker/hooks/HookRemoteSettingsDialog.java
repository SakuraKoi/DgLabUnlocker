package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookRemoteSettingsDialog extends AbstractHook<HookRemoteSettingsDialog.IRemoteSettingDialogUiInterceptor> {
    public HookRemoteSettingsDialog() {
        super(IRemoteSettingDialogUiInterceptor.class);
    }

    @Override
    public String getName() {
        return "远程控制设置";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_ANY;
    }

    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_RemoteSettingDialog, classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object thisObject = param.thisObject;
                        HookRemoteSettingsDialog.this.callHandlers(interceptor -> {
                            interceptor.interceptUiElements(context, thisObject);
                        });
                    }
                });
    }

    public interface IRemoteSettingDialogUiInterceptor {
        void interceptUiElements(Context context, Object thisObject) throws ReflectiveOperationException;
    }
}
