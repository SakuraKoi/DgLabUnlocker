package sakura.kooi.dglabunlocker.hooks.business;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_HomeActivity_updateStrengthText;

import android.content.Context;
import android.widget.TextView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.utils.DgLabVersion;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class HookStrengthText extends AbstractHook<HookStrengthText.StrengthTextHandler> {
    public HookStrengthText() {
        super(StrengthTextHandler.class);
    }

    @Override
    public String getName() {
        return "强度文字显示";
    }

    @Override
    public int getMinVersion() {
        return DgLabVersion.V_1_3_2;
    }

    @Override
    protected void apply(Context context, ClassLoader classLoader) throws ReflectiveOperationException {
        XposedHelpers.findAndHookMethod(class_HomeActivity, classLoader,
                method_HomeActivity_updateStrengthText, int.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        callHandlers(handler -> {
                            handler.interceptStrengthText(
                                    Accessors.textHomeActivityStrengthA.get(param.thisObject),
                                    Accessors.textHomeActivityStrengthB.get(param.thisObject)
                            );
                        });
                    }
                });
    }

    public interface StrengthTextHandler {
        void interceptStrengthText(TextView textA, TextView textB) throws ReflectiveOperationException;
    }
}
