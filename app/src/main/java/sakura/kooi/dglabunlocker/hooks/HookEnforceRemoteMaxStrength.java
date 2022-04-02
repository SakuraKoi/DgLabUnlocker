package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.injector.InjectProtocolStrengthDecode;

public class HookEnforceRemoteMaxStrength implements InjectProtocolStrengthDecode.ProtocolStrengthHandler {
    public static final HookEnforceRemoteMaxStrength INSTANCE = new HookEnforceRemoteMaxStrength();
    private HookEnforceRemoteMaxStrength() {}

    @Override
    public int handleStrengthA(Context context, int strength) throws ReflectiveOperationException {
        int max = GlobalVariables.maxStrengthA.getInt(null) + 30;
        Log.d("DgLabUnlocker", "Bluetooth sending A " + strength + " " + max);
        if (strength > max) {
            Log.w("DgLabUnlocker", "Enforced max strength A: " + strength + " > " + max);
            strength = max;
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
        }
        return strength;
    }

    @Override
    public int handleStrengthB(Context context, int strength) throws ReflectiveOperationException {
        int max = GlobalVariables.maxStrengthB.getInt(null) + 30;
        Log.d("DgLabUnlocker", "Bluetooth sending B " + strength + " " + max);
        if (strength > max) {
            Log.w("DgLabUnlocker", "Enforced max strength B: " + strength + " > " + max);
            strength = max;
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
        }
        return strength;
    }
}
