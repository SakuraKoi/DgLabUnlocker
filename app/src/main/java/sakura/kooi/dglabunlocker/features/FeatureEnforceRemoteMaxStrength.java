package sakura.kooi.dglabunlocker.features;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureEnforceRemoteMaxStrength {
    public static final FeatureEnforceRemoteMaxStrength INSTANCE = new FeatureEnforceRemoteMaxStrength();

    private FeatureEnforceRemoteMaxStrength() {
    }

    public int handleStrengthA(Context context, int strength) throws ReflectiveOperationException {
        int max = Accessors.maxStrengthA.get() + 30;
        Log.d("DgLabUnlocker", "Bluetooth sending A " + strength + " " + max);
        if (strength > max) {
            Log.w("DgLabUnlocker", "Enforced max strength A: " + strength + " > " + max);
            strength = max;
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
        }
        return strength;
    }

    public int handleStrengthB(Context context, int strength) throws ReflectiveOperationException {
        int max = Accessors.maxStrengthB.get() + 30;
        Log.d("DgLabUnlocker", "Bluetooth sending B " + strength + " " + max);
        if (strength > max) {
            Log.w("DgLabUnlocker", "Enforced max strength B: " + strength + " > " + max);
            strength = max;
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
        }
        return strength;
    }
}
