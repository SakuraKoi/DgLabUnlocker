package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;

import sakura.kooi.dglabunlocker.injector.InjectProtocolStrengthDecode;

public class HookDeviceProtection implements InjectProtocolStrengthDecode.ProtocolStrengthHandler {
    public static final HookDeviceProtection INSTANCE = new HookDeviceProtection();

    private HookDeviceProtection() {
    }

    @Override
    public int handleStrengthA(Context context, int strength) throws ReflectiveOperationException {
        if (strength > 306) {
            strength = 306;
            Log.e("DgLabUnlocker", "Protected reset too high strength");
        }
        return strength;
    }

    @Override
    public int handleStrengthB(Context context, int strength) throws ReflectiveOperationException {
        if (strength > 306) {
            strength = 306;
            Log.e("DgLabUnlocker", "Protected reset too high strength");
        }
        return strength;
    }
}
