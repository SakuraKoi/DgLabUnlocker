package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.util.Log;

public class FeatureDeviceProtection {
    public static final FeatureDeviceProtection INSTANCE = new FeatureDeviceProtection();

    private FeatureDeviceProtection() {
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
