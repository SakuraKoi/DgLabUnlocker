package sakura.kooi.dglabunlocker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import sakura.kooi.dglabunlocker.injector.InjectBluetoothServiceReceiver;
import sakura.kooi.dglabunlocker.injector.InjectBugReportDialog;
import sakura.kooi.dglabunlocker.injector.InjectRemoteSettingsDialog;

public class XposedModuleInit implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.bjsm.dungeonlab"))
            return;
        Log.i("DgLabUnlocker", "Loading: found target app " + lpparam.packageName);
        try {
            Class.forName("com.wrapper.proxyapplication.WrapperProxyApplication", false, lpparam.classLoader);

            XposedHelpers.findAndHookMethod("com.wrapper.proxyapplication.WrapperProxyApplication", lpparam.classLoader,
                    "attachBaseContext", Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Context context = (Context) param.args[0];
                            ClassLoader classLoader = context.getClassLoader();
                            onAppLoaded(context, classLoader);
                        }
                    });
        } catch (ClassNotFoundException e) {
            Log.i("DgLabUnlocker", "Possible unpacked app, try hook activity directly");
            XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.global", lpparam.classLoader, "onCreate", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Context context = (Context) param.thisObject;
                    ClassLoader classLoader = context.getClassLoader();
                    onAppLoaded(context, classLoader);
                }
            });
        }
    }

    private void onAppLoaded(Context context, ClassLoader classLoader) {
        Log.i("DgLabUnlocker", "App loaded! Applying hooks...");
        try {
            GlobalVariables.initDgLabFields(classLoader);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "An error occurred while initDgLabFields()", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            InjectRemoteSettingsDialog.apply(context, classLoader);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "Could not apply InjectRemoteSettingsDialog", e);
        }

        try {
            InjectBluetoothServiceReceiver.apply(context, classLoader);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "Could not apply HookDoubleBugFix", e);
        }

        Toast.makeText(context, "DG-Lab Unlocker 加载成功\nGithub @SakuraKoi/DgLabUnlocker", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.bjsm.dungeonlab"))
            return;
        Log.i("DgLabUnlocker", "Loading resource: found target app " + resparam.packageName);

        boolean success = true;

        try {
            InjectBugReportDialog.apply(resparam);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "Could not apply InjectBugReportDialog", e);
            success = false;
        }
        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "question_feedback", success ? "DG-Lab Unlocker 设置" : "DG-Lab Unlocker 加载失败");
    }
}