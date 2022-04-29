package sakura.kooi.dglabunlocker.variables;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.FeatureUnlockRemoteMax;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public class HookRegistry {
    // 调试用的实验性功能菜单开关
    public static final boolean ENABLE_DEV_FEATURE = false;

    public static final List<Class<? extends AbstractFeature>> features = Collections.unmodifiableList(Arrays.asList(
            FeatureUnlockRemoteMax.class
    ));

    public static final Map<Class<? extends AbstractFeature>, AbstractFeature> featureInstances = new ConcurrentHashMap<>();
    public static final Map<Class<? extends AbstractHook<?>>, AbstractHook<?>> hookInstances = new ConcurrentHashMap<>();
}
