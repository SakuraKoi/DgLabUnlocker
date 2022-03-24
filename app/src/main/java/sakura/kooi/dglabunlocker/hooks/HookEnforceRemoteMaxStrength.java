package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.widget.Toast;

import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.injector.InjectBluetoothServiceReceiver;

public class HookEnforceRemoteMaxStrength implements InjectBluetoothServiceReceiver.BluetoothServiceDataHandler {
    @Override
    public void beforeDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {

    }

    @Override
    public void afterDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {
        boolean blocked = false;
        int maxA = GlobalVariables.maxStrengthA.getInt(null);
        if (totalStrengthA > maxA + 20) {
            GlobalVariables.totalStrengthA.setInt(null, maxA);
            blocked = true;
        }
        int maxB = GlobalVariables.maxStrengthB.getInt(null);
        if (totalStrengthB > maxB + 20) {
            GlobalVariables.totalStrengthB.setInt(null, maxB);
            blocked = true;
        }
        if (blocked) {
            Toast.makeText(context, "强制限制了一次远程最大强度", Toast.LENGTH_SHORT).show();
        }
    }
}
