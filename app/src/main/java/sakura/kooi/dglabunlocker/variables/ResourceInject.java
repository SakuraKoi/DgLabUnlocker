package sakura.kooi.dglabunlocker.variables;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import sakura.kooi.dglabunlocker.R;

public class ResourceInject {
    public static String modulePath;

    public static Drawable dialogSettingsBackground;
    public static Drawable switchCloseThumb;
    public static Drawable switchOpenThumb;
    public static Drawable switchCloseTrack;
    public static Drawable switchOpenTrack;
    public static Drawable buttonBackground;

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
        } catch (Resources.NotFoundException e) {
            Log.e("DgLabUnlocker", "settings_bg cannot be found from XModuleResources, still try inject...");
        }

        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "anquanxuzhi2", "模块设置");
        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "question_feedback", "DG-Lab Unlocker 设置");
        resparam.res.setReplacement("com.bjsm.dungeonlab", "string", "email_con", "模块设置替换失败, 请检查Logcat日志");
    }
}
