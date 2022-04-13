package sakura.kooi.dglabunlocker.hooks;

import static sakura.kooi.dglabunlocker.GlobalVariables.classControlledTouchListeners;

import android.content.Context;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import sakura.kooi.dglabunlocker.GlobalVariables;

public class HookEnforceLocalStrength {
    public static final HookEnforceLocalStrength INSTANCE = new HookEnforceLocalStrength();
    private HookEnforceLocalStrength() {}

    private AtomicInteger localStrengthA = new AtomicInteger();
    private AtomicInteger localStrengthB = new AtomicInteger();

    public void handleLocalStrengthChange(Context context, AtomicInteger localStrengthA, AtomicInteger localStrengthB) {
        if (Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .anyMatch(clazz -> classControlledTouchListeners.contains(clazz))) {
            this.localStrengthA.set(localStrengthA.get());
            this.localStrengthB.set(localStrengthB.get());
        } else {
            localStrengthA.set(this.localStrengthA.get());
            localStrengthB.set(this.localStrengthB.get());
        }
    }

    public void afterStrengthDecode(Context context) throws IllegalAccessException {
        GlobalVariables.localStrengthA.setInt(null, localStrengthA.get());
        GlobalVariables.localStrengthB.setInt(null, localStrengthB.get());
    }
}
