package sakura.kooi.dglabunlocker;

import static sakura.kooi.dglabunlocker.utils.MapUtils.entry;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Map;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import sakura.kooi.dglabunlocker.hooks.HookBugReportDialog;
import sakura.kooi.dglabunlocker.hooks.HookProtocolStrengthDecode;
import sakura.kooi.dglabunlocker.hooks.IHook;
import sakura.kooi.dglabunlocker.hooks.HookBluetoothServiceReceiver;
import sakura.kooi.dglabunlocker.hooks.HookControlledStrengthButton;
import sakura.kooi.dglabunlocker.hooks.HookGuestLogin;
import sakura.kooi.dglabunlocker.hooks.HookRemoteSettingsDialog;
import sakura.kooi.dglabunlocker.hooks.HookStrengthButton;
import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.utils.MapUtils;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;
import sakura.kooi.dglabunlocker.variables.ResourceInject;
import sakura.kooi.dglabunlocker.ver.AbstractVersionedCompatibilityProvider;
import sakura.kooi.dglabunlocker.ver.Version126;
import sakura.kooi.dglabunlocker.ver.Version131;

public class XposedModuleInit implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    // 调试用的实验性功能菜单开关
    public static final boolean ENABLE_DEV_FEATURE = true;

    private final Map<Class<? extends IHook>, Runnable> hookClasses = MapUtils.of(
            entry(HookRemoteSettingsDialog.class, () -> StatusDialog.remoteSettingsDialogInject = true),
            entry(HookBluetoothServiceReceiver.class, () -> StatusDialog.bluetoothDecoderInject = true),
            entry(HookProtocolStrengthDecode.class, () -> StatusDialog.protocolStrengthDecodeInject = true),
            entry(HookStrengthButton.class, () -> StatusDialog.strengthButtonInject = true),
            entry(HookControlledStrengthButton.class, () -> StatusDialog.localStrengthHandlerInject = true),
            entry(HookGuestLogin.class, () -> StatusDialog.guestLogin = true)
    );

    @NonNull
    private static AbstractVersionedCompatibilityProvider detectAppVersion(Context context) {
        AbstractVersionedCompatibilityProvider versionedFieldInitializer;
        int versionCode = ModuleUtils.getAppVersion(context);
        switch (versionCode) {
            case 21:
                versionedFieldInitializer = new Version131();
                StatusDialog.currentLoadedVersion = "1.3.1";
                break;
            case 19:
                versionedFieldInitializer = new Version126();
                StatusDialog.currentLoadedVersion = "1.2.6";
                break;
            default:
                versionedFieldInitializer = new Version126();
                StatusDialog.currentLoadedVersion = null;
        }
        return versionedFieldInitializer;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        Log.i("DgLabUnlocker", "Init Loading: running on app " + lpparam.packageName);
        if (!lpparam.packageName.equals("com.bjsm.dungeonlab")) {
            Log.e("DgLabUnlocker", "Init Loading: app not match! " + lpparam.packageName);
            return;
        }
        Log.i("DgLabUnlocker", "Init Loading: found target app " + lpparam.packageName);
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
            XposedHelpers.findAndHookMethod("com.bjsm.dungeonlab.global.BaseLocalApplication", lpparam.classLoader, "onCreate", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    Context context = (Context) param.thisObject;
                    ClassLoader classLoader = context.getClassLoader();
                    onAppLoaded(context, classLoader);
                }
            });
        }
    }

    private void onAppLoaded(Context context, ClassLoader classLoader) {
        Log.i("DgLabUnlocker", "Hook Loading: App loaded! Trying to take over the world...");

        // region load configuration
        Log.i("DgLabUnlocker", "Hook Loading: Loading configuration...");
        try {
            ModuleSettings.loadConfiguration(context);
            Log.i("DgLabUnlocker", "Hook Loading: Configuration loaded");
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred in loadConfiguration()", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }
        // endregion

        // region inject setting dialog
        try {
            new HookBugReportDialog().apply(context, classLoader);
            Log.i("DgLabUnlocker", "Hook Loading: Settings dialog loaded");
            StatusDialog.moduleSettingsDialogInject = true;
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred in HookBugReportDialog", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }
        // endregion

        // region detect app version and initialize reflection
        try {
            detectAppVersion(context).initializeAccessors(classLoader);
            ModuleUtils.testFieldWorks();

            Log.i("DgLabUnlocker", "Hook Loading: Fields lookup done");
            StatusDialog.fieldsLookup = true;
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred in initializeAccessors()", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }
        // endregion

        // region hooks
        for (Map.Entry<Class<? extends IHook>, Runnable> hookClass : hookClasses.entrySet()) {
            try {
                hookClass.getKey().newInstance().apply(context, classLoader);
                hookClass.getValue().run();
                Log.i("DgLabUnlocker", "Hook Loading: injected " + hookClass.getKey().getName());
            } catch (Exception e) {
                ModuleUtils.logError("DgLabUnlocker", "Could not apply " + hookClass.getKey().getName(), e);
            }
        }
        // endregion

        Toast.makeText(context, "DG-Lab Unlocker 注入成功\nGithub @SakuraKoi/DgLabUnlocker", Toast.LENGTH_LONG).show();
    }

    @Override
    public void initZygote(StartupParam startupParam) {
        ResourceInject.modulePath = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) {
        if (!resparam.packageName.equals("com.bjsm.dungeonlab"))
            return;
        ResourceInject.doResourceInject(resparam);
    }

}