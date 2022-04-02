package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.GlobalVariables;
import sakura.kooi.dglabunlocker.injector.InjectBluetoothServiceReceiver;

public class HookDoubleBugFix implements InjectBluetoothServiceReceiver.BluetoothServiceDataHandler {
    public static final HookDoubleBugFix INSTANCE = new HookDoubleBugFix();
    private HookDoubleBugFix() {}

    private AtomicInteger lastStrengthA = new AtomicInteger(0);
    private AtomicInteger lastStrengthB = new AtomicInteger(0);

    public void beforeDataUpdate(Context context,
                                 int localStrengthA, int totalStrengthA, int remoteStrengthA,
                                 int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException{
        lastStrengthA.set(localStrengthA);
        lastStrengthB.set(localStrengthB);
    }
    public void afterDataUpdate(Context context,
                                int localStrengthA, int totalStrengthA, int remoteStrengthA,
                                int localStrengthB, int totalStrengthB, int remoteStrengthB) throws ReflectiveOperationException {
        boolean fixed;

        fixed = tryFixDouble(lastStrengthA, GlobalVariables.localStrengthA, localStrengthA, remoteStrengthA);
        fixed = fixed || tryFixDouble(lastStrengthB, GlobalVariables.localStrengthB, localStrengthB, remoteStrengthB);

        if (fixed) {
            Toast.makeText(context, "拦截了一次强度翻倍BUG", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean tryFixDouble(AtomicInteger lastStrength, Field localStrengthField, int localStrength, int remoteStrength) throws ReflectiveOperationException {
        if (remoteStrength == 0) {
            if (localStrength > 10 && Math.abs(localStrength - lastStrength.get()) > 1) {
                localStrengthField.setInt(null, lastStrength.get());
                Log.w("DgLabUnlocker", "Double bug blocked, set to " + lastStrength.get());
                return true;
            }
        }
        return false;
    }
}