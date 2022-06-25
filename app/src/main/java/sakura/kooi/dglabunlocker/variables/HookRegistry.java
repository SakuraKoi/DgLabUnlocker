package sakura.kooi.dglabunlocker.variables;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import sakura.kooi.dglabunlocker.features.AbstractFeature;
import sakura.kooi.dglabunlocker.features.clickable.FeatureExportWave;
import sakura.kooi.dglabunlocker.features.toggleable.FeatureBypassRemoteMaxStrength;
import sakura.kooi.dglabunlocker.features.toggleable.FeatureEnforceLocalStrength;
import sakura.kooi.dglabunlocker.features.toggleable.FeatureEnforceRemoteMaxStrength;
import sakura.kooi.dglabunlocker.features.toggleable.FeatureUnlockRemoteMax;
import sakura.kooi.dglabunlocker.features.toggleable.FeatureWebsocketRpc;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.preload.HookUpdateClientSide;

public class HookRegistry {
    // 调试用的实验性功能菜单开关
    public static final boolean ENABLE_DEV_FEATURE = false;

    public static final List<Class<? extends AbstractFeature>> features = List.of(
            FeatureUnlockRemoteMax.class,
            FeatureEnforceLocalStrength.class,
            FeatureEnforceRemoteMaxStrength.class,
            FeatureBypassRemoteMaxStrength.class,
            FeatureWebsocketRpc.class,
            FeatureExportWave.class
    );
    public static final List<Class<? extends AbstractHook<?>>> preloadHooks = List.of(
            HookUpdateClientSide.class
    );

    public static final Map<Class<? extends AbstractFeature>, AbstractFeature> featureInstances = Collections.synchronizedMap(new LinkedHashMap<>());
    public static final Map<Class<? extends AbstractHook<?>>, AbstractHook<?>> hookInstances = Collections.synchronizedMap(new LinkedHashMap<>());
    public static final Map<String, Map.Entry<Supplier<String>, Supplier<Integer>>> customStatuses = Collections.synchronizedMap(new LinkedHashMap<>());
    public static int versionCode;
}
