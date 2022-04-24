package sakura.kooi.dglabunlocker.variables;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;

import sakura.kooi.dglabunlocker.remote.WebsocketRPC;

public class ModuleSettings {
    public static boolean unlockRemoteMaxStrength = false;
    public static boolean deviceProtection = false;
    public static boolean enforceRemoteMaxStrength = false;
    public static boolean enforceLocalStrength = false;
    public static boolean bypassRemoteMaxStrength = false;
    public static boolean fixDoubleBug = false;
    public static boolean randomQrCode = false;

    public static SharedPreferences sharedPref;

    public static void loadConfiguration(Context context) {
        sharedPref = context.getSharedPreferences("dglabunlocker", Context.MODE_PRIVATE);

        unlockRemoteMaxStrength = sharedPref.getBoolean("unlockRemoteMaxStrength", false);
        enforceLocalStrength = sharedPref.getBoolean("enforceLocalStrength", false);
        deviceProtection = sharedPref.getBoolean("deviceProtection", true);
        enforceRemoteMaxStrength = sharedPref.getBoolean("enforceRemoteMaxStrength", false);
        bypassRemoteMaxStrength = sharedPref.getBoolean("bypassRemoteMaxStrength", false);
        randomQrCode = sharedPref.getBoolean("randomQrCode", false);
        if (sharedPref.getBoolean("remoteRpc", false)) {
            WebsocketRPC.update(true, null);
        }
    }

    public static void showSettingsDialog(Context context) throws ReflectiveOperationException {
        try {
            ((Dialog) Accessors.constructorBugDialog.invoke(context)).show();
        } catch (Throwable throwable) {
            throw new ReflectiveOperationException(throwable);
        }
    }
}
