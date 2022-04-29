package sakura.kooi.dglabunlocker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashSet;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.PreLoadHookSettingsDialog;
import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.utils.ModuleException;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.HookRegistry;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;
import sakura.kooi.dglabunlocker.variables.ResourceInject;
import sakura.kooi.dglabunlocker.ver.AbstractVersionedCompatibilityProvider;
import sakura.kooi.dglabunlocker.ver.Version126;
import sakura.kooi.dglabunlocker.ver.Version131;

public class XposedModuleInit implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
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
        ModuleUtils.applicationContext = context;

        // region load configuration
        Log.i("DgLabUnlocker", "Hook Loading: Injecting settings dialog...");
        try {
            PreLoadHookSettingsDialog.apply(context, classLoader);
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred while injecting settings dialog", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }
        // endregion
        // region detect app version and initialize reflection
        try {
            detectAppVersion(context).initializeAccessors(classLoader);
            ModuleUtils.testFieldWorks(); // FIXME move to features

            Log.i("DgLabUnlocker", "Hook Loading: Fields lookup done");
            StatusDialog.fieldsLookup = true;
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred in initializeAccessors()", e);
            Toast.makeText(context, "DG-Lab Unlocker 加载失败", Toast.LENGTH_LONG).show();
            return;
        }
        // endregion

        // region load features and collect hook classes
        HashSet<Class<? extends AbstractHook<?>>> hookClasses = new HashSet<>();
        for (Class<? extends AbstractFeature> featureClass : HookRegistry.features) {
            try {
                AbstractFeature feature = featureClass.newInstance();
                hookClasses.addAll(feature.getRequiredHooks());
                HookRegistry.featureInstances.put(featureClass, feature);
            } catch (Exception e) {
                ModuleUtils.logError("DgLabUnlocker", "Could not initialize feature " + featureClass.getName(), e);
            }
        }
        // endregion
        // region inject hooks
        for (Class<? extends AbstractHook<?>> hookClass : hookClasses) {
            try {
                AbstractHook<?> hook = hookClass.newInstance();
                hook.applyHook(context, classLoader);
                HookRegistry.hookInstances.put(hookClass, hook);
            } catch (Exception e) {
                ModuleUtils.logError("DgLabUnlocker", "Could not apply hook " + hookClass.getName(), e);
            }
        }
        // endregion
        // region register hook handlers
        for (Class<? extends AbstractFeature> featureClass : HookRegistry.features) {
            AbstractFeature feature = null;
            try {
                feature = HookRegistry.featureInstances.get(featureClass);
                if (feature == null)
                    continue;

                for (Class<? extends AbstractHook<?>> hookClass : feature.getRequiredHooks()) {
                    AbstractHook<?> hook = HookRegistry.hookInstances.get(hookClass);
                    if (hook == null)
                        throw new ModuleException("Cannot register feature " + featureClass.getName() + " to hook " + hookClass.getName() + ": hook not initialized");
                    hook.registerHandler(feature);
                }
            } catch (Exception e) {
                ModuleUtils.logError("DgLabUnlocker", "Could not register hook handler for feature " + featureClass.getName(), e);
                if (feature != null)
                    feature.setWorking(false);
            }
        }
        // endregion
        // region load configurations
        Log.i("DgLabUnlocker", "Hook Loading: Loading configuration...");
        try {
            ModuleSettings.loadConfiguration(context);
            Log.i("DgLabUnlocker", "Hook Loading: Configuration loaded");
        } catch (Exception e) {
            ModuleUtils.logError("DgLabUnlocker", "An error occurred in loadConfiguration()", e);
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