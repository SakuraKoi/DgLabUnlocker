package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthB;
import static sakura.kooi.dglabunlocker.GlobalVariables.remoteStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.remoteStrengthB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class HookDoubleBugFix {
    private static AtomicInteger lastStrengthA = new AtomicInteger(0);
    private static AtomicInteger lastStrengthB = new AtomicInteger(0);

    public static void beforeDataUpdate() throws ReflectiveOperationException{
        lastStrengthA.set(localStrengthA.getInt(null));
        lastStrengthB.set(localStrengthB.getInt(null));

    }

    public static void afterDataUpdate(Context context) throws ReflectiveOperationException {
        boolean fixed;

        fixed = tryFixDouble(lastStrengthA, localStrengthA, remoteStrengthA);
        fixed = fixed || tryFixDouble(lastStrengthB, localStrengthB, remoteStrengthB);

        if (fixed) {
            Toast.makeText(context, "拦截了一次强度翻倍BUG", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean tryFixDouble(AtomicInteger lastStrength, Field baseChannel, Field remote) throws ReflectiveOperationException {
        if (remote.getInt(null) == 0) {
            int base = baseChannel.getInt(null);
            if (base > 10 && Math.abs(base - lastStrength.get()) > 1) {
                baseChannel.setInt(null, lastStrength.get());
                Log.e("DgLabUnlocker", "Double bug blocked, set to " + lastStrength.get());
                return true;
            }
        }
        return false;
    }
}