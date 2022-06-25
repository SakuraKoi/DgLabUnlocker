package sakura.kooi.dglabunlocker.variables;

import android.widget.TextView;

import java.util.List;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;

public class Accessors {
    public static boolean isRemote;

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
    public static FieldAccessor<Boolean> isRemoteControlling;

    public static FieldAccessor<List<Object>> listWaveData;
    public static Class<?> classWaveClassicBean;
    public static FieldAccessor<Integer> waveIsClassic;
    public static FieldAccessor<String> waveName;
}
