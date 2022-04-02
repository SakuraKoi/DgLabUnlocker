package sakura.kooi.dglabunlocker;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class GlobalVariables {
    public static String modulePath;
    public static Drawable resInjectSettingsBackground;

    public static boolean unlockRemoteMaxStrength = false;
    public static boolean fixDoubleBug = false;
    public static boolean deviceProtection = true;
    public static boolean enforceRemoteMaxStrength = false;
    public static boolean bypassRemoteMaxStrength = false;

    public static Field localStrengthA;
    public static Field totalStrengthA;
    public static Field remoteStrengthA;
    public static Field localStrengthB;
    public static Field totalStrengthB;
    public static Field remoteStrengthB;

    public static Field maxStrengthA;
    public static Field maxStrengthB;

    public static Field isRemote;

    public static Field fieldHomeActivityBluetoothService;
    public static Field fieldBluetoothServiceIsController;
    public static Drawable resInjectSwitchCloseThumb;
    public static Drawable resInjectSwitchOpenThumb;
    public static Drawable resInjectSwitchCloseTrack;
    public static Drawable resInjectSwitchOpenTrack;
    public static SharedPreferences sharedPref;
    private static Constructor<?> constBugDialog;

    public static boolean isRemote(Object homeActivity) throws IllegalAccessException {
        return (isRemote.getBoolean(null) && fieldBluetoothServiceIsController.getInt(fieldHomeActivityBluetoothService.get(homeActivity)) == 1);
    }

    public static void showSettingsDialog(Context context) throws ReflectiveOperationException {
        Dialog dialog = (Dialog) constBugDialog.newInstance(context);
        dialog.show();
    }

    public static void initDgLabFields(ClassLoader classLoader, Context context) throws ReflectiveOperationException {
        Class<?> clsGlobalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);
        localStrengthA = lookupField(clsGlobalVariable, "ab");
        totalStrengthA = lookupField(clsGlobalVariable, "V");
        remoteStrengthA = lookupField(clsGlobalVariable, "X");

        localStrengthB = lookupField(clsGlobalVariable, "ac");
        totalStrengthB = lookupField(clsGlobalVariable, "W");
        remoteStrengthB = lookupField(clsGlobalVariable, "Y");

        maxStrengthA = lookupField(clsGlobalVariable, "Z");
        maxStrengthB = lookupField(clsGlobalVariable, "aa");

        isRemote = lookupField(clsGlobalVariable, "aZ");


        Class<?> clsHomeActivity = Class.forName("com.bjsm.dungeonlab.ui.activity.HomeActivity", true, classLoader);
        fieldHomeActivityBluetoothService = lookupField(clsHomeActivity, "aR");
        Class<?> clsBluetoothService = Class.forName("com.bjsm.dungeonlab.service.BlueToothService", true, classLoader);
        fieldBluetoothServiceIsController = lookupField(clsBluetoothService, "S");
        Class<?> clsBugDialog = Class.forName("com.bjsm.dungeonlab.widget.BugDialog", true, classLoader);
        constBugDialog = clsBugDialog.getDeclaredConstructor(Context.class);

        sharedPref = context.getSharedPreferences("dglabunlocker", Context.MODE_PRIVATE);

        unlockRemoteMaxStrength = sharedPref.getBoolean("unlockRemoteMaxStrength", false);
        fixDoubleBug = sharedPref.getBoolean("fixDoubleBug", false);
        deviceProtection = sharedPref.getBoolean("deviceProtection", false);
        enforceRemoteMaxStrength = sharedPref.getBoolean("enforceRemoteMaxStrength", false);
        bypassRemoteMaxStrength = sharedPref.getBoolean("bypassRemoteMaxStrength", false);
    }

    private static Field lookupField(Class<?> clazz, String name) throws ReflectiveOperationException {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException e) {
            Log.e("DgLabUnlocker", "Failed lookup field " + clazz.getName() + " : " + name, e);
            throw e;
        }
    }
}
