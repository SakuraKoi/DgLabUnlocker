package sakura.kooi.dglabunlocker.features;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;

import lombok.SneakyThrows;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.remote.WebsocketRPC;
import sakura.kooi.dglabunlocker.variables.HookRegistry;

public class FeatureWebsocketRpc extends AbstractFeature {
    private WebsocketRPC service = new WebsocketRPC();

    @Override
    public String getSettingName() {
        return "远程 | 开放RPC服务端口";
    }

    @Override
    public String getSettingDesc() {
        return service.isRunning ? "运行于 ws://0.0.0.0:23301" : "对外开放Websocket RPC服务端口";
    }

    @Override
    public String getConfigurationKey() {
        return "FeatureWebsocketRpc";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Collections.emptyList();
    }

    @Override
    public boolean isUnsupported() {
        return false;
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.ALL;
    }

    @Override
    public void initializeAndTest() throws Exception {
        HookRegistry.customStatuses.put("[接口] RPC服务连接数", new AbstractMap.SimpleEntry<>(
                () -> service.isRunning ? String.valueOf(service.connected) : "关闭",
                () -> service.isRunning ? 0xffc6ff00 : 0xfff44336
        ));
    }

    @SneakyThrows(InterruptedException.class)
    @Override
    public void updateFeatureStatus(boolean enabled) {
        if (enabled) {
            service.start();
        } else {
            service.stop();
            service = new WebsocketRPC();
        }
    }
}
