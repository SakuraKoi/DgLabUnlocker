package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class HookEnforceLocalStrength {
    public static final HookEnforceLocalStrength INSTANCE = new HookEnforceLocalStrength();

    private HookEnforceLocalStrength() {
    }

    private AtomicInteger localStrengthA = new AtomicInteger();
    private AtomicInteger localStrengthB = new AtomicInteger();

    public void handleLocalStrengthChange(Context context) throws ReflectiveOperationException {
        this.localStrengthA.set(Accessors.localStrengthA.get());
        this.localStrengthB.set(Accessors.localStrengthB.get());
    }

    public void afterStrengthDecode(Context context) throws ReflectiveOperationException {
        Accessors.localStrengthA.set(localStrengthA.get());
        Accessors.localStrengthB.set(localStrengthB.get());
    }
}
