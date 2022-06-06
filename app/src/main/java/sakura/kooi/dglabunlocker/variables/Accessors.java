package sakura.kooi.dglabunlocker.variables;

import android.widget.TextView;

import java.lang.invoke.MethodHandle;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;

public class Accessors {
    public static MethodHandle constructorBugDialog;

    public static FieldAccessor<Integer> localStrengthA;
    public static FieldAccessor<Integer> totalStrengthA;
    public static FieldAccessor<Integer> maxStrengthA;
    public static FieldAccessor<Integer> remoteStrengthA;
    public static FieldAccessor<Integer> localStrengthB;
    public static FieldAccessor<Integer> totalStrengthB;
    public static FieldAccessor<Integer> maxStrengthB;
    public static FieldAccessor<Integer> remoteStrengthB;

    public static FieldAccessor<TextView> textHomeActivityStrengthA;
    public static FieldAccessor<TextView> textHomeActivityStrengthB;

    public static boolean isRemote;
}
