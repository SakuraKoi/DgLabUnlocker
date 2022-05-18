package sakura.kooi.dglabunlocker.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;
import sakura.kooi.dglabunlocker.utils.ModuleUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;
import sakura.kooi.dglabunlocker.variables.ModuleSettings;

public class WebsocketRPC extends WebSocketServer {
    public WebsocketRPC() {
        super(new InetSocketAddress("0.0.0.0", 23301));
    }

    public boolean isRunning = false;
    public int connected = 0;

    public void start() {
        super.start();
        isRunning = true;
        Log.i("DgLabUnlocker", "RPC: Websocket server started");
    }

    public void stop() throws InterruptedException {
        super.stop();
        isRunning = false;
        Log.i("DgLabUnlocker", "RPC: Websocket server stopped");
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
            Object data = req.has("data") ? req.get("data") : null;
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
                        addStrength(conn, id, true, strength, Accessors.maxStrengthA, Accessors.totalStrengthA);
                    } else {
                        addStrength(conn, id, false, strength, Accessors.maxStrengthB, Accessors.totalStrengthB);
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

    private void addStrength(WebSocket conn, int id, boolean channel, int strength, FieldAccessor<Integer> maxStrength, FieldAccessor<Integer> totalStrength) throws ReflectiveOperationException, JSONException {
        strength = totalStrength.get() + strength;

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
        Log.i("DgLabUnlocker", "RPC: Strength " + (channel ? "A" : "B") + " set to " + strength);
        ModuleUtils.broadcastUiUpdate();
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
        ModuleUtils.broadcastUiUpdate();
        conn.send(makeResponse(id, 0, "ok", null));
    }

    private void queryStrength(WebSocket conn, int id) throws JSONException, ReflectiveOperationException {
        JSONObject data = new JSONObject();
        data.put("totalStrengthA", Accessors.totalStrengthA.get());
        data.put("totalStrengthB", Accessors.totalStrengthB.get());
        data.put("baseStrengthA", Accessors.localStrengthA.get());
        data.put("baseStrengthB", Accessors.localStrengthB.get());
        data.put("remoteStrengthA", Accessors.remoteStrengthA.get());
        data.put("remoteStrengthB", Accessors.remoteStrengthB.get());
        data.put("maxStrengthA", Accessors.maxStrengthA.get());
        data.put("maxStrengthB", Accessors.maxStrengthB.get());
        data.put("isRemote", Accessors.isRemote.get());
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
