package sakura.kooi.dglabunlocker.features;

import android.util.Log;

import java.util.UUID;

public class FeatureRandomQrCode {
    public static final FeatureRandomQrCode INSTANCE = new FeatureRandomQrCode();
    private FeatureRandomQrCode() {}

    public String apply(String identifier) {
        Log.i("DgLabUnlocker", "Replacing login identifier " + identifier);
        return UUID.randomUUID().toString();
    }
}
