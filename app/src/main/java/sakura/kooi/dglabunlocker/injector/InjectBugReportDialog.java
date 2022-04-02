package sakura.kooi.dglabunlocker.injector;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.ConfigurationDialog;
import sakura.kooi.dglabunlocker.GlobalVariables;

public class InjectBugReportDialog implements IHookPointInjector {
    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.FuncSelectDialog", classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    Log.i("DgLabUnlocker", "SafeButtonHook: replacing onClickListener");
                    Dialog dialog = (Dialog) param.thisObject;
                    Field btnField = dialog.getClass().getDeclaredField("safe_button");
                    btnField.setAccessible(true);
                    View btn = (View) btnField.get(dialog);
                    if (btn != null) {
                        btn.setOnClickListener(e -> {
                            try {
                                GlobalVariables.showSettingsDialog(btn.getContext());
                            } catch (ReflectiveOperationException ex) {
                                Log.e("DgLabUnlocker", "Failed open settings dialog", ex);
                            }
                        });
                    } else {
                        Log.e("DgLabUnlocker", "SafeButtonHook: button is null");
                    }
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in InjectBugReportDialog FuncSelectDialog", e);
                }
            }
        });
        XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.widget.BugDialog", classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    Log.i("DgLabUnlocker", "BugDialogHook: replacing layout");
                    Dialog dialog = (Dialog) param.thisObject;
                    dialog.setContentView(ConfigurationDialog.createSettingsPanel(dialog.getContext()));
                } catch (Exception e) {
                    Log.e("DgLabUnlocker", "An error occurred in InjectBugReportDialog BugDialog", e);
                }
            }
        });
    }
}
