package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.GlobalVariables;

public class HookEnforceLocalStrength {
    public static final HookEnforceLocalStrength INSTANCE = new HookEnforceLocalStrength();

    private HookEnforceLocalStrength() {
    }

    private AtomicInteger localStrengthA = new AtomicInteger();
    private AtomicInteger localStrengthB = new AtomicInteger();

    public void handleLocalStrengthChange(Context context) throws IllegalAccessException {
        this.localStrengthA.set(GlobalVariables.localStrengthA.getInt(null));
        this.localStrengthB.set(GlobalVariables.localStrengthB.getInt(null));
    }

    public void afterStrengthDecode(Context context) throws IllegalAccessException {
        GlobalVariables.localStrengthA.setInt(null, localStrengthA.get());
        GlobalVariables.localStrengthB.setInt(null, localStrengthB.get());
    }
}
