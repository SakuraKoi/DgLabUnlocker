package sakura.kooi.dglabunlocker.hooks.preload;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.content.Context;
import android.util.Log;

import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;

public class HookDisableForceUpdate extends AbstractHook<Object> {
    public HookDisableForceUpdate() {
        super(Object.class);
    }

    @Override
    public String getName() {
        return "屏蔽强制更新";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_ANY;
    }

    // com.bjsm.dungeonlab.widget.UpdateAppDialog.show(String str, String str2, int isForce, String str3)
    // isForce -> 0
    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        withCatch("HookDisableForceUpdate findMethod", () -> {
            XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.UpdateAppDialog", classLoader,
                    "show", String.class, String.class, int.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            withCatch("HookDisableForceUpdate", () -> {
                                Log.i("DgLabUnlocker", "HookDisableForceUpdate: Showing update dialog with param " + param.args[2]);
                                StringBuilder message = new StringBuilder();
                                message.append("** [DGLab-Unlocker] 提示: \n");
                                if (Objects.equals(param.args[2], 0)) {
                                    message.append("** 屏蔽强制更新成功\n** 您现在可以取消本次更新\n\n");
                                    param.args[2] = 1;
                                    Log.i("DgLabUnlocker", "HookDisableForceUpdate: Disabling force update flag");
                                }
                                message.append("** 建议先检查模块是否适配新版本\n** 以免更新APP后模块失效\n\n");
                                message.append((String) param.args[0]);
                                param.args[0] = message.toString();
                            });
                        }
                    });
        });
    }
}
