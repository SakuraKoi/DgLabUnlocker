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

import sakura.kooi.dglabunlocker.injector.InjectStrengthAddButton;

public class HookBypassRemoteMaxStrength implements InjectStrengthAddButton.StrengthAddHandler {
    private final AtomicInteger realMaxA = new AtomicInteger();
    private final AtomicInteger realMaxB = new AtomicInteger();
    @Override
    public void beforeStrengthA(Context context) throws IllegalAccessException { // totalStrengthA >= localStrengthA + maxStrengthA
        if (isRemote.getBoolean(null)) {
            realMaxA.set(maxStrengthA.getInt(null));
            realMaxB.set(maxStrengthB.getInt(null));
            maxStrengthA.setInt(null, 276);
        }
    }

    @Override
    public void beforeStrengthB(Context context) throws IllegalAccessException { // totalStrengthB >= localStrengthB + maxStrengthB
        if (isRemote.getBoolean(null)) {
            realMaxA.set(maxStrengthA.getInt(null));
            realMaxB.set(maxStrengthB.getInt(null));
            maxStrengthB.setInt(null, 276);
        }
    }

    @Override
    public void afterStrengthA(Context context) throws IllegalAccessException {
        if (isRemote.getBoolean(null)) {
            if (totalStrengthA.getInt(null) >= localStrengthA.getInt(null) + realMaxA.get())
                Toast.makeText(context, "绕过远程最大强度 A: " + realMaxA.get(), Toast.LENGTH_SHORT).show();
            maxStrengthA.setInt(null, realMaxA.get());
            maxStrengthB.setInt(null, realMaxB.get());
        }
    }

    @Override
    public void afterStrengthB(Context context) throws IllegalAccessException {
        if (isRemote.getBoolean(null)) {
            if (totalStrengthB.getInt(null) >= localStrengthB.getInt(null) + realMaxB.get())
                Toast.makeText(context, "绕过远程最大强度 B: " + realMaxB.get(), Toast.LENGTH_SHORT).show();
            maxStrengthA.setInt(null, realMaxA.get());
            maxStrengthB.setInt(null, realMaxB.get());
        }
    }
}
