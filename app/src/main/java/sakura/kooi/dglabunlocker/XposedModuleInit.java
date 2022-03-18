package sakura.kooi.dglabunlocker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import sakura.kooi.dglabunlocker.hooks.HookDoubleBugFix;
import sakura.kooi.dglabunlocker.hooks.HookUnlockRemoteMax;

public class XposedModuleInit implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.bjsm.dungeonlab"))
            return;
        Log.i("DgLabUnlocker", "Loading: found target app " + lpparam.packageName);

        XposedHelpers.findAndHookMethod("com.wrapper.proxyapplication.WrapperProxyApplication", lpparam.classLoader,
                "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.args[0];
                ClassLoader classLoader=context.getClassLoader();
                onAppLoaded(context, classLoader);
            }
        });
    }

    private void onAppLoaded(Context context, ClassLoader classLoader) {
        Log.i("DgLabUnlocker", "App loaded! Applying hooks...");
        try {
            HookUnlockRemoteMax.apply(context, classLoader);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "Could not apply HookUnlockRemoteMax", e);
        }
        try {
            HookDoubleBugFix.apply(context, classLoader);
        } catch (Throwable e) {
            Log.e("DgLabUnlocker", "Could not apply HookDoubleBugFix", e);
        }
        Toast.makeText(context, "DG-Lab Unlocker 加载成功\nGithub @SakuraKoi/DgLabUnlocker", Toast.LENGTH_LONG).show();
    }
}