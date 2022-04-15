package sakura.kooi.dglabunlocker.hooks;

import android.util.Log;

import java.util.UUID;

public class HookRandomQrCode {
    public static final HookRandomQrCode INSTANCE = new HookRandomQrCode();
    private HookRandomQrCode() {}

    public String apply(String identifier) {
        Log.i("DgLabUnlocker", "Replacing login identifier " + identifier);
        return UUID.randomUUID().toString();
    }
}
