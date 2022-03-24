package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Field;

import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.injector.InjectBluetoothServiceReceiver;

public class HookDeviceProtection implements InjectBluetoothServiceReceiver.BluetoothServiceDataHandler {
    @Override
    public void beforeDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {

    }

    @Override
    public void afterDataUpdate(Context context, int localStrengthA, int totalStrengthA, int remoteStrengthA, int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {
        boolean blocked = false;
        blocked = blocked || checkStrength(localStrengthA, GlobalVariables.localStrengthA);
        blocked = blocked || checkStrength(totalStrengthA, GlobalVariables.totalStrengthA);
        blocked = blocked || checkStrength(remoteStrengthA, GlobalVariables.remoteStrengthA);
        blocked = blocked || checkStrength(localStrengthB, GlobalVariables.localStrengthB);
        blocked = blocked || checkStrength(totalStrengthB, GlobalVariables.totalStrengthB);
        blocked = blocked || checkStrength(remoteStrengthB, GlobalVariables.remoteStrengthB);

        if (blocked) {
            Toast.makeText(context, "拦截了一次非法超高强度", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkStrength(int value, Field field) throws ReflectiveOperationException{
        if (value > 296) {
            field.setInt(null, 0);
            return true;
        }
        return false;
    }
}
