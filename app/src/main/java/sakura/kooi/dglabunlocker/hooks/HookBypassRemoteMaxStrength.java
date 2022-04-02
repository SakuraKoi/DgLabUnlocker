package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.GlobalVariables.isRemote;
import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.localStrengthB;
import static sakura.kooi.dglabunlocker.GlobalVariables.maxStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.maxStrengthB;
import static sakura.kooi.dglabunlocker.GlobalVariables.totalStrengthA;
import static sakura.kooi.dglabunlocker.GlobalVariables.totalStrengthB;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class HookBypassRemoteMaxStrength {
    public static final HookBypassRemoteMaxStrength INSTANCE = new HookBypassRemoteMaxStrength();
    private final AtomicInteger realMaxA = new AtomicInteger();
    private final AtomicInteger realMaxB = new AtomicInteger();
    private HookBypassRemoteMaxStrength() {
    }

    public void beforeStrength(Context context) throws IllegalAccessException { // totalStrengthA >= localStrengthA + maxStrengthA
        if (isRemote.getBoolean(null)) {
            realMaxA.set(maxStrengthA.getInt(null));
            realMaxB.set(maxStrengthB.getInt(null));
            maxStrengthA.setInt(null, 276);
            maxStrengthB.setInt(null, 276);
        }
    }

    public void afterStrength(Context context) throws IllegalAccessException {
        if (isRemote.getBoolean(null)) {
            boolean bypassedA = false;
            if (totalStrengthA.getInt(null) >= localStrengthA.getInt(null) + realMaxA.get())
                bypassedA = true;

            boolean bypassedB = false;
            if (totalStrengthB.getInt(null) >= localStrengthB.getInt(null) + realMaxB.get())
                bypassedB = true;

            if (bypassedA || bypassedB) {
                StringBuilder sb = new StringBuilder();
                sb.append("绕过远程最大强度");
                if (bypassedA)
                    sb.append(" A: ").append(realMaxA.get());
                if (bypassedB)
                    sb.append(" B: ").append(realMaxB.get());
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
            }

            maxStrengthA.setInt(null, realMaxA.get());
            maxStrengthB.setInt(null, realMaxB.get());
        }
    }
}
