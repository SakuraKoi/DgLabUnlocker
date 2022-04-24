package sakura.kooi.dglabunlocker.remote;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.utils.FieldAccessor;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class WebsocketRPC extends WebSocketServer {
    public static final WebsocketRPC INSTANCE = new WebsocketRPC();

    private WebsocketRPC() {
        super(new InetSocketAddress("0.0.0.0", 23301));
    }

    public static boolean isRunning = false;
    public static int connected = 0;

    public static void update(boolean checked, @Nullable TextView textDesc) throws InterruptedException {
        if (isRunning != checked) {
            if (isRunning) {
                INSTANCE.stop();
                Log.i("DgLabUnlocker", "RPC: Websocket server stopped");
            } else {
                INSTANCE.start();
                Log.i("DgLabUnlocker", "RPC: Websocket server started");
            }
        }
        if (textDesc != null) {
            updateStatus(textDesc);
        }
    }

    public static void updateStatus(TextView textDesc) {
        textDesc.setText(WebsocketRPC.isRunning ? "运行于 ws://0.0.0.0:23301" : "对外开放Websocket RPC服务端口");
        if (WebsocketRPC.isRunning) {
            textDesc.setTextColor(0xffc6ff00);
        } else if (StatusDialog.resourceInjection) {
            textDesc.setTextColor(0xffdfd2a5);
        } else { // system default color
            TextView textView = new TextView(textDesc.getContext());
            textDesc.setTextColor(textView.getCurrentTextColor());
        }
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connected++;
        Log.i("DgLabUnlocker", "RPC: client connected");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connected--;
        Log.i("DgLabUnlocker", "RPC: client disconnected");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        JSONObject req;
        int id;
        try {
            req = new JSONObject(message);
            id = req.getInt("id");
        } catch (JSONException e) {
            conn.send("{\"id\":null,\"code\":400,\"result\":\"Bad request\"}");
            return;
        }

        try {
            Log.i("DgLabUnlocker", "RPC: received message " + message);
            String method = req.getString("method");
            Object data = req.get("data");
            dispatchMethods(conn, id, method, data);
        } catch (Exception e) {
            try {
                ModuleUtils.logError("DgLabUnlocker", "RPC: Internal error", e);
                conn.send(makeResponse(id, 500, "Internal error", null));
            } catch (JSONException ex) {
                ModuleUtils.logError("DgLabUnlocker", "RPC: Failed write response", e);
                conn.send("{\"id\":" + id + ",\"code\":500,\"result\":\"Internal server error, see xposed log for details\"}");
            }
        }
    }

    private void dispatchMethods(WebSocket conn, int id, String method, Object payload) throws JSONException, ReflectiveOperationException {
        switch (method) {
            case "queryStrength": {
                queryStrength(conn, id);
                break;
            }
            case "setStrength": {
                if (payload instanceof JSONObject) {
                    setStrength(conn, id, (JSONObject) payload);
                } else {
                    conn.send(makeResponse(id, 400, "Bad request", null));
                }
                break;
            }
            case "addStrength": {
                if (payload instanceof JSONObject) {
                    boolean channel = ((JSONObject) payload).getBoolean("channel");
                    int strength = ((JSONObject) payload).getInt("strength");
                    if (channel) {
                        addStrength(conn, id, true, strength, Accessors.localStrengthA, Accessors.remoteStrengthA, Accessors.maxStrengthA, Accessors.totalStrengthA);
                    } else {
                        addStrength(conn, id, false, strength, Accessors.localStrengthB, Accessors.remoteStrengthB, Accessors.maxStrengthB, Accessors.totalStrengthB);
                    }
                } else {
                    conn.send(makeResponse(id, 400, "Bad request", null));
                }
                break;
            }
            default: {
                conn.send(makeResponse(id, 400, "Unknown command", null));
            }
        }
    }

    private void addStrength(WebSocket conn, int id, boolean channel, int strength, FieldAccessor<Integer> localStrength, FieldAccessor<Integer> remoteStrength, FieldAccessor<Integer> maxStrength, FieldAccessor<Integer> totalStrength) throws ReflectiveOperationException, JSONException {
        strength = localStrength.get() + strength + remoteStrength.get();

        if (Accessors.isRemote.get() && !ModuleSettings.bypassRemoteMaxStrength) {
            int max = maxStrength.get();
            if (max < strength) {
                strength = max;
            }
        }
        if (strength < 0) {
            strength = 0;
        }
        totalStrength.set(strength);
        Log.i("DgLabUnlocker", "RPC: Strength " + (channel ? "A" : "B")+ "set to " + strength);
        conn.send(makeResponse(id, 0, "ok", null));
    }

    private void setStrength(WebSocket conn, int id, JSONObject payload) throws JSONException, ReflectiveOperationException {
        int strengthA = payload.getInt("strengthA");
        int strengthB = payload.getInt("strengthB");
        strengthA = strengthA + Accessors.remoteStrengthA.get();
        strengthB = strengthB + Accessors.remoteStrengthA.get();

        if (Accessors.isRemote.get() && !ModuleSettings.bypassRemoteMaxStrength) {
            int maxStrengthA = Accessors.maxStrengthA.get();
            int maxStrengthB = Accessors.maxStrengthB.get();
            if (maxStrengthA < strengthA) {
                strengthA = maxStrengthA;
            }
            if (maxStrengthB < strengthB) {
                strengthB = maxStrengthB;
            }
        }
        Accessors.totalStrengthA.set(strengthA);
        Accessors.totalStrengthB.set(strengthB);
        Log.i("DgLabUnlocker", "RPC: Strength set to " + strengthA + " | " + strengthB);
        conn.send(makeResponse(id, 0, "ok", null));
    }

    private void queryStrength(WebSocket conn, int id) throws JSONException, ReflectiveOperationException {
        JSONObject data = new JSONObject();
        data.put("baseStrengthA", Accessors.localStrengthA.get());
        data.put("baseStrengthB", Accessors.localStrengthB.get());
        if (Accessors.isRemote.get()) {
            data.put("remoteStrengthA", Accessors.remoteStrengthA.get());
            data.put("remoteStrengthB", Accessors.remoteStrengthB.get());
            data.put("maxStrengthA", Accessors.maxStrengthA.get());
            data.put("maxStrengthB", Accessors.maxStrengthB.get());
        } else {
            data.put("remoteStrengthA", null);
            data.put("remoteStrengthB", null);
            data.put("maxStrengthA", null);
            data.put("maxStrengthB", null);
        }

        conn.send(makeResponse(id, 0, "ok", data));
    }

    @NonNull
    private String makeResponse(int id, int code, String result, Object data) throws JSONException {
        JSONObject resp = new JSONObject();
        resp.put("id", id);
        resp.put("code", code);
        resp.put("result", result);
        resp.put("data", data);
        return resp.toString();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }
}
