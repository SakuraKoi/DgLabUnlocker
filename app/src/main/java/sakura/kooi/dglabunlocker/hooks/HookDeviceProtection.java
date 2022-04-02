package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;

public class HookDeviceProtection {
    public static final HookDeviceProtection INSTANCE = new HookDeviceProtection();

    private HookDeviceProtection() {
    }

    public int handleStrengthA(Context context, int strength) throws ReflectiveOperationException {
        if (strength > 306) {
            strength = 306;
            Log.e("DgLabUnlocker", "Protected reset too high strength");
        }
        return strength;
    }

    public int handleStrengthB(Context context, int strength) throws ReflectiveOperationException {
        if (strength > 306) {
            strength = 306;
            Log.e("DgLabUnlocker", "Protected reset too high strength");
        }
        return strength;
    }
}
