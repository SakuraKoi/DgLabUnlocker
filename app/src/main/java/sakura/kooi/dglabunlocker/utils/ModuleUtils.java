package sakura.kooi.dglabunlocker.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import de.robv.android.xposed.XposedBridge;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class ModuleUtils {
    public static Context applicationContext;

    public static int getAppVersion(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {}
        return versionCode;
    }

    // 不优雅, 能用就行.jpg
    public static void logError(String tag, String s) {
        XposedBridge.log(s);
        Log.e(tag, s);
    }

    public static void logError(String tag, String s, Exception e) {
        XposedBridge.log(s);
        XposedBridge.log(e);
        Log.e(tag, s, e);
    }

    public static void broadcastUiUpdate() throws ReflectiveOperationException {
        Intent broadcast = new Intent();
        broadcast.setAction("com.bjsm.dungeonlab.ble.abpower");
        broadcast.putExtra("apower", Accessors.totalStrengthA.get());
        broadcast.putExtra("bpower", Accessors.totalStrengthB.get());
        applicationContext.sendBroadcast(broadcast);
    }
}
