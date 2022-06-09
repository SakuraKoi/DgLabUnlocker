package sakura.kooi.dglabunlocker.hooks.preload;

import static sakura.kooi.dglabunlocker.utils.ExceptionLogger.withCatch;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.ui.ModuleDialog;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class HookSettingsDialog {
    public static void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.FuncSelectDialog", classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        withCatch("HookBugReportDialog FuncSelectDialog", () -> {
                            Log.i("DgLabUnlocker", "HookSettingsDialog: replacing onClickListener");
                            Dialog dialog = (Dialog) param.thisObject;
                            Field btnField = dialog.getClass().getDeclaredField("safe_button");
                            btnField.setAccessible(true);
                            View btn = (View) btnField.get(dialog);
                            if (btn == null) {
                                Log.e("DgLabUnlocker", "HookSettingsDialog: Inject failed, button is null");
                                return;
                            }

                            btn.setOnClickListener(e -> withCatch("SettingDialog open", () -> ModuleSettings.showSettingsDialog(btn.getContext())));
                        });
                    }
                });

        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.BugDialog", classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        withCatch("HookBugReportDialog BugDialog", () -> {
                            Log.i("DgLabUnlocker", "HookSettingsDialog: replacing layout of BugDialog");
                            Dialog dialog = (Dialog) param.thisObject;
                            dialog.setContentView(ModuleDialog.makeLayout(dialog.getContext()));
                        });
                    }
                });
    }
}
