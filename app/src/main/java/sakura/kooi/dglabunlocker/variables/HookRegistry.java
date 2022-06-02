package sakura.kooi.dglabunlocker.variables;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.FeatureBypassRemoteMaxStrength;
import sakura.kooi.dglabunlocker.features.FeatureEnforceLocalStrength;
import sakura.kooi.dglabunlocker.features.FeatureEnforceRemoteMaxStrength;
import sakura.kooi.dglabunlocker.features.FeatureUnlockRemoteMax;
import sakura.kooi.dglabunlocker.features.FeatureWebsocketRpc;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;

public class HookRegistry {
    // 调试用的实验性功能菜单开关
    public static final boolean ENABLE_DEV_FEATURE = false;

    public static final List<Class<? extends AbstractFeature>> features = Collections.unmodifiableList(Arrays.asList(
            FeatureUnlockRemoteMax.class,
            FeatureEnforceLocalStrength.class,
            FeatureEnforceRemoteMaxStrength.class,
            FeatureBypassRemoteMaxStrength.class,
            FeatureWebsocketRpc.class
    ));

    public static final Map<Class<? extends AbstractFeature>, AbstractFeature> featureInstances = Collections.synchronizedMap(new LinkedHashMap<>());
    public static final Map<Class<? extends AbstractHook<?>>, AbstractHook<?>> hookInstances = Collections.synchronizedMap(new LinkedHashMap<>());
    public static final Map<String, Map.Entry<Supplier<String>, Supplier<Integer>>> customStatuses = Collections.synchronizedMap(new LinkedHashMap<>());
}
