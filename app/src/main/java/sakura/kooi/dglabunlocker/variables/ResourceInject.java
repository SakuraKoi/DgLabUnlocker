package sakura.kooi.dglabunlocker.variables;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import sakura.kooi.dglabunlocker.R;
import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;

public class ResourceInject {
    public static String modulePath;

    public static Drawable dialogSettingsBackground;
    public static Drawable switchCloseThumb;
    public static Drawable switchOpenThumb;
    public static Drawable switchCloseTrack;
    public static Drawable switchOpenTrack;
    public static Drawable buttonBackground;
    public static Drawable buttonSettingIcon;

    public static Drawable checkOn;
    public static Drawable checkOff;

    @SuppressWarnings("deprecation")
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void doResourceInject(XC_InitPackageResources.InitPackageResourcesParam resparam) {
        Log.i("DgLabUnlocker", "Resource Loading: found target app " + resparam.packageName);

        XModuleResources modRes = XModuleResources.createInstance(modulePath, resparam.res);
        try {
            ResourceInject.dialogSettingsBackground = modRes.getDrawable(R.drawable.settings_bg);
            ResourceInject.switchCloseThumb = modRes.getDrawable(R.drawable.switch_close_thumb);
            ResourceInject.switchOpenThumb = modRes.getDrawable(R.drawable.switch_open_thumb);
            ResourceInject.switchCloseTrack = modRes.getDrawable(R.drawable.switch_close_track);
            ResourceInject.switchOpenTrack = modRes.getDrawable(R.drawable.switch_open_track);
            ResourceInject.buttonBackground = modRes.getDrawable(R.drawable.button_yellow);
            ResourceInject.checkOn = modRes.getDrawable(R.drawable.check_on);
            ResourceInject.checkOff = modRes.getDrawable(R.drawable.check_off);
            ResourceInject.buttonSettingIcon = modRes.getDrawable(R.drawable.icon_settings);
            StatusDialog.resourceInjection = true;
        } catch (Resources.NotFoundException e) {
            ModuleUtils.logError("DgLabUnlocker", "resources cannot be found from XModuleResources, still try inject...", e);
        }

        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "anquanxuzhi2", "模块设置");
        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "question_feedback", "DG-Lab Unlocker 设置");
        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "email_con", "模块设置替换失败, 请检查Logcat日志");
    }
}
