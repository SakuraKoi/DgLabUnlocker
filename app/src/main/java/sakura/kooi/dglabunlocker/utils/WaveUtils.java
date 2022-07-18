package sakura.kooi.dglabunlocker.utils;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class WaveUtils {
    public static List<Object> getWaveList(boolean filterClassic) throws ReflectiveOperationException {
        return Accessors.listWaveData.get().stream().filter(wave -> {
            if (filterClassic) {
                try {
                    return Accessors.waveIsClassic.get(wave) == 1;
                } catch (ReflectiveOperationException e) {
                    Log.e("DgLabUnlocker", "An error occurred while checking wave is classic", e);
                    return false;
                }
            } else return true;
        }).collect(Collectors.toList());
    }
    public static Map<String, Object> getWaveListWithName(boolean filterClassic) throws ReflectiveOperationException {
        return getWaveList(filterClassic).stream().collect(Collectors.toMap(wave -> {
            try {
                return Accessors.waveName.get(wave);
            } catch (ReflectiveOperationException e) {
                Log.e("DgLabUnlocker", "An error occurred while getting wave name", e);
                return null;
            }
        }, wave -> wave, (o1, o2) -> o1, LinkedHashMap::new));
    }
}
