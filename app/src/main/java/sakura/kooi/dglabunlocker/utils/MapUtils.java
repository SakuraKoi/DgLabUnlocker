package sakura.kooi.dglabunlocker.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import sakura.kooi.dglabunlocker.injector.IHookPointInjector;

public class MapUtils {
    public static Map<Class<? extends IHookPointInjector>, Runnable> of(Map.Entry<Class<? extends IHookPointInjector>, Runnable>... entries) {
        HashMap<Class<? extends IHookPointInjector>, Runnable> map = new HashMap<>();
        for (Map.Entry<Class<? extends IHookPointInjector>, Runnable> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static Map.Entry<Class<? extends IHookPointInjector>, Runnable> entry(Class<? extends IHookPointInjector> key, Runnable val) {
        return new AbstractMap.SimpleEntry<>(key, val);
    }
}
