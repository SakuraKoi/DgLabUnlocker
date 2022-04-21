package sakura.kooi.dglabunlocker.features;


import static sakura.kooi.dglabunlocker.variables.Accessors.isRemote;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.localStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.maxStrengthB;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthA;
import static sakura.kooi.dglabunlocker.variables.Accessors.totalStrengthB;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class FeatureBypassRemoteMaxStrength {
    public static final FeatureBypassRemoteMaxStrength INSTANCE = new FeatureBypassRemoteMaxStrength();
    private final AtomicInteger realMaxA = new AtomicInteger();
    private final AtomicInteger realMaxB = new AtomicInteger();

    private FeatureBypassRemoteMaxStrength() {
    }

    public void beforeStrength(Context context) throws ReflectiveOperationException { // totalStrengthA >= localStrengthA + maxStrengthA
        if (isRemote.get()) {
            realMaxA.set(maxStrengthA.get());
            realMaxB.set(maxStrengthB.get());
            maxStrengthA.set(276);
            maxStrengthB.set(276);
        }
    }

    public void afterStrength(Context context) throws ReflectiveOperationException {
        if (isRemote.get()) {
            boolean bypassedA = totalStrengthA.get() >= localStrengthA.get() + realMaxA.get();
            boolean bypassedB = totalStrengthB.get() >= localStrengthB.get() + realMaxB.get();

            if (bypassedA || bypassedB) {
                StringBuilder sb = new StringBuilder();
                sb.append("绕过远程最大强度");
                if (bypassedA)
                    sb.append(" A: ").append(realMaxA.get());
                if (bypassedB)
                    sb.append(" B: ").append(realMaxB.get());
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
            }

            maxStrengthA.set(realMaxA.get());
            maxStrengthB.set(realMaxB.get());
        }
    }
}