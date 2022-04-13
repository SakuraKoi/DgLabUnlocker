package sakura.kooi.dglabunlocker.utils;

import static sakura.kooi.dglabunlocker.variables.Accessors.isRemote;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.remoteStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.remoteStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthB;

import android.content.Context;
import android.content.pm.PackageManager;

public class ModuleUtils {
    public static int getAppVersion(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {}
        return versionCode;
    }

    public static void testFieldWorks() throws ReflectiveOperationException {
        localStrengthA.get();
        totalStrengthA.get();
        remoteStrengthA.get();
        localStrengthB.get();
        totalStrengthB.get();
        remoteStrengthB.get();
        maxStrengthA.get();
        maxStrengthB.get();
        isRemote.get();
    }
}
