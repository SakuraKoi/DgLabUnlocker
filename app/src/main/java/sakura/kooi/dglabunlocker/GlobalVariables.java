package sakura.kooi.dglabunlocker;

import java.lang.reflect.Field;

public class GlobalVariables {
    public static boolean unlockRemoteMaxStrength = false;
    public static boolean fixDoubleBug = false;

    public static boolean deviceProtection = true;
    public static boolean enforceRemoteMaxStrength = false;
    public static boolean bypassRemoteMaxStrength = false;

    public static Field localStrengthA;
    public static Field totalStrengthA;
    public static Field remoteStrengthA;
    public static Field localStrengthB;
    public static Field totalStrengthB;
    public static Field remoteStrengthB;

    public static Field maxStrengthA;
    public static Field maxStrengthB;

    public static void initDgLabFields(ClassLoader classLoader) throws ReflectiveOperationException {
        Class<?> globalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);
        localStrengthA = lookupField(globalVariable, "ab");
        localStrengthA = lookupField(globalVariable, "ab");
        totalStrengthA = lookupField(globalVariable, "V");
        remoteStrengthA = lookupField(globalVariable, "X");
        localStrengthB = lookupField(globalVariable, "ac");
        totalStrengthB = lookupField(globalVariable, "W");
        remoteStrengthB = lookupField(globalVariable, "Y");

        maxStrengthA = lookupField(globalVariable, "Z");
        maxStrengthB = lookupField(globalVariable, "aa");
    }

    private static Field lookupField(Class<?> clazz, String name) throws ReflectiveOperationException {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }
}
