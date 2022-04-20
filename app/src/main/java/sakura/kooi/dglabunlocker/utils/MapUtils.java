package sakura.kooi.dglabunlocker.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import sakura.kooi.dglabunlocker.hooks.IHook;

public class MapUtils {
    public static Map<Class<? extends IHook>, Runnable> of(Map.Entry<Class<? extends IHook>, Runnable>... entries) {
        HashMap<Class<? extends IHook>, Runnable> map = new HashMap<>();
        for (Map.Entry<Class<? extends IHook>, Runnable> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static Map.Entry<Class<? extends IHook>, Runnable> entry(Class<? extends IHook> key, Runnable val) {
        return new AbstractMap.SimpleEntry<>(key, val);
    }
}
