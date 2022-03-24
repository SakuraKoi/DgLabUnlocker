package sakura.kooi.dglabunlocker;

import java.lang.reflect.Field;

public class GlobalVariables {
    public static boolean unlockRemoteMaxStrength = false;
    public static boolean fixDoubleBug = false;

    public static Field localStrengthA;
    public static Field totalStrengthA;
    public static Field remoteStrengthA;
    public static Field localStrengthB;
    public static Field totalStrengthB;
    public static Field remoteStrengthB;

    public static void initDgLabFields(ClassLoader classLoader) throws ReflectiveOperationException{
        Class<?> globalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);
        localStrengthA = globalVariable.getDeclaredField("ab");
        totalStrengthA = globalVariable.getDeclaredField("V");
        remoteStrengthA = globalVariable.getDeclaredField("X");
        localStrengthB = globalVariable.getDeclaredField("ac");
        totalStrengthB = globalVariable.getDeclaredField("W");
        remoteStrengthB = globalVariable.getDeclaredField("Y");
        localStrengthA.setAccessible(true);
        localStrengthB.setAccessible(true);
        totalStrengthA.setAccessible(true);
        totalStrengthB.setAccessible(true);
        remoteStrengthA.setAccessible(true);
        remoteStrengthB.setAccessible(true);
    }
}
