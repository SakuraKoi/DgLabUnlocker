package sakura.kooi.dglabunlocker;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.ver.IDgLabField;
import sakura.kooi.dglabunlocker.ver.Version126;
import sakura.kooi.dglabunlocker.ver.Version131;

public class GlobalVariables {

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

    public static Constructor<?> constBugDialog;

    public static String classBluetoothServiceDecoder;
    public static String methodBluetoothServiceDecoder_Decode;
    public static String classBluetoothService;
    public static String methodBluetoothServiceUpdateStrength;
    public static String[] classTouchListeners;
    public static List<String> classControlledTouchListeners;

    public static boolean isRemote(Object homeActivity) throws IllegalAccessException {
        return (isRemote.getBoolean(null) && fieldBluetoothServiceIsController.getInt(fieldHomeActivityBluetoothService.get(homeActivity)) == 1);
    }

    public static void showSettingsDialog(Context context) throws ReflectiveOperationException {
        Dialog dialog = (Dialog) constBugDialog.newInstance(context);
        dialog.show();
    }

    public static void initDgLabFields(ClassLoader classLoader, Context context) throws ReflectiveOperationException {
        IDgLabField versionedFieldInitializer;
        versionedFieldInitializer = detectDgLabVersion(context);
        versionedFieldInitializer.initDgLabFields(classLoader, context);

        testFieldWorks();
    }

    private static void testFieldWorks() throws ReflectiveOperationException {
        localStrengthA.getInt(null);
        totalStrengthA.getInt(null);
        remoteStrengthA.getInt(null);
        localStrengthB.getInt(null);
        totalStrengthB.getInt(null);
        remoteStrengthB.getInt(null);

        maxStrengthA.getInt(null);
        maxStrengthB.getInt(null);

        isRemote.getBoolean(null);
    }

    @NonNull
    private static IDgLabField detectDgLabVersion(Context context) {
        IDgLabField versionedFieldInitializer;
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {}
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
}
